
package com.citysearch;

import java.util.ArrayList;
import java.util.List;

public class KMPSearch implements StringMatcher {
    private long comparisons;

    public int[] lps(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;

        while (i < pattern.length()) {
            comparisons++;

            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i] = ++len;
                i++;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }

    @Override
    public List<Integer> search(String text, String pattern) {
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException(
                    "Pattern cannot be empty"
            );
        }

        comparisons = 0;
        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return matches;
        }

        int[] prefix = lps(pattern);
        int i = 0;
        int j = 0;

        while (i < n) {
            comparisons++;

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == m) {
                    matches.add(i - m);
                    j = prefix[j - 1];
                }
            } else if (j > 0) {
                j = prefix[j - 1];
            } else {
                i++;
            }
        }

        return matches;
    }

    @Override
    public long comparisons() {
        return comparisons;
    }

    @Override
    public String name() {
        return "KMP";
    }
}

