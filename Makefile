all:
	jflex src/Lexer1.flex
	javac src/Lexer1.java

test:
	java Lexer1 test/Euclid.gls