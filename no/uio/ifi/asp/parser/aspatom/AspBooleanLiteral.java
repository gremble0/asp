package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.trueToken;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspBooleanLiteral extends AspAtom {
    private boolean booleanLit;
    
    public AspBooleanLiteral(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspBooleanLiteral}
      * @return  {@code AspBooleanLiteral} with the literal value of the scanners current token
      */
    public static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral booleanLiteral = new AspBooleanLiteral(s.curLineNum());

        if (s.curToken().kind == trueToken)
            booleanLiteral.booleanLit = true;
        else
            booleanLiteral.booleanLit = false;

        s.readNextToken();

        leaveParser("boolean literal");
        return booleanLiteral;
    }

    @Override
    public void prettyPrint() {
        if (booleanLit)
            prettyWrite("True");
        else
            prettyWrite("False");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
