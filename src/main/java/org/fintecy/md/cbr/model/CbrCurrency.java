package org.fintecy.md.cbr.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.Objects;

public class CbrCurrency {
    @XmlAttribute(name = "ID")
    private String id;
    @XmlElement(name = "ISO_Num_Code")
    private long numCode;
    @XmlElement(name = "ISO_Char_Code")
    private String charCode;
    @XmlElement(name = "EngName")
    private String name;
    @XmlElement(name = "Nominal")
    private long nominal;

    public CbrCurrency() {
    }

    public CbrCurrency(String id, int numCode, String charCode, String name, int nominal) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.name = name;
        this.nominal = nominal;
    }

    public Currency toCurrency() {
        return new Currency(charCode);
    }

    public java.util.Currency toJavaCurrency() {
        return java.util.Currency.getInstance(charCode);
    }

    public String getId() {
        return id;
    }

    public long getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getName() {
        return name;
    }

    public long getNominal() {
        return nominal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CbrCurrency that = (CbrCurrency) o;
        return numCode == that.numCode && nominal == that.nominal && Objects.equals(id, that.id)
                && Objects.equals(charCode, that.charCode) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numCode, charCode, name, nominal);
    }

    @Override
    public String toString() {
        return "CbrCurrency{" +
                "id='" + id + '\'' +
                ", numCode=" + numCode +
                ", charCode=" + charCode +
                ", name='" + name + '\'' +
                ", nominal=" + nominal +
                "} " + super.toString();
    }
}
