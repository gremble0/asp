package no.uio.ifi.asp.parser.aspsmallstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.aspatom.AspName;
import no.uio.ifi.asp.parser.aspprimarysuffix.AspSubscription;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspAssignment extends AspSmallStmt {
    public AspName name;
    public ArrayList<AspSubscription> subscriptions = new ArrayList<>();
    public AspExpr expr;
    
    public AspAssignment(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspAssignment}
      * @return  {@code AspAssignment} containing the parsed {@code AspName} including
      *          any possible {@code AspSubscription}s and the {@code AspExpr} the name
      *          is assigned to.
      */
    public static AspAssignment parse(Scanner s) {
        enterParser("assignment");
        AspAssignment assignment = new AspAssignment(s.curLineNum());

        assignment.name = AspName.parse(s);

        while (s.curToken().kind == leftBracketToken)
            assignment.subscriptions.add(AspSubscription.parse(s));
        
        skip(s, equalToken);
        assignment.expr = AspExpr.parse(s);

        leaveParser("assignment");
        return assignment;
    }

    @Override
    public void prettyPrint() {
        name.prettyPrint();

        for (AspSubscription subscription : subscriptions)
            subscription.prettyPrint();

        prettyWrite(" = ");
        
        expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
