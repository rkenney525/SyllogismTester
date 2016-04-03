package org.diddies.syllogism;

import java.util.Comparator;
import static org.junit.Assert.*;
import org.junit.Test;

public class SyllogismTest {

    private static final Syllogism SYLLOGISM_AAA1 = Syllogism.parseSyllogism("AAA-1");
    private static final Syllogism SYLLOGISM_AEA1 = Syllogism.parseSyllogism("AEA-1");
    private static final Syllogism SYLLOGISM_AAI2 = Syllogism.parseSyllogism("AAI-2");
    private static final Syllogism SYLLOGISM_AIE4 = Syllogism.parseSyllogism("AIE-4");
    private static final Syllogism SYLLOGISM_AIO1 = Syllogism.parseSyllogism("AIO-1");
    private static final Syllogism SYLLOGISM_AOA2 = Syllogism.parseSyllogism("AOA-2");
    private static final Syllogism SYLLOGISM_AOE2 = Syllogism.parseSyllogism("AOE-2");
    private static final Syllogism SYLLOGISM_EAA1 = Syllogism.parseSyllogism("EAA-1");
    private static final Syllogism SYLLOGISM_IEO1 = Syllogism.parseSyllogism("IEO-1");
    private static final Syllogism SYLLOGISM_IIO3 = Syllogism.parseSyllogism("IIO-3");
    private static final Syllogism SYLLOGISM_OAA1 = Syllogism.parseSyllogism("OAA-1");
    private static final Syllogism SYLLOGISM_OAA3 = Syllogism.parseSyllogism("OAA-3");
    private static final Syllogism SYLLOGISM_OAE2 = Syllogism.parseSyllogism("OAE-2");
    private static final Syllogism SYLLOGISM_OOO1 = Syllogism.parseSyllogism("OOO-1");
    private static final Syllogism SYLLOGISM_OOO4 = Syllogism.parseSyllogism("OOO-4");

    @Test
    public void testParseSyllogism() {
        String form = "aaa-1";
        Syllogism syll = Syllogism.parseSyllogism(form);
        assertEquals("The figure was parsed correctly", 1, syll.getFigure());
        assertEquals("The toString should return the input in all caps", form.toUpperCase(), syll.toString());
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
        assertTrue("EAA-1 is distributed on the major premise", SYLLOGISM_EAA1.hasDistributedMiddle());
        assertTrue("AEA-1 is distributed on the minor premise", SYLLOGISM_AEA1.hasDistributedMiddle());
        assertFalse("AIO-1 is not distributed", SYLLOGISM_AIO1.hasDistributedMiddle());
    }

    @Test
    public void testCommitsIllicitMajor() {
        // TODO relearn the rules of syllogisms and better document the distributions
        assertFalse("AAI-2 does not commit this because it has a positive conclusion (see distributions)", SYLLOGISM_AAI2.commitsIllicitMajor());
        assertFalse("OOO-4 does not commit this (see distributions)", SYLLOGISM_OOO4.commitsIllicitMajor());
        assertTrue("OOO-1 does commit this (see distributions)", SYLLOGISM_OOO1.commitsIllicitMajor());
    }

    @Test
    public void testHasExclusivePremises() {
        assertFalse("IEO-1 does not commit this because it has a positive major premise", SYLLOGISM_IEO1.hasExclusivePremises());
        assertFalse("OAE-2 does not commit this because it has a positive minor premise", SYLLOGISM_OAE2.hasExclusivePremises());
        assertTrue("OOO-4 does commit this because it has no positive premise", SYLLOGISM_OOO4.hasExclusivePremises());
    }

    @Test
    public void testCommitsIllicitMinor() {
        // TODO relearn the rules of syllogisms and better document the distributions
        assertFalse("AAI-2 does not commit this because it has an existential conclusion (see distributions)", SYLLOGISM_AAI2.commitsIllicitMinor());
        assertFalse("OAA-3 does not commit this (see distributions)", SYLLOGISM_OAA3.commitsIllicitMinor());
        assertTrue("OAA-1 does commit this (see distributions)", SYLLOGISM_OAA1.commitsIllicitMinor());
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
