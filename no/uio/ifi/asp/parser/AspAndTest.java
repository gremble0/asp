package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
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

        for (AspNotTest ant: notTests) {
            if (n > 0)
                prettyWrite(" and ");
            ant.prettyPrint();
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
