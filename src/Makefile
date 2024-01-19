default: clean Parser Lexer

Parser:
	cup PLXC.cup

Lexer:
	jflex PLXC.flex

clean:
	rm -f Parser.java sym.java Lexer.java

plx:
	../resources/plx

ctd:
	../resources/ctd

entrega:
	rm -f ../entrega/plxc.zip
	zip -r ../entrega/plxc.zip . -x Makefile Chueleta.java Lexer.java Parser.java sym.java test1.plxc
