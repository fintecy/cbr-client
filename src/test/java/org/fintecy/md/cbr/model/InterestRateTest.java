package org.fintecy.md.cbr.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static org.fintecy.md.cbr.model.Tenor.tenor;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InterestRateTest {
    @Test
    void should_create_ir() {
        var ir = new InterestRate(tenor("M1"), now(), valueOf(1.2));
        assertEquals(String.format("IR(M1=1.2, asOf=%s)", LocalDate.now()), ir.toString());
    }
}
