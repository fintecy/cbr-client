package org.fintecy.md.cbr;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.fintecy.md.cbr.model.CbrCurrency;
import org.fintecy.md.cbr.model.ExchangeRate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.parse;
import static org.fintecy.md.cbr.CbrClient.api;
import static org.fintecy.md.cbr.CbrClient.cbrClient;
import static org.fintecy.md.cbr.model.Currency.currency;
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
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusWeeks(1);
        //when
        //then
        assertThrows(IllegalStateException.class, () -> cbrClient()
                .rootPath("http://localhost:7777")
                .build()
                .depositRates(from, to));
    }

    @Test
    void should_test_historical_rates_by_currency() {
        //given
        String code = "GBP";
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusWeeks(1);
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
        String api = "XML_daily";
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
        String api = "XML_daily";
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
