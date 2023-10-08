package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.notToken;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspNotTest extends AspSyntax {
    boolean not;
    AspComparison comparison;
    
    public AspNotTest(int n) {
        super(n);
    }

    public static AspNotTest parse(Scanner s) {
        enterParser("not test");
        AspNotTest notTest = new AspNotTest(s.curLineNum());

        if (s.curToken().kind == notToken) {
            notTest.not = true;
            skip(s, notToken);
        } else {
            notTest.not = false;
        }

        notTest.comparison = AspComparison.parse(s);
        
        leaveParser("not test");
        return notTest;
    }

    @Override
    public void prettyPrint() {
        if (not)
            prettyWrite("not ");

        comparison.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
