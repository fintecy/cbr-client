package org.fintecy.md.cbr;

import org.fintecy.md.cbr.model.CbrCurrency;
import org.fintecy.md.cbr.model.Currency;
import org.fintecy.md.cbr.model.ExchangeRate;
import org.fintecy.md.cbr.model.InterestRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoOpCbrApiTest {
    private CbrApi noOpCbrApi;

    @BeforeEach
    void setUp() {
        noOpCbrApi = new NoOpCbrApi();
    }

    @Test
    void should_test_supported_currencies() {
        //given
        List<CbrCurrency> expected = List.of();
        //when
        List<CbrCurrency> actual = noOpCbrApi.supportedCurrencies();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_depositRates() {
        //given
        Map<LocalDate, List<InterestRate>> expected = Map.of();
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusWeeks(1);
        //when
        var actual = noOpCbrApi.depositRates(from, to);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_historical_rates_by_currency() {
        //given
        String code = "GBP";
        List<ExchangeRate> expected = List.of();
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusWeeks(1);
        //when
        var actual = noOpCbrApi.rates(code, from, to);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_historical_rates() {
        //given
        List<ExchangeRate> expected = List.of();
        var asOf = LocalDate.now().minusWeeks(1);
        //when
        var actual = noOpCbrApi.rates(asOf);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_latest_monthly_rates() {
        //given
        List<ExchangeRate> expected = List.of();
        boolean monthly = true;
        //when
        var actual = noOpCbrApi.rates(monthly);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_latest_rates() {
        //given
        List<ExchangeRate> expected = List.of();
        //when
        var actual = noOpCbrApi.rates();
        //then
        assertEquals(expected, actual);
    }
}