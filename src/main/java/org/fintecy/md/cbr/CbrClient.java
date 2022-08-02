package org.fintecy.md.cbr;

import dev.failsafe.Failsafe;
import dev.failsafe.Policy;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.fintecy.md.cbr.model.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.math.BigDecimal.valueOf;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.fintecy.md.cbr.model.Currency.currency;
import static org.fintecy.md.cbr.model.Tenor.tenor;

public class CbrClient implements CbrApi {
    private final String rootPath;
    private final HttpClient client;
    private final List<Policy<Object>> policies;

    protected CbrClient(String rootPath, HttpClient httpClient, List<Policy<Object>> policies) {
        this.client = checkRequired(httpClient, "Http client required for CBR client");
        this.rootPath = checkRequired(rootPath, "root path cannot be empty");
        this.policies = ofNullable(policies).orElse(List.of());
    }

    public static CbrApi api() {
        return cbrClient().build();
    }

    public static CbrClientBuilder cbrClient() {
        return new CbrClientBuilder();
    }

    public static <T> T checkRequired(T v, String msg) {
        return ofNullable(v)
                .orElseThrow(() -> new IllegalArgumentException(msg));
    }

    @Override
    public List<ExchangeRate> rates(boolean monthly) {
        return convert(processRequest("/XML_daily.asp" + (monthly ? "?d=1" : ""), CbrDailyQuoteResponse.class));
    }

    @Override
    public List<ExchangeRate> rates(LocalDate date) {
        boolean monthly = date.getDayOfMonth() == 1;
        final var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return convert(processRequest("/XML_daily.asp?date_req=" + date.format(formatter) + (monthly ? "&d=1" : ""), CbrDailyQuoteResponse.class));
    }

    @Override
    public List<ExchangeRate> rates(String code, LocalDate from, LocalDate to) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Map<LocalDate, List<InterestRate>> depositRates(LocalDate from, LocalDate to) {
        final var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return convert(processRequest("/XML_depo.asp?date_req1=" + from.format(formatter) + "&date_req2=" + to.format(formatter), CbrDepositRatesResponse.class).getRecords());
    }

    private Map<LocalDate, List<InterestRate>> convert(List<CbrDepositRate> records) {
        LocalDate now = LocalDate.now();
        HashMap<LocalDate, List<InterestRate>> result = new HashMap<>();
        for (CbrDepositRate record : records) {
            result.putIfAbsent(record.getDate(), new ArrayList<>());
            List<InterestRate> irs = result.get(record.getDate());
            irs.add(new InterestRate(tenor("ON"), now, record.getOvernight()));
            irs.add(new InterestRate(tenor("TN"), now, record.getTomNext()));
            irs.add(new InterestRate(tenor("SN"), now, record.getSpotNext()));
            irs.add(new InterestRate(tenor("W1"), now, record.getP1week()));
            irs.add(new InterestRate(tenor("W1S"), now, record.getSpotWeek()));
            irs.add(new InterestRate(tenor("W2"), now, record.getP2weeks()));
            irs.add(new InterestRate(tenor("W2S"), now, record.getSpot2Weeks()));
            irs.add(new InterestRate(tenor("M1"), now, record.getP1month()));
            irs.add(new InterestRate(tenor("M3"), now, record.getP3month()));
        }
        return result;
    }

    @Override
    public List<CbrCurrency> supportedCurrencies() {
        return processRequest("/XML_valFull.asp", CbrCurrenciesResponse.class).getCurrencies();
    }

    private <T> T processRequest(String url, Class<T> responseType) {
        final var request = HttpRequest.newBuilder(URI.create(rootPath + url)).build();
        var failsafeExecutor = policies.isEmpty() ? Failsafe.none() : Failsafe.with(policies);
        if (client.executor().isPresent()) {
            failsafeExecutor = failsafeExecutor.with(client.executor().get());
        }
        return parseResponse(failsafeExecutor.get(() -> client.send(request, ofString()))
                .body(), responseType);
    }

    private <T> T parseResponse(String body, Class<T> modelClass) {
        try {
            return (T) JAXBContext.newInstance(modelClass)
                    .createUnmarshaller()
                    .unmarshal(new ByteArrayInputStream(body.getBytes()));
        } catch (JAXBException e) {
            throw new IllegalStateException("Can parse response", e);
        }
    }

    private List<ExchangeRate> convert(CbrDailyQuoteResponse processRequest) {
        return processRequest.getQuotes()
                .stream()
                .filter(q -> q.getCurrCode() != null)
                .map(q -> new ExchangeRate(currency("RUB"), currency(q.getCurrCode()), processRequest.getDate(),
                        valueOf(q.getValue() / q.getNominal())))
                .collect(toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CbrClient cbrClient = (CbrClient) o;
        return Objects.equals(rootPath, cbrClient.rootPath)
                && Objects.equals(policies, cbrClient.policies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootPath, policies);
    }
}
