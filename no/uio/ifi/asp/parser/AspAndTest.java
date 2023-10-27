package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.andToken;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTests = new ArrayList<>();
    
    public AspAndTest(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspAndTest}
      * @return  {@code AspAndTest} with parsed information about all
      *          {@code AspNotTest}s
      */
    public static AspAndTest parse(Scanner s) {
        enterParser("and test");
        AspAndTest andTest = new AspAndTest(s.curLineNum());

        do {
            ignore(s, andToken);

            andTest.notTests.add(AspNotTest.parse(s));
        } while(s.curToken().kind == andToken);

        leaveParser("and test");
        return andTest;
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < notTests.size()) {
            if (n > 0)
                prettyWrite(" and ");

            notTests.get(n).prettyPrint();
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = notTests.get(0).eval(curScope);

        for (int i = 1; i < notTests.size(); ++i) {
            if (!v.getBoolValue("and operand", this))
                return v;

            v = notTests.get(i).eval(curScope);
        }

        return v;
    }
}
