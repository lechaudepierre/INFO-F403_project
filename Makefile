# Variables
JAVAC = javac
JFLAGS = -Xlint
JFLEX = jflex
JAR = jar
LEXER_FILE = src/LexicalAnalyzer
MAIN_CLASS = src/Main
SYMBOL_CLASS = src/Symbol
LEXICALUNIT_CLASS = src/LexicalUnit
SOURCES = $(LEXER_FILE).java $(MAIN_CLASS).java $(SYMBOL_CLASS).java $(LEXICALUNIT_CLASS).java
INPUT_FILE = test/Euclid.gls
OUTPUT_JAR = dist/part1.jar
TEST_DIR = test/test1
TEST_FILES := $(wildcard $(TEST_DIR)/*.gls)
JAVA_PROGRAM = Main
SRC_DIR = src

# Cible par défaut (compiler tout)
all: $(LEXER_FILE).java $(OUTPUT_JAR)

# Génération du fichier LexicalAnalyzer.java à partir du fichier JFlex
$(LEXER_FILE).java: $(LEXER_FILE).flex
	$(JFLEX) $(LEXER_FILE).flex

# Compilation des fichiers Java
$(MAIN_CLASS).class: $(SOURCES)
	$(JAVAC) $(JFLAGS) $(SOURCES)

# Création du dossier dist si nécessaire et génération du fichier JAR dans ce dossier
$(OUTPUT_JAR): $(MAIN_CLASS).class
	$(JAR) cfe $(OUTPUT_JAR) Main -C src .

# Compilation des fichiers avant les tests
compile: $(MAIN_CLASS).class

# Exécuter les tests sur tous les fichiers .gls dans le répertoire de test
tests: compile
	for test_file in $(TEST_FILES); do \
		echo "\nTesting $$test_file\n"; \
		java -cp $(SRC_DIR) $(JAVA_PROGRAM) "$$test_file"; \
	done
	echo "Done testing"

# Exécution du programme à partir du fichier JAR avec un fichier .gls en argument
run: $(OUTPUT_JAR)
	java -jar $(OUTPUT_JAR) $(INPUT_FILE)

# Nettoyage des fichiers générés (.class, .java et le fichier JAR)
clean:
	rm -f src/*.class $(LEXER_FILE).java
	echo "Done cleaning"
