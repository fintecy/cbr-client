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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.fintecy.md.cbr.model.Currency.currency;

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

    public static double checkRequired(double v, String msg) {
        return (v == 0 ? Optional.<Double>empty() : Optional.of(v))
                .orElseThrow(() -> new IllegalArgumentException(msg));
    }

    public static <T> T checkRequired(T v, String msg) {
        return ofNullable(v)
                .orElseThrow(() -> new IllegalArgumentException(msg));
    }

    @Override
    public List<ExchangeRate> rates(boolean monthly) {
        return convert(processRequest("/XML_val.asp" + (monthly ? "?d=1" : ""), CbrDailyQuoteResponse.class));
    }

    @Override
    public List<ExchangeRate> rates(LocalDate date) {
        boolean monthly = date.getDayOfMonth() == 1;
        final var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return convert(processRequest("/XML_daily.asp" + "?date_req=" + date.format(formatter) + (monthly ? "&d=1" : ""), CbrDailyQuoteResponse.class));
    }

    @Override
    public List<ExchangeRate> rates(String code, LocalDate from, LocalDate to) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Map<LocalDate, List<InterestRate>> depositRates(LocalDate from, LocalDate to) {
        throw new IllegalStateException("not implemented");
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
}
