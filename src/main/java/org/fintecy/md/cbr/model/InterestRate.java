package org.fintecy.md.cbr.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class InterestRate {
    private final Tenor tenor;
    private final LocalDate asOf;
    private final BigDecimal rate;

    public InterestRate(Tenor tenor, LocalDate asOf, BigDecimal rate) {
        this.tenor = tenor;
        this.asOf = asOf;
        this.rate = rate;
    }

    public Tenor getTenor() {
        return tenor;
    }

    public LocalDate getAsOf() {
        return asOf;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestRate that = (InterestRate) o;
        return Objects.equals(tenor, that.tenor) && Objects.equals(asOf, that.asOf) && Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenor, asOf, rate);
    }

    @Override
    public String toString() {
        return "IR(" + tenor.getValue() + "=" + rate + ", asOf=" + asOf + ")";
    }
}
