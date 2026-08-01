# Contributing to java-lab

Thanks for your interest in improving java-lab! This is a learning-focused collection of small,
self-contained Java examples. The goal is that **every example teaches one concept clearly**.

## Ways to contribute

- Add a new example for a Core Java concept that isn't covered yet.
- Improve an existing example (clarity, comments, edge cases).
- Improve the docs (`README.md`, `CONCURRENCY.md`, `LAMBDA.md`).
- Add tests for logic that can be asserted deterministically.
- Fix bugs or typos.

## Getting started

Prerequisites: **JDK 21+** and **Maven**.

```bash
git clone https://github.com/rahilsh/java-lab.git
cd java-lab
mvn verify            # compiles, checks formatting, runs tests
```

Run any example:

```bash
mvn -q compile exec:java -Dexec.mainClass=<fully-qualified-class-name>
```

## Guidelines for a good example

1. **One concept per class.** Keep it small and focused.
2. **Add a class-level Javadoc** that states: what concept it shows, what to observe when you run
   it, and a reference link where useful. See the existing examples for the style.
3. **Put it in the right topic package**, e.g. `com.rsh.jlab.concurrency.<topic>` or
   `com.rsh.jlab.lambda`.
4. **Prefer readable over clever.** This code is meant to be read by people who are still learning.
5. **Add a test** if the example has logic that can be asserted without relying on timing or console
   output (see `ProcessListFasterTest` and `DynamicFilteringTest`).

## Before you open a pull request

Run the full build locally — CI runs the same checks:

```bash
mvn spotless:apply    # auto-format your code (google-java-format)
mvn verify            # must pass: formatting, enforcer, tests
```

- Keep commits focused and write clear messages.
- Reference any related issue in the PR description.
- One logical change per PR where possible.

## Code style

Formatting is enforced by [Spotless](https://github.com/diffplug/spotless) using
`google-java-format`. If the build fails on formatting, run `mvn spotless:apply` and commit the
result.

By contributing, you agree that your contributions are licensed under the project's
[Apache 2.0 License](LICENSE).
