package org.fintecy.md.cbr.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CbrDepositRate {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @XmlAttribute(name = "Date")
    private String date;
    @XmlElement(name = "Overnight")
    private String overnight;
    @XmlElement(name = "Tom-next")
    private String tomNext;
    @XmlElement(name = "Spot-next")
    private String spotNext;
    @XmlElement(name = "P1week")
    private String p1week;
    @XmlElement(name = "Spot-week")
    private String spotWeek;
    @XmlElement(name = "P2weeks")
    private String p2weeks;
    @XmlElement(name = "Spot-2weeks")
    private String spot2Weeks;
    @XmlElement(name = "P1month")
    private String p1month;
    @XmlElement(name = "P3month")
    private String p3month;

    public LocalDate getDate() {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public BigDecimal getOvernight() {
        return normalise(overnight);
    }

    public BigDecimal getTomNext() {
        return normalise(tomNext);
    }

    public BigDecimal getSpotNext() {
        return normalise(spotNext);
    }

    public BigDecimal getP1week() {
        return normalise(p1week);
    }

    public BigDecimal getSpotWeek() {
        return normalise(spotWeek);
    }

    public BigDecimal getP2weeks() {
        return normalise(p2weeks);
    }

    public BigDecimal getSpot2Weeks() {
        return normalise(spot2Weeks);
    }

    public BigDecimal getP1month() {
        return normalise(p1month);
    }

    public BigDecimal getP3month() {
        return normalise(p3month);
    }

    private BigDecimal normalise(String v) {
        return new BigDecimal(v.replace(",", "."));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CbrDepositRate that = (CbrDepositRate) o;
        return Objects.equals(date, that.date) && Objects.equals(overnight, that.overnight) && Objects.equals(tomNext, that.tomNext) && Objects.equals(spotNext, that.spotNext) && Objects.equals(p1week, that.p1week) && Objects.equals(spotWeek, that.spotWeek) && Objects.equals(p2weeks, that.p2weeks) && Objects.equals(spot2Weeks, that.spot2Weeks) && Objects.equals(p1month, that.p1month) && Objects.equals(p3month, that.p3month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, overnight, tomNext, spotNext, p1week, spotWeek, p2weeks, spot2Weeks, p1month, p3month);
    }

    @Override
    public String toString() {
        return "CbrDepositRate{" +
                "date='" + date + '\'' +
                ", overnight='" + overnight + '\'' +
                ", tomNext='" + tomNext + '\'' +
                ", spotNext='" + spotNext + '\'' +
                ", p1week='" + p1week + '\'' +
                ", spotWeek='" + spotWeek + '\'' +
                ", p2weeks='" + p2weeks + '\'' +
                ", spot2Weeks='" + spot2Weeks + '\'' +
                ", p1month='" + p1month + '\'' +
                ", p3month='" + p3month + '\'' +
                '}';
    }
}
