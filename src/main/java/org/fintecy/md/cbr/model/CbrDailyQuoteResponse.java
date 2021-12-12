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
@XmlRootElement(name = "ValCurs")
public class CbrDailyQuoteResponse {
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "Date")
    private String date;
    @XmlElement(name = "Valute")
    private List<CbrQuote> quotes;

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public List<CbrQuote> getQuotes() {
        return quotes == null ? List.of() : quotes;
    }

    @Override
    public String toString() {
        return "CbrDailyQuoteResponse{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", quotes=" + quotes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CbrDailyQuoteResponse that = (CbrDailyQuoteResponse) o;
        return Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(quotes, that.quotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, quotes);
    }
}
