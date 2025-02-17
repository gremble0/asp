package no.uio.ifi.asp.parser.aspprimarysuffix;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspSubscription extends AspPrimarySuffix {
    public AspExpr expr;
    
    public AspSubscription(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspSubscription}
      * @return  {@code AspSubscription} containing the parsed {@code AspExpr}
      */
    public static AspSubscription parse(Scanner s) {
        enterParser("subscription");
        AspSubscription subscription = new AspSubscription(s.curLineNum());

        skip(s, leftBracketToken);
        subscription.expr = AspExpr.parse(s);
        skip(s, rightBracketToken);

        leaveParser("subscription");
        return subscription;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("[");
        expr.prettyPrint();
        prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return expr.eval(curScope);
    }
}
