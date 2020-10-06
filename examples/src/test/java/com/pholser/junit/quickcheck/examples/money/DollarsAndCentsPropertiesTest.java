/*
 The MIT License

 Copyright (c) 2010-2020 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.pholser.junit.quickcheck.examples.money;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_DOWN;
import static java.math.RoundingMode.HALF_UP;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.Precision;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import java.math.BigDecimal;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class DollarsAndCentsPropertiesTest {
    @Property public void roundingDown(@Precision(scale = 8) BigDecimal d) {
        BigDecimal[] pieces = d.divideAndRemainder(ONE);
        BigDecimal integral = pieces[0];
        BigDecimal fractional = pieces[1];

        assumeThat(fractional, lessThanOrEqualTo(new BigDecimal("0.5")));

        DollarsAndCents money = new DollarsAndCents(d);

        assertEquals(
            integral.add(fractional).setScale(2, HALF_DOWN),
            money.toBigDecimal());
    }

    @Property public void roundingUp(@Precision(scale = 8) BigDecimal d) {
        BigDecimal[] pieces = d.divideAndRemainder(ONE);
        BigDecimal integral = pieces[0];
        BigDecimal fractional = pieces[1];

        assumeThat(fractional, greaterThan(new BigDecimal("0.5")));

        DollarsAndCents money = new DollarsAndCents(d);

        assertEquals(
            integral.add(fractional).setScale(2, HALF_UP),
            money.toBigDecimal());
    }
}
