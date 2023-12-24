default: clean Parser Lexer

Parser:
	cup -parser Parser PLXC.cup

Lexer:
	jflex PLC.flex

cleam:
	rm -f Parser.java sym.java Lexer.java

plx:
	../resources/plx

ctd:
	../resources/ctd