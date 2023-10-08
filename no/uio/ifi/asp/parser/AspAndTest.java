package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.andToken;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTests = new ArrayList<>();
    
    public AspAndTest(int n) {
        super(n);
    }

    public static AspAndTest parse(Scanner s) {
        enterParser("and test");
        AspAndTest andTest = new AspAndTest(s.curLineNum());

        // TODO refactor while true loop
        while (true) {
            andTest.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != andToken)
                break;
            skip(s, andToken);
        }

        leaveParser("and test");
        return andTest;
    }

    @Override
    public void prettyPrint() {
        int n = 0;

        // TODO convert to while loop
        for (AspNotTest notTest : notTests) {
            if (n > 0)
                prettyWrite(" and ");
            notTest.prettyPrint();
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
