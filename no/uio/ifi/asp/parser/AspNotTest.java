package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.notToken;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspNotTest extends AspSyntax {
    boolean not;
    AspComparison comparison;
    
    public AspNotTest(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspNotTest}
      * @return  {@code AspNotTest} with parsed information about the not test
      *          including whether its negated or not (prefixed by a not token) and
      *          the {@code AspComparison}
      */
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
        RuntimeValue v = comparison.eval(curScope);
        if (not)
            v = v.evalNot(this);

        return v;
    }
}
