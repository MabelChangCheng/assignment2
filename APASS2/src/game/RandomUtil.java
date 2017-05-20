package game;

import java.util.Random;

/**
 * Utility to generate random numbers.
 */
class RandomUtil {

    /**
     * Random number generator.
     */
    private static final Random RANDOM = new Random();

    /**
     * Generates a number in the specified range.
     *
     * @param min lower bound of the random number.
     * @param max upper bound of the random number.
     * @return the number generated.
     */
    public static int generate(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
