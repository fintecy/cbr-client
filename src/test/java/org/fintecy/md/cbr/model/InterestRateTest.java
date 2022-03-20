package org.fintecy.md.cbr.model;

import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static org.fintecy.md.cbr.model.Tenor.tenor;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InterestRateTest {
    @Test
    void should_create_ir() {
        var ir = new InterestRate(tenor("M1"), now(), valueOf(1.2));
        assertEquals("IR(M1=1.2, asOf=2022-03-20)", ir.toString());
    }
}