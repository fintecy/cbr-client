package org.fintecy.md.cbr;

import org.fintecy.md.cbr.model.CbrCurrency;
import org.fintecy.md.cbr.model.ExchangeRate;
import org.fintecy.md.cbr.model.InterestRate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class NoOpCbrApi implements CbrApi {
    @Override
    public List<ExchangeRate> rates(boolean monthly) {
        return List.of();
    }

    @Override
    public List<ExchangeRate> rates(LocalDate date) {
        return List.of();
    }

    @Override
    public List<ExchangeRate> rates(String code, LocalDate from, LocalDate to) {
        return List.of();
    }

    @Override
    public Map<LocalDate, List<InterestRate>> depositRates(LocalDate from, LocalDate to) {
        return Map.of();
    }

    @Override
    public List<CbrCurrency> supportedCurrencies() {
        return List.of();
    }
}
