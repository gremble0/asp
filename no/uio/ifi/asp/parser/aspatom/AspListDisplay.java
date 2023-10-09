package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspListDisplay extends AspAtom {
    private ArrayList<AspExpr> exprs = new ArrayList<AspExpr>();
    
    public AspListDisplay(int n) {
        super(n);
    }

    public static AspListDisplay parse(Scanner s) {
        enterParser("list display");
        AspListDisplay listDisplay = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);

        while (s.curToken().kind != rightBracketToken) {
            listDisplay.exprs.add(AspExpr.parse(s));

            test(s, commaToken, rightBracketToken);
            ignore(s, commaToken);
        }

        skip(s, rightBracketToken);
        
        leaveParser("list display");
        return listDisplay;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("[");

        int n = 0;
        while (n < exprs.size()) {
            exprs.get(n).prettyPrint();
            if (n != exprs.size() - 1)
                prettyWrite(", ");
            ++n;
        }
        
        prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
