package org.fintecy.md.cbr.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author batiaev
 */
@XmlRootElement(name = "Deposit")
public class CbrDepositRatesResponse {
    @XmlAttribute(name = "FromDate")
    private String fromDate;
    @XmlAttribute(name = "ToDate")
    private String toDate;
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "Record")
    private List<CbrDepositRate> records;

    public LocalDate getFromDate() {
        long l = Long.parseLong(fromDate);
        return LocalDate.of((int) (l % 100000000 / 10000), (int) (l % 10000 / 100), (int) (l % 100));
    }

    public LocalDate getToDate() {
        long l = Long.parseLong(toDate);
        return LocalDate.of((int) (l % 100000000 / 10000), (int) (l % 10000 / 100), (int) (l % 100));
    }

    public String getName() {
        return name;
    }

    public List<CbrDepositRate> getRecords() {
        return records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CbrDepositRatesResponse that = (CbrDepositRatesResponse) o;
        return Objects.equals(fromDate, that.fromDate)
                && Objects.equals(toDate, that.toDate)
                && Objects.equals(name, that.name)
                && Objects.equals(records, that.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromDate, toDate, name, records);
    }

    @Override
    public String toString() {
        return "CbrDepositRatesResponse{" +
                "fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", name='" + name + '\'' +
                ", records=" + records +
                '}';
    }
}
