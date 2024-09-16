# Compiler flags
JAVAC = javac
JFLAGS = -d bin -cp src/main/java:src/test/java

# Class to run
MAINCLASS = test.java.com.example.Tests

# Find Java source files
SOURCES = $(shell find src/main/java -name '*.java') $(shell find src/test/java -name '*.java')

# Main target
all: $(SOURCES)
	$(JAVAC) $(JFLAGS) $^

run: all
	java -cp bin $(MAINCLASS)

# Clean target
clean:
	rm -rf bin