package org.diddies.syllogism;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Take a syllogism as input and test if it's valid.
 *
 * @author Ryan Kenney
 */
public class SyllogismTester {

    // Attributes
    private static final String EXIT_STRING = "exit";
    private static final String ALL_VALID_STRING = "show valids";

    // Enums
    private enum Fallacy {
        UM, IMa, IMi, EP, ACNP, EF
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Init
        Scanner user = new Scanner(System.in);
        String input = "";

        // Main program
        System.out.println("Welcome to the Syllogism Tester!");
        System.out.println("Please enter a syllogism of the form MMM-F.");
        System.out.println("Type \"" + EXIT_STRING + "\" to quit.");
        System.out.println("Type \"" + ALL_VALID_STRING + "\" to show all valid syllogisms.");

        CONSOLE:
        while (!input.equals(EXIT_STRING)) {
            Syllogism syll;
            System.out.print("> ");
            input = user.nextLine();
            switch (input) {
                // Don't parse the exit string.
                case EXIT_STRING:
                    break CONSOLE;
                // Show all syllogisms that are valid
                case ALL_VALID_STRING:
		    // Note that this is well known, and calculating it each time
                    // isn't necessary, but rather done to prove the functionality
                    // of the program
                    List<Syllogism> allSyllogisms = generateSyllogisms();
                    List<Syllogism> allValid = new ArrayList<>();
                    // Find the valid syllogisms
                    for (Syllogism s : allSyllogisms) {
                        if (validateSyllogism(s) == null) {
                            allValid.add(s);
                        }
                    }
                    Collections.sort(allValid, Syllogism.orderByFigure());

                    // Print them out
                    int currentFigure = 1;
                    System.out.format("Here are all %d valid syllogisms:\n", allValid.size());
                    System.out.println("{");
                    for (int i = 0; i < allValid.size(); i++) {
                        if (allValid.get(i).getFigure() != currentFigure) {
                            currentFigure = allValid.get(i).getFigure();
                            System.out.println();
                        }
                        System.out.print(allValid.get(i));
                        // Print out a comma for all but the last one.
                        if (i < allValid.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println("\n}");
                    break;
                // Test the validity of the user defined syllogism
                default:
                    syll = Syllogism.parseSyllogism(input);
                    if (syll != null) {
                        List<Fallacy> fallacies = validateSyllogism(syll);
                        System.out.format("%s: %s\n", syll, (fallacies == null ? "valid" : "invalid"));
                        if (fallacies != null) {
                            // print them
                        }
                    } else {
                        System.out.println("Sorry, that syllogism was not formatted correctly (MMM-F)");
                    }
                    break;
            }
        }

        // End the program
        System.out.println("Goodbye!");
    }

    /**
     * Generates all possible syllogisms
     *
     * @return The List of Syllogisms
     */
    public static List<Syllogism> generateSyllogisms() {
        List<Syllogism> ret = new ArrayList<>();

        // Create every combination
        char[] moods = {'A', 'E', 'I', 'O'};
        for (char a : moods) {
            for (char b : moods) {
                for (char c : moods) {
                    for (int figure = 1; figure <= 4; figure++) {
                        ret.add(Syllogism.parseSyllogism(String.valueOf(a)
                                + String.valueOf(b)
                                + String.valueOf(c)
                                + "-" + figure));
                    }
                }
            }
        }

        return ret;
    }

    /**
     * Find, if any, all the fallacies a syllogism commits
     *
     * @param s The syllogism to check
     * @return The fallacies, if any. If there are none, null is returned.
     */
    private static List<Fallacy> validateSyllogism(Syllogism s) {
        // Init
        List<Fallacy> ret = new ArrayList<>();

        // Check for fallacies
        if (!s.hasDistributedMiddle()) {
            ret.add(Fallacy.UM);
        }
        if (s.commitsIllicitMajor()) {
            ret.add(Fallacy.IMa);
        }
        if (s.commitsIllicitMinor()) {
            ret.add(Fallacy.IMi);
        }
        if (s.hasExclusivePremises()) {
            ret.add(Fallacy.EP);
        }
        if (s.affConNegPrem()) {
            ret.add(Fallacy.ACNP);
        }
        if (s.commitsExistentialFallacy()) {
            ret.add(Fallacy.EF);
        }

        // Return the list
        return (ret.isEmpty()) ? null : ret;
    }
}
