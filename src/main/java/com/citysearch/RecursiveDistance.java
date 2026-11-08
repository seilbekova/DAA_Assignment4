package com.citysearch;

public class RecursiveDistance implements Distance {
    private long operations;

    // State: d(i,j) = edit distance of prefixes a[0..i), b[0..j).
    // Transition: match -> diagonal; mismatch -> 1 + minimum of three choices.
    // Base case: d(0,j)=j, d(i,0)=i.
    @Override
    public int distance(String a, String b) {
        operations = 0;
        return solve(a, b, a.length(), b.length());
    }

    private int solve(String a, String b, int i, int j) {
        operations++;

        if (i == 0) return j;
        if (j == 0) return i;

        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return solve(a, b, i - 1, j - 1);
        }

        int deletion = solve(a, b, i - 1, j);
        int insertion = solve(a, b, i, j - 1);
        int replacement = solve(a, b, i - 1, j - 1);

        return 1 + Math.min(deletion,
                Math.min(insertion, replacement));
    }

    @Override
    public long operations() {
        return operations;
    }

    @Override
    public String name() {
        return "Recursive";
    }
}
