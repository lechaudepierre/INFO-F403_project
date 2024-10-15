# Variables
JAVAC = javac
JFLAGS = -Xlint
JFLEX = jflex
LEXER_FILE = src/LexicalAnalyzer
MAIN_CLASS = src/Main
SYMBOL_CLASS = src/Symbol
LEXICALUNIT_CLASS = src/LexicalUnit
SOURCES = $(LEXER_FILE).java $(MAIN_CLASS).java $(SYMBOL_CLASS).java $(LEXICALUNIT_CLASS).java
INPUT_FILE = test/Euclid.gls

# Cible par défaut (compiler tout)
all: $(LEXER_FILE).java $(MAIN_CLASS).class

# Génération du fichier LexicalAnalyzer.java à partir du fichier JFlex
$(LEXER_FILE).java: $(LEXER_FILE).flex
	$(JFLEX) $(LEXER_FILE).flex

# Compilation des fichiers Java
$(MAIN_CLASS).class: $(SOURCES)
	$(JAVAC) $(JFLAGS) $(SOURCES)

# Exécution du programme avec un fichier txt en argument
run: $(MAIN_CLASS).class
	java -cp src Main $(INPUT_FILE)

# Nettoyage des fichiers générés (.class et le fichier Java généré par JFlex)
clean:
	rm -f src/*.class $(LEXER_FILE).java
