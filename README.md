# City Search & Spell-Check

**Design and Analysis of Algorithms — Assignment 4**

## Project Description

This Java project implements Dynamic Programming and String Matching algorithms for a city search platform.

The project has two main tasks:

1. Calculate Edit Distance between two strings to detect typing mistakes.
2. Find all occurrences of a pattern in a text using Naive Search and KMP.

## Implemented Algorithms

### Edit Distance

- Recursive Edit Distance
- Dynamic Programming Table
- Dynamic Programming with Two Rows

### String Search

- Naive String Search
- Knuth-Morris-Pratt (KMP)
- LPS Array Construction

## Technologies

- Java 17+
- Maven
- JUnit 5
- CSV for experiment results
- Python and Matplotlib for generating graphs

## Project Structure

```text
DAA_Assignment4/
├── src/
│   ├── main/java/com/citysearch/
│   │   ├── Distance.java
│   │   ├── RecursiveDistance.java
│   │   ├── TableDistance.java
│   │   ├── TwoRowsDistance.java
│   │   ├── StringMatcher.java
│   │   ├── NaiveSearch.java
│   │   ├── KMPSearch.java
│   │   ├── InputGenerator.java
│   │   └── Experiment.java
│   └── test/java/com/citysearch/
│       └── AlgorithmTest.java
├── results/
│   ├── results.csv
│   └── plots/
├── pom.xml
├── README.md
├── REPORT.md
└── plot_results.py
```

## How to Run

Open the project in IntelliJ IDEA.

### 1. Run tests

Open the terminal and enter:

```bash
mvn test
```

Expected result: all 7 tests pass.

### 2. Run the experiment

```bash
mvn exec:java
```

The results will be saved in:

`results/results.csv`

### 3. Generate plots

Install Matplotlib if necessary:

```bash
pip install matplotlib
```

Run:

```bash
python plot_results.py
```

The graphs will be saved in:

`results/plots/`

## Experiment

The experiment compares algorithm performance using different input sizes and input types.

The following metrics are recorded:

- Algorithm name
- Input type
- Input size
- Execution time in milliseconds
- Number of operations or comparisons
- Number of matches

The experiment uses a fixed random seed for reproducibility.

Each configuration is measured five times.

## Testing

JUnit 5 tests verify:

- Reference Edit Distance values
- Empty strings
- Correct LPS arrays
- Overlapping pattern matches
- Empty pattern exceptions
- Patterns longer than the text
- 100 random Edit Distance comparisons
- 100 random String Search comparisons

All 7 tests passed successfully.

## Results

The experiment generated 230 measurements.

Four graphs compare recursive operations, DP execution time, Naive and KMP comparisons, and string search execution time.

The results show that Dynamic Programming avoids repeated recursive work, while KMP reduces unnecessary character comparisons.

Detailed findings are available in `REPORT.md`.

## GitHub Submission

The final repository should use:

- Branch: `main`
- Tag: `v1.0`

The final ZIP archive should be named:

`DAA_Assignment4_name_surname_group.zip`

The GitHub repository link must be included in the Moodle submission.

## AI-Use Acknowledgement

AI tools were used to assist with code development, debugging, explanations, documentation and report preparation.

The algorithms were tested in IntelliJ IDEA using JUnit 5. Experiment results were generated using the Java implementation and saved to CSV.