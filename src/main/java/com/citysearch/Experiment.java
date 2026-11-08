
package com.citysearch;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class Experiment {
    private static final int REPEATS = 5;

    private static void saveRow(
            BufferedWriter writer,
            String algorithm,
            String input,
            int n,
            int run,
            long time,
            long operations,
            int matches
    ) throws Exception {
        writer.write(String.format(
                Locale.US,
                "%s,%s,%d,%d,%.6f,%d,%d%n",
                algorithm,
                input,
                n,
                run,
                time / 1_000_000.0,
                operations,
                matches
        ));
    }

    public static void main(String[] args) throws Exception {
        Path file = Path.of("results/results.csv");
        Files.createDirectories(file.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write(
                    "algorithm,input,n,run,time_ms,operations,matches\n"
            );

            Distance[] algorithms = {
                    new RecursiveDistance(),
                    new TableDistance(),
                    new TwoRowsDistance()
            };

            for (Distance algorithm : algorithms) {
                int[] sizes = algorithm instanceof RecursiveDistance
                        ? new int[]{2, 4, 6, 8, 10}
                        : new int[]{64, 128, 256, 512};

                for (String type : new String[]{"random", "different"}) {
                    for (int n : sizes) {
                        String[] pair =
                                InputGenerator.distancePair(type, n);

                        int expected = new TwoRowsDistance()
                                .distance(pair[0], pair[1]);

                        // Warm-up
                        algorithm.distance(pair[0], pair[1]);

                        for (int run = 1; run <= REPEATS; run++) {
                            long start = System.nanoTime();

                            int answer = algorithm.distance(
                                    pair[0], pair[1]
                            );

                            long elapsed = System.nanoTime() - start;

                            if (answer != expected) {
                                throw new IllegalStateException(
                                        "Edit Distance mismatch"
                                );
                            }

                            saveRow(
                                    writer,
                                    algorithm.name(),
                                    type,
                                    n,
                                    run,
                                    elapsed,
                                    algorithm.operations(),
                                    -1
                            );
                        }
                    }
                }
            }

            StringMatcher[] matchers = {
                    new NaiveSearch(),
                    new KMPSearch()
            };

            for (StringMatcher matcher : matchers) {
                for (String type : new String[]{"random", "near_match"}) {
                    for (int n : new int[]{
                            1000, 4000, 16000, 64000, 256000
                    }) {
                        String[] pair =
                                InputGenerator.searchPair(type, n);

                        List<Integer> expected = new NaiveSearch()
                                .search(pair[0], pair[1]);

                        // Warm-up
                        matcher.search(pair[0], pair[1]);

                        for (int run = 1; run <= REPEATS; run++) {
                            long start = System.nanoTime();

                            List<Integer> result = matcher.search(
                                    pair[0], pair[1]
                            );

                            long elapsed = System.nanoTime() - start;

                            if (!result.equals(expected)) {
                                throw new IllegalStateException(
                                        "Search mismatch"
                                );
                            }

                            saveRow(
                                    writer,
                                    matcher.name(),
                                    type,
                                    n,
                                    run,
                                    elapsed,
                                    matcher.comparisons(),
                                    result.size()
                            );
                        }
                    }
                }
            }
        }

        System.out.println("Experiment completed!");
        System.out.println("File: results/results.csv");
    }
}
