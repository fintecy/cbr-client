package org.fintecy.md.cbr.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.Objects;

/**
 * @author batiaev
 */
public class CbrQuote {
    @XmlAttribute(name = "ID")
    private String cbrCode;
    @XmlElement(name = "NumCode")
    private int code;
    @XmlElement(name = "CharCode")
    private String currCode;
    @XmlElement(name = "Nominal")
    private int nominal;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Value")
    private String value;

    public double getValue() {
        return Double.parseDouble(value.replace(",", "."));
    }

    public int getCode() {
        return code;
    }

    public String getCurrCode() {
        return currCode;
    }

    public int getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CbrQuote cbrQuote = (CbrQuote) o;
        return code == cbrQuote.code && nominal == cbrQuote.nominal && Objects.equals(cbrCode, cbrQuote.cbrCode) && Objects.equals(currCode, cbrQuote.currCode) && Objects.equals(name, cbrQuote.name) && Objects.equals(value, cbrQuote.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cbrCode, code, currCode, nominal, name, value);
    }

    @Override
    public String toString() {
        return "CbrQuote{" +
                "cbrCode='" + cbrCode + '\'' +
                ", code=" + code +
                ", currCode='" + currCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
