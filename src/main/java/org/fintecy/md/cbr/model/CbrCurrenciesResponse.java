package org.fintecy.md.cbr.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * @author batiaev
 */
@XmlRootElement(name = "Valuta")
public class CbrCurrenciesResponse {
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "Item")
    private List<CbrCurrency> currencies;

    public String getName() {
        return name;
    }

    public List<CbrCurrency> getCurrencies() {
        return currencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CbrCurrenciesResponse that = (CbrCurrenciesResponse) o;
        return Objects.equals(name, that.name) && Objects.equals(currencies, that.currencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, currencies);
    }

    @Override
    public String toString() {
        return "CbrCurrenciesResponse{" +
                "name='" + name + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}
