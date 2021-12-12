package org.fintecy.md.cbr.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ExchangeRate {
    private final Currency base;
    private final Currency counter;
    private final LocalDate date;
    private final BigDecimal mid;

    public ExchangeRate(Currency base,
                        Currency counter,
                        LocalDate date,
                        BigDecimal mid) {
        this.base = base;
        this.counter = counter;
        this.date = date;
        this.mid = mid;
    }

    public Currency getBase() {
        return base;
    }

    public Currency getCounter() {
        return counter;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getMid() {
        return mid;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "base=" + base +
                ", counter=" + counter +
                ", date=" + date +
                ", mid=" + mid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(base, that.base)
                && Objects.equals(counter, that.counter)
                && Objects.equals(date, that.date)
                && Objects.equals(mid, that.mid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, counter, date, mid);
    }
}
