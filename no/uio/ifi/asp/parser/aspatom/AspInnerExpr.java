package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspInnerExpr extends AspAtom {
    public AspExpr expr;
    
    public AspInnerExpr(int n) {
        super(n);
    }

    public static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr innerExpr = new AspInnerExpr(s.curLineNum());

        skip(s, leftParToken);
        innerExpr.expr = AspExpr.parse(s);
        skip(s, rightParToken);
        
        leaveParser("inner expr");
        return innerExpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("(");
        expr.prettyPrint();
        prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
