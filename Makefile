all:
	jflex src/Lexer1.flex
	javac src/LexicalAnalyzer.java

test:
	java src/LexicalAnalyzer.java test/Euclid.gls