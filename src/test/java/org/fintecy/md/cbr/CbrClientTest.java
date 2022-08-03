package org.fintecy.md.cbr;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.fintecy.md.cbr.model.CbrCurrency;
import org.fintecy.md.cbr.model.ExchangeRate;
import org.fintecy.md.cbr.model.InterestRate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.parse;
import static org.fintecy.md.cbr.CbrClient.api;
import static org.fintecy.md.cbr.CbrClient.cbrClient;
import static org.fintecy.md.cbr.model.Currency.currency;
import static org.fintecy.md.cbr.model.Tenor.tenor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(httpPort = 7777)
class CbrClientTest {

    @Test
    void should_create_equal_client() {
        //when
        var actual = api();
        var expected = cbrClient().build();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_supported_currencies() {
        //given
        List<CbrCurrency> expected = List.of(new CbrCurrency("R01010", 36, "AUD",
                        "Australian Dollar", 1),
                new CbrCurrency("R01015", 40, "ATS",
                        "Austrian Shilling", 1000));
        //given
        String api = "XML_valFull";
        stubFor(get("/" + api + ".asp")
                .willReturn(aResponse()
                        .withBodyFile(api + ".xml")));

        //when
        var actual = cbrClient()
                .rootPath("http://localhost:7777")
                .build()
                .supportedCurrencies();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_depositRates() {
        //given
        var api = "XML_depo";
        var to = LocalDate.now();
        var from = to.minusWeeks(1);
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        stubFor(get("/" + api + ".asp?date_req1=" + formatter.format(from) + "&date_req2=" + formatter.format(to))
                .willReturn(aResponse()
                        .withBodyFile(api + ".xml")));
        var expected = expectedDepoRatesResponse();
        //when
        var actual = cbrClient()
                .rootPath("http://localhost:7777")
                .build()
                .depositRates(from, to);
        //then
        assertEquals(expected, actual);
    }

    private Map<LocalDate, List<InterestRate>> expectedDepoRatesResponse() {
        var now = LocalDate.now();
        return Map.of(
                parse("2001-07-02"), List.of(
                        new InterestRate(tenor("ON"), now, new BigDecimal(2)),
                        new InterestRate(tenor("TN"), now, new BigDecimal("2.3")),
                        new InterestRate(tenor("SN"), now, new BigDecimal("2.5")),
                        new InterestRate(tenor("W1"), now, new BigDecimal(7)),
                        new InterestRate(tenor("W1S"), now, new BigDecimal("7.5")),
                        new InterestRate(tenor("W2"), now, new BigDecimal("8.5")),
                        new InterestRate(tenor("W2S"), now, new BigDecimal(9)),
                        new InterestRate(tenor("M1"), now, new BigDecimal(10)),
                        new InterestRate(tenor("M3"), now, new BigDecimal(12))
                ),
                parse("2001-07-03"), List.of(
                        new InterestRate(tenor("ON"), now, new BigDecimal(2)),
                        new InterestRate(tenor("TN"), now, new BigDecimal("2.3")),
                        new InterestRate(tenor("SN"), now, new BigDecimal("2.5")),
                        new InterestRate(tenor("W1"), now, new BigDecimal(7)),
                        new InterestRate(tenor("W1S"), now, new BigDecimal("7.5")),
                        new InterestRate(tenor("W2"), now, new BigDecimal("8.5")),
                        new InterestRate(tenor("W2S"), now, new BigDecimal(9)),
                        new InterestRate(tenor("M1"), now, new BigDecimal(10)),
                        new InterestRate(tenor("M3"), now, new BigDecimal(12))
                )
        );
    }

    @Test
    void should_test_historical_rates_by_currency() {
        //given
        var code = "GBP";
        var to = LocalDate.now();
        var from = to.minusWeeks(1);
        //when
        //then
        assertThrows(IllegalStateException.class, () -> cbrClient()
                .rootPath("http://localhost:7777")
                .build()
                .rates(code, from, to));
    }

    @Test
    void should_test_historical_rates() {
        //given
        var api = "XML_daily";
        var asOf = LocalDate.now().minusWeeks(1);
        final var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        stubFor(get("/" + api + ".asp?date_req=" + formatter.format(asOf))
                .willReturn(aResponse()
                        .withBodyFile(api + ".xml")));
        var expected = expectedLatestResponse();

        //when
        var actual = cbrClient()
                .rootPath("http://localhost:7777")
                .build()
                .rates(asOf);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_latest_monthly_rates() {
        //given
        var api = "XML_daily";
        stubFor(get("/" + api + ".asp?d=1")
                .willReturn(aResponse()
                        .withBodyFile(api + ".xml")));

        var expected = expectedLatestResponse();
        boolean monthly = true;
        //when
        var actual = cbrClient()
                .rootPath("http://localhost:7777")
                .build()
                .rates(monthly);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_latest_rates() {
        //given
        String api = "XML_daily";
        stubFor(get("/" + api + ".asp")
                .willReturn(aResponse()
                        .withBodyFile(api + ".xml")));

        var expected = expectedLatestResponse();
        //when
        var actual = cbrClient()
                .rootPath("http://localhost:7777")
                .build()
                .rates();
        //then
        assertEquals(expected, actual);
    }

    private List<ExchangeRate> expectedLatestResponse() {
        return List.of(new ExchangeRate(currency("RUB"), currency("AUD"), parse("2021-12-01"), valueOf(53.3685)));
    }
}
