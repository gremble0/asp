package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.integerToken;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspIntegerLiteral extends AspAtom {
    long integerLit;
    
    public AspIntegerLiteral(int n) {
        super(n);
    }

    public static AspIntegerLiteral parse(Scanner s) {
        enterParser("integer literal");
        AspIntegerLiteral integerLiteral = new AspIntegerLiteral(s.curLineNum());

        integerLiteral.integerLit = s.curToken().integerLit;
        skip(s, integerToken);

        leaveParser("integer literal");
        return integerLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(Long.toString(integerLit));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
