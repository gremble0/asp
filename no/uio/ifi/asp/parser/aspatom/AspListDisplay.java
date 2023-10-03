package no.uio.ifi.asp.parser.aspatom;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {
    private ArrayList<AspExpr> expressions = new ArrayList<AspExpr>();
    
    public AspListDisplay(int n) {
        super(n);
    }

    public static AspListDisplay parse(Scanner s) {
        enterParser("list display");

        AspListDisplay listDisplay = new AspListDisplay(s.curLineNum());
        while (s.curToken().kind != rightBraceToken)
            listDisplay.expressions.add(AspExpr.parse(s));
        
        leaveParser("list display");
        return listDisplay;
    }

    @Override
    public void prettyPrint() {

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
