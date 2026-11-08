
package com.citysearch;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {

    private final Distance[] distances = {
            new RecursiveDistance(),
            new TableDistance(),
            new TwoRowsDistance()
    };

    @Test
    void testReferenceDistances() {
        for (Distance d : distances) {
            assertEquals(3, d.distance("kitten", "sitting"));
            assertEquals(2, d.distance("flaw", "lawn"));
            assertEquals(5, d.distance("intention", "execution"));
            assertEquals(3, d.distance("", "abc"));
            assertEquals(3, d.distance("abc", ""));
            assertEquals(0, d.distance("abc", "abc"));
            assertEquals(0, d.distance("", ""));
        }
    }

    @Test
    void testRandomDistances() {
        Random random = new Random(20261108);

        for (int i = 0; i < 100; i++) {
            String a = generate(random, 6);
            String b = generate(random, 6);

            int expected = distances[1].distance(a, b);

            for (Distance d : distances) {
                assertEquals(expected, d.distance(a, b));
            }
        }
    }

    @Test
    void testLPS() {
        KMPSearch kmp = new KMPSearch();

        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void testReferenceSearch() {
        StringMatcher[] algorithms = {
                new NaiveSearch(),
                new KMPSearch()
        };

        for (StringMatcher s : algorithms) {
            assertEquals(
                    List.of(10),
                    s.search("ABABDABACDABABCABAB", "ABABCABAB")
            );

            assertEquals(
                    List.of(0, 1, 2, 3),
                    s.search("AAAAA", "AA")
            );

            assertEquals(
                    List.of(0, 2, 4),
                    s.search("ABABABA", "ABA")
            );

            assertEquals(
                    List.of(),
                    s.search("HELLO", "XYZ")
            );
        }
    }

    @Test
    void testRandomSearch() {
        Random random = new Random(1919);

        NaiveSearch naive = new NaiveSearch();
        KMPSearch kmp = new KMPSearch();

        for (int i = 0; i < 100; i++) {
            String text = generate(
                    random, 10 + random.nextInt(30)
            );

            String pattern = generate(
                    random, 1 + random.nextInt(8)
            );

            assertEquals(
                    naive.search(text, pattern),
                    kmp.search(text, pattern)
            );
        }
    }

    @Test
    void testEmptyPattern() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NaiveSearch().search("hello", "")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new KMPSearch().search("hello", "")
        );
    }

    @Test
    void testPatternLongerThanText() {
        assertEquals(
                List.of(),
                new NaiveSearch().search("abc", "abcdef")
        );

        assertEquals(
                List.of(),
                new KMPSearch().search("abc", "abcdef")
        );
    }

    private String generate(Random random, int length) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(
                    "abc".charAt(random.nextInt(3))
            );
        }

        return result.toString();
    }
}
