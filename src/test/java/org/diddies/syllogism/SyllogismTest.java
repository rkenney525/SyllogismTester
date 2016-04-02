package org.diddies.syllogism;

import java.util.Comparator;
import static org.junit.Assert.*;
import org.junit.Test;

public class SyllogismTest {

    private static final Syllogism SYLLOGISM_AAA1 = Syllogism.parseSyllogism("AAA-1");
    private static final Syllogism SYLLOGISM_AAI2 = Syllogism.parseSyllogism("AAI-2");
    private static final Syllogism SYLLOGISM_AIE4 = Syllogism.parseSyllogism("AIE-4");
    private static final Syllogism SYLLOGISM_AIO1 = Syllogism.parseSyllogism("AIO-1");
    private static final Syllogism SYLLOGISM_AOA2 = Syllogism.parseSyllogism("AOA-2");
    private static final Syllogism SYLLOGISM_AOE2 = Syllogism.parseSyllogism("AOE-2");
    private static final Syllogism SYLLOGISM_IEO1 = Syllogism.parseSyllogism("IEO-1");
    private static final Syllogism SYLLOGISM_IIO3 = Syllogism.parseSyllogism("IIO-3");

    @Test
    public void testParseSyllogism() {
        fail("The test case is a prototype.");
    }

    @Test
    public void testOrderByFigure() {
        Comparator<Syllogism> comparator = Syllogism.orderByFigure();
        assertEquals("AOE-2 should be greater than AAA-1", 1, comparator.compare(SYLLOGISM_AOE2, SYLLOGISM_AAA1));
        assertEquals("AAA-1 should be less than AOA-2", -1, comparator.compare(SYLLOGISM_AAA1, SYLLOGISM_AOA2));
        assertEquals("AOA-2 should equal to AOE-2", 0, comparator.compare(SYLLOGISM_AOA2, SYLLOGISM_AOE2));
    }

    @Test
    public void testAffConNegPrem() {
        assertFalse("AOE-2 does not commit this because it has a negative conclusion", SYLLOGISM_AOE2.affConNegPrem());
        assertFalse("AAA-1 does not commit this because it has positive premises", SYLLOGISM_AAA1.affConNegPrem());
        assertTrue("AOA-2 does commit this because it has a negative premise", SYLLOGISM_AOA2.affConNegPrem());
    }

    @Test
    public void testCommitsExistentialFallacy() {
        assertFalse("AAA-1 does not commit this because it has a universal conclusion", SYLLOGISM_AAA1.commitsExistentialFallacy());
        assertFalse("AIO-1 does not commit this because it has universal major premise", SYLLOGISM_AIO1.commitsExistentialFallacy());
        assertFalse("IEO-1 does not commit this because it has universal minor premises", SYLLOGISM_IEO1.commitsExistentialFallacy());
        assertTrue("AAI-2 does commit this because it has no existential premise", SYLLOGISM_AAI2.commitsExistentialFallacy());
    }

    @Test
    public void testHasDistributedMiddle() {
        fail("The test case is a prototype.");
    }

    @Test
    public void testCommitsIllicitMajor() {
        fail("The test case is a prototype.");
    }

    @Test
    public void testHasExclusivePremises() {
        fail("The test case is a prototype.");
    }

    @Test
    public void testCommitsIllicitMinor() {
        fail("The test case is a prototype.");
    }

    @Test
    public void testToString() {
        assertEquals("Should be AAA-1", "AAA-1", SYLLOGISM_AAA1.toString());
        assertEquals("Should be IIO-3", "IIO-3", SYLLOGISM_IIO3.toString());
    }

    @Test
    public void testGetFigure() {
        assertEquals("AAA-1's figure is 1", 1, SYLLOGISM_AAA1.getFigure());
        assertEquals("AOE-2's figure is 2", 2, SYLLOGISM_AOE2.getFigure());
        assertEquals("IIO-3's figure is 3", 3, SYLLOGISM_IIO3.getFigure());
        assertEquals("AIE-4's figure is 4", 4, SYLLOGISM_AIE4.getFigure());
    }
}
