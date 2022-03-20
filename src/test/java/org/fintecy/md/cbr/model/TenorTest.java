package org.fintecy.md.cbr.model;

import org.junit.jupiter.api.Test;

import static org.fintecy.md.cbr.model.Tenor.tenor;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TenorTest {
    @Test
    void should_create_tenor() {
        var actual = tenor("M1");
        assertEquals("M1", actual.getValue());
    }
}