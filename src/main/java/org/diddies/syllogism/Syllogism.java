package org.diddies.syllogism;

import java.util.Comparator;

/**
 * Encapsulates a categorical syllogism. A syllogism is an argument in which
 * objects are divided into specific classes. Syllogisms deal with categorical
 * propositions, which are statements that say something about all or some
 * members of a class.
 *
 * A Syllogism of the form AAA-4: All dogs are pets All pets are animals
 * Therefore all dogs are animals
 *
 * This is obviously not true (look at premise #1), but the argument is
 * certainly valid. It is arguments of this form that this class represents.
 *
 * @author Ryan Kenney
 */
public class Syllogism {

    // Attributes
    private String mood;
    private int figure;
    private int majorIndex;
    private int minorIndex;
    private boolean[][] distribution;
    
    // Constants
    private final int MAJOR_PREMISE = 0;
    private final int MINOR_PREMISE = 1;
    private final int CONCLUSION = 2;

    /**
     * Create a syllogism of the specified mood and figure.
     *
     * @param mood The mood
     * @param figure The figure
     */
    private Syllogism(String mood, int figure) {
        this.mood = mood.toUpperCase();
        this.figure = figure;

        // Prepare the distibution
        // This could be condensed, but the code is more intuitive
        // when written out like this.
        distribution = new boolean[3][2];
        for (int premise = 0; premise < 3; premise++) {
            switch (this.mood.charAt(premise)) {
                case 'A':
                    distribution[premise][0] = true;
                    distribution[premise][1] = false;
                    break;
                case 'E':
                    distribution[premise][0] = true;
                    distribution[premise][1] = true;
                    break;
                case 'I':
                    distribution[premise][0] = false;
                    distribution[premise][1] = false;
                    break;
                case 'O':
                    distribution[premise][0] = false;
                    distribution[premise][1] = true;
                    break;
            }
        }

        // Major and minor indices
        // This could be condensed, but this is more intuitive
        switch (figure) {
            case 1:
                majorIndex = 0;
                minorIndex = 1;
                break;
            case 2:
                majorIndex = 1;
                minorIndex = 1;
                break;
            case 3:
                majorIndex = 0;
                minorIndex = 0;
                break;
            case 4:
                majorIndex = 1;
                minorIndex = 0;
                break;
        }
    }

    /**
     * Create a Syllogism object out of a String representation.
     *
     * @param input The syllogism in the form MMM-F
     * @return A syllogism object created from input.
     */
    public static Syllogism parseSyllogism(String input) {
        Syllogism ret = null;
        String mood;
        int figure;
        String[] parts = input.split("-");
        if (parts[0].length() == 3) {
            mood = parts[0];
            if (!mood.matches(".*[^AaEeIiOo].*")) {
                try {
                    figure = Integer.parseInt(parts[1]);
                    if (figure >= 1 && figure <= 4) {
                        ret = new Syllogism(mood, figure);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Error:  Enter a valid figure (1-4)");
                }
            }
        }
        return ret;
    }

    /**
     * Get a Comparator that orders Syllogisms by figure
     *
     * @return A Comparator that orders Syllogisms by their figure
     */
    public static Comparator<Syllogism> orderByFigure() {
        return new Comparator<Syllogism>() {
            @Override
            public int compare(Syllogism o1, Syllogism o2) {
                if (o1.getFigure() > o2.getFigure()) {
                    return 1;
                } else if (o1.getFigure() == o2.getFigure()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        };
    }

    /**
     * Tests if the syllogism commits the fallacy of Affirmative Conclusion with
     * a negative premise.
     *
     * @return True if the syllogism has a negative premise and affirmative
     * conclusion, false otherwise.
     */
    public boolean affConNegPrem() {
        if (!isNegative(CONCLUSION)) {
            return (isNegative(MAJOR_PREMISE) || isNegative(MINOR_PREMISE));
        } else {
            return false;
        }
    }

    /**
     * Tests if the syllogism commits the existential fallacy.
     *
     * @return True if the syllogism commits the existential fallacy, false
     * otherwise.
     */
    public boolean commitsExistentialFallacy() {
        if (isExistential(CONCLUSION)) {
            return (!isExistential(MAJOR_PREMISE) && !isExistential(MINOR_PREMISE));
        } else {
            return false;
        }
    }

    /**
     * Check if a premise is existential. Use the defined constants for
     * readability.
     *
     * @param premise The premise to check
     * @return True if the premise is existential, false otherwise
     */
    private boolean isExistential(int premise) {
        return mood.charAt(premise) == 'I' || mood.charAt(premise) == 'O';
    }

    /**
     * Tests if the middle term is distributed at least once.
     *
     * @return True if the middle is distributed, false otherwise.
     */
    public boolean hasDistributedMiddle() {
        return distribution[MAJOR_PREMISE][1 - majorIndex] || distribution[MINOR_PREMISE][1 - minorIndex];
    }

    /**
     * Tests if the syllogism commits the fallacy of illicit major.
     *
     * @return True if the syllogism commits the fallacy of illicit major, false
     * otherwise.
     */
    public boolean commitsIllicitMajor() {
        if (distribution[CONCLUSION][1]) {
            return !distribution[MAJOR_PREMISE][majorIndex];
        } else {
            return false;
        }
    }

    /**
     * Check if a premise is negative. Use the defined constants for
     * readability.
     *
     * @param premise The premise to check
     * @return True if the premise is negative, false otherwise
     */
    private boolean isNegative(int premise) {
        return mood.charAt(premise) == 'E' || mood.charAt(premise) == 'O';
    }

    /**
     * Determine if the syllogism has exclusive premises (both negative)
     *
     * @return True if the syllogism has exclusive premises, false otherwise.
     */
    public boolean hasExclusivePremises() {
        return isNegative(MAJOR_PREMISE) && isNegative(MINOR_PREMISE);
    }

    /**
     * Tests if the syllogism commits the fallacy of illicit minor.
     *
     * @return True if the syllogism commits the fallacy of illicit minor, false
     * otherwise.
     */
    public boolean commitsIllicitMinor() {
        if (distribution[CONCLUSION][0]) {
            return !distribution[MINOR_PREMISE][minorIndex];
        } else {
            return false;
        }
    }

    /**
     * Get the String representation of a Syllogism
     *
     * @return String version of the object
     */
    @Override
    public String toString() {
        return mood + "-" + getFigure();
    }

    /**
     * @return the figure
     */
    public int getFigure() {
        return figure;
    }
}
