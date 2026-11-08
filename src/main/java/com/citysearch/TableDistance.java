package com.citysearch;

public class TableDistance implements Distance {
    private long operations;

    // State: dp[i][j] = edit distance of prefixes a[0..i), b[0..j).
    // Transition: min(delete + 1, insert + 1, diagonal + cost).
    // Base case: dp[i][0]=i, dp[0][j]=j.
    @Override
    public int distance(String a, String b) {
        operations = 0;

        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                operations++;

                int cost = a.charAt(i - 1) ==
                        b.charAt(j - 1) ? 0 : 1;

                dp[i][j] = Math.min(
                        dp[i - 1][j] + 1,
                        Math.min(
                                dp[i][j - 1] + 1,
                                dp[i - 1][j - 1] + cost
                        )
                );
            }
        }

        return dp[n][m];
    }

    @Override
    public long operations() {
        return operations;
    }

    @Override
    public String name() {
        return "Table";
    }
}
