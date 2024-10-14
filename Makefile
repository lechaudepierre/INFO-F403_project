all:
	jflex src/Lexer1.flex
	javac src/LexicalAnalyzer.java

test:
	java src/LexicalAnalyzer test/Euclid.gls