package no.uio.ifi.asp.parser.aspatom;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspListDisplay extends AspAtom {
    private ArrayList<AspExpr> expressions = new ArrayList<AspExpr>();
    
    public AspListDisplay(int n) {
        super(n);
    }

    public static AspListDisplay parse(Scanner s) {
        enterParser("list display");
        AspListDisplay listDisplay = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);

        while (s.curToken().kind != rightBracketToken) {
            listDisplay.expressions.add(AspExpr.parse(s));

            test(s, commaToken, rightBracketToken);
            if (s.curToken().kind == commaToken)
                s.readNextToken();
        }

        skip(s, rightBracketToken);
        
        leaveParser("list display");
        return listDisplay;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("[");

        int n = 0;
        while (n < expressions.size()) {
            expressions.get(n).prettyPrint();
            if (n != expressions.size() - 1)
                prettyWrite(", ");
            ++n;
        }
        
        prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
