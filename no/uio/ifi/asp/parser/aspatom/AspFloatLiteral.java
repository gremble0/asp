package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.floatToken;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspFloatLiteral extends AspAtom {
    double floatLit;

    public AspFloatLiteral(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspFloatLiteral}
      * @return  {@code AspFloatLiteral} with the literal value of the scanners current token
      */
    public static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");
        AspFloatLiteral floatLiteral = new AspFloatLiteral(s.curLineNum());

        floatLiteral.floatLit = s.curToken().floatLit;
        skip(s, floatToken);
        
        leaveParser("float literal");
        return floatLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(Double.toString(floatLit));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
