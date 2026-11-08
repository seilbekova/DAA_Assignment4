package com.citysearch;

import java.util.Random;

public class InputGenerator {
    private static final long SEED = 20261108L;

    public static String randomString(int length, long salt) {
        Random random = new Random(SEED + salt);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append("abcd".charAt(random.nextInt(4)));
        }

        return sb.toString();
    }

    public static String[] distancePair(String type, int n) {
        if (type.equals("random")) {
            return new String[]{
                    randomString(n, n + 17),
                    randomString(n, n + 101)
            };
        }

        if (type.equals("different")) {
            return new String[]{
                    "a".repeat(n),
                    "b".repeat(n)
            };
        }

        throw new IllegalArgumentException(type);
    }

    public static String[] searchPair(String type, int n) {
        if (type.equals("random")) {
            return new String[]{
                    randomString(n, n + 151),
                    randomString(32, n + 271)
            };
        }

        if (type.equals("near_match")) {
            return new String[]{
                    "a".repeat(n),
                    "a".repeat(31) + "b"
            };
        }

        throw new IllegalArgumentException(type);
    }
}
