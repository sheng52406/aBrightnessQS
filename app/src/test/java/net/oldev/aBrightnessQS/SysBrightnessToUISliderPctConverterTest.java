package net.oldev.aBrightnessQS;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;

public class SysBrightnessToUISliderPctConverterTest {

    @RunWith(Parameterized.class)
    public static class PositiveTest {

        @Parameters(name = "{index}: uiPctToSysBrightness({0})={1}")
        public static Iterable<Object[]> data() {
            return Arrays.asList(new Object[][] { 
                    { 0, 0 }, { 10, 1 }, { 20, 6 }, { 25, 10 }, 
                    { 50, 50 }, { 75, 130 }, { 100, 255 }
            });
        }

        @Parameter
        public int uiPctInTest;

        @Parameter(1)
        public int sysBrightnessExpected;

        // Test driver for positive tests.
        // Given a uiPct (0 - 100), convert to sysBrightness;
        // then convert it back.
        @Test
        public void tWithUiPct() throws Exception {
            final SysBrightnessToUISliderPctConverterI c = new SysBrightnessToUISliderPctConverter();
            final int sysBrightnessActual = c.uiPctToSysBrightness(uiPctInTest);
            assertEquals("1) uiPct to sysBrightness conversion:",  sysBrightnessExpected, sysBrightnessActual);

            // Do reverse conversion test
            //  The exepected result should be the uiPct supplied by the caller
            final int uiPctExpected = uiPctInTest; 
            final int uiPctConverted = c.sysBrightnessToUiPct(sysBrightnessActual);

            // since the conversion involves rounding to integer
            // reverse the conversion could induce slight rounding error
            assertEquals("2) sysBrightness to uiPct conversion:", (float)uiPctExpected, (float)uiPctConverted, 1f);

        }

    }

    @RunWith(Parameterized.class)
    public static class Negative4UiPctTest {

        @Parameters(name = "{index}: uiPct={0}")
        public static Object[] data() {
            return new Object[] { -1, 101 };
        }
        
        @Parameter
        public int uiPct;

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Test
        public void test() throws Exception {
            thrown.expect(IllegalArgumentException.class);
            final SysBrightnessToUISliderPctConverterI c = new SysBrightnessToUISliderPctConverter();
            int sysBrightnessActual = c.uiPctToSysBrightness(uiPct);
        }
    }

    @RunWith(Parameterized.class)
    public static class Negative4SysBrightnessTest {

        @Parameters(name = "{index}: sysBrightness={0}")
        public static Object[] data() {
            return new Object[] { -1, 256 };
        }

        @Parameter
        public int sysBrightness;

        @Rule
        public ExpectedException thrown = ExpectedException.none();
        
        @Test
        public void test() throws Exception {
            thrown.expect(IllegalArgumentException.class);
            final SysBrightnessToUISliderPctConverterI c = new SysBrightnessToUISliderPctConverter();
            int uiPctActual = c.sysBrightnessToUiPct(sysBrightness);
        }
    }    
    
}