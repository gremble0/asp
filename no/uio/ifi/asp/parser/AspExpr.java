package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.orToken;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspExpr extends AspSyntax {
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    public AspExpr(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspExpr}
      * @return  {@code AspExpr} with parsed information about the expression
      *          including all {@code AspAndTest}s
      */
    public static AspExpr parse(Scanner s) {
        enterParser("expr");
        AspExpr expr = new AspExpr(s.curLineNum());

        do {
            ignore(s, orToken);

            expr.andTests.add(AspAndTest.parse(s));
        } while(s.curToken().kind == orToken);

        leaveParser("expr");
        return expr;
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < andTests.size()) {
            if (n > 0)
                prettyWrite(" or ");

            andTests.get(n).prettyPrint();
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = andTests.get(0).eval(curScope);
        for (int i = 1; i < andTests.size(); ++i) {
            if (v.getBoolValue("or operand", this))
                return v;

            v = andTests.get(i).eval(curScope);
        }

        return v;
    }
}
