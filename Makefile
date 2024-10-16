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

# Exécution du programme à partir du fichier JAR avec un fichier .gls en argument
run: $(OUTPUT_JAR)
	java -jar $(OUTPUT_JAR) $(INPUT_FILE)

# Nettoyage des fichiers générés (.class, .java et le fichier JAR)
clean:
	rm -f src/*.class $(LEXER_FILE).java
