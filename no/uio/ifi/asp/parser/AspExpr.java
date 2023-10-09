package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.orToken;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspExpr extends AspSyntax {
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    public AspExpr(int n) {
        super(n);
    }

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
        return null;
    }
}
