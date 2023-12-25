default: clean Parser Lexer

Parser:
	cup -parser Parser PLXC.cup

Lexer:
	jflex PLXC.flex

clean:
	rm -f Parser.java sym.java Lexer.java

plx:
	../resources/plx

ctd:
	../resources/ctd