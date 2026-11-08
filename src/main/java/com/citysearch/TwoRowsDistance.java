package com.citysearch;

public class TwoRowsDistance implements Distance {
    private long operations;

    // State: previous[j] and current[j] hold adjacent DP rows.
    // Transition: min(delete + 1, insert + 1, diagonal + cost).
    // Base case: previous[j]=j, current[0]=i.
    @Override
    public int distance(String a, String b) {
        operations = 0;

        if (b.length() > a.length()) {
            String temp = a;
            a = b;
            b = temp;
        }

        int n = a.length();
        int m = b.length();

        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            previous[j] = j;
        }

        for (int i = 1; i <= n; i++) {
            current[0] = i;

            for (int j = 1; j <= m; j++) {
                operations++;

                int cost = a.charAt(i - 1) ==
                        b.charAt(j - 1) ? 0 : 1;

                current[j] = Math.min(
                        previous[j] + 1,
                        Math.min(
                                current[j - 1] + 1,
                                previous[j - 1] + cost
                        )
                );
            }

            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[m];
    }

    @Override
    public long operations() {
        return operations;
    }

    @Override
    public String name() {
        return "TwoRows";
    }
}
