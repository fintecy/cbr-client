package org.fintecy.md.cbr.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

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
}
