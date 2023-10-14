package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.stringToken;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspStringLiteral extends AspAtom {
    String stringLit;
    
    AspStringLiteral(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspStringLiteral}
      * @return  {@code AspStringLiteral} with the literal value of the scanners current token
      */
    public static AspStringLiteral parse(Scanner s) {
        enterParser("string literal");
        AspStringLiteral stringLiteral = new AspStringLiteral(s.curLineNum());

        stringLiteral.stringLit = s.curToken().stringLit;
        skip(s, stringToken);

        leaveParser("string literal");
        return stringLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("\"");
        prettyWrite(stringLit);
        prettyWrite("\"");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
