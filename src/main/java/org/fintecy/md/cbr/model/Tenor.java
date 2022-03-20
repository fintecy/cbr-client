package org.fintecy.md.cbr.model;

public class Tenor extends MicroType<String> {

    public Tenor(String value) {
        super(value);
    }

    public static Tenor tenor(String tenor) {
        return new Tenor(tenor);
    }
}
