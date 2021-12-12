package org.fintecy.md.cbr.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyTest {
    private static Set<java.util.Currency> getAvailableCurrencies() {
        return java.util.Currency.getAvailableCurrencies();
    }

    @ParameterizedTest
    @MethodSource("getAvailableCurrencies")
    void shouldConvertJavaCurrencies(java.util.Currency currency) {
        java.util.Currency actual = Currency.fromJavaCurrency(currency).toJavaCurrency();
        assertEquals(currency, actual);
        System.out.println(currency);
    }
}