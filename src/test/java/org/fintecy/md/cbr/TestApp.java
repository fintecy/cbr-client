package org.fintecy.md.cbr;

import java.time.LocalDate;

public class TestApp {
    public static void main(String[] args) {
        var api = CbrClient.api();
        var cbrCurrencies = api.supportedCurrencies();
        var rates1 = api.rates();
        var rates2 = api.rates(LocalDate.of(2022, 8, 1));
        var rates3 = api.rates(true);
    }
}
