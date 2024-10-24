# INFO-F403_project_part1 
# Project Build and Run Instructions

This project uses a Makefile to manage the build, test, and run processes. Below are the instructions for various tasks you can perform using the Makefile.

## Prerequisites

Ensure you have the following tools installed:
- `javac` (Java Compiler)
- `jflex` (JFlex Scanner Generator)
- `jar` (Java Archive Tool)
- `javadoc` (Java Documentation Tool)

## Makefile Targets

### Compile Everything

To compile all the source files and generate the necessary artifacts, run:
```sh
make all
```

### Generate Lexical Analyzer

To generate the `LexicalAnalyzer.java` file from the JFlex file, run:
```sh
make $(SRC_DIR)/LexicalAnalyzer.java
```

### Compile Java Files

To compile the Java source files, run:
```sh
make $(SRC_DIR)/Main.class
```

### Create JAR File

To create the JAR file, run:
```sh
make $(OUTPUT_JAR)
```

### Run Tests

To compile the files and run tests on all `.gls` files in the test directory, run:
```sh
make tests
```

### Run the Program

To run the program from the JAR file with a specified `.gls` input file, run:
```sh
make run
```

### Generate Javadoc

To generate the Javadoc for the project, run:
```sh
make javadoc
```

### Clean Up

To clean up generated files (`.class`, `.java`, and the JAR file), run:
```sh
make clean
```

To clean up generated Javadoc, run:
```sh
make javadoclean
```

## Directory Structure

- `src`: Contains the source files.
- `test/test1`: Contains the test files.
- `dist`: Directory where the JAR file will be generated.
- `doc/javadoc`: Directory where the Javadoc will be generated.

## Example Usage

To compile the project, run tests, and then clean up, you can use the following commands:
```sh
make all
make tests
make clean
```

To generate the Javadoc and then clean it up, use:
```sh
make javadoc
make javadoclean
```

For more details, refer to the Makefile in the project directory.


