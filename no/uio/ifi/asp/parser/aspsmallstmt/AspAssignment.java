package no.uio.ifi.asp.parser.aspsmallstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.aspatom.AspName;
import no.uio.ifi.asp.parser.aspprimarysuffix.AspSubscription;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeDictValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeListValue;
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

    /**
     * @param curScope {@code RuntimeScope} to add the binding to
     *
     * Adds a binding from {@code this.name.name} to the result of {@code this.expr.eval(curScope)}
     * if {@code this.subscriptions.size() > 0}, update the value the subscription(s) point to.
     */
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        String tracedAssignment = name.name;
        RuntimeValue val = expr.eval(curScope);
        if (subscriptions.size() != 0) {
            RuntimeValue var = curScope.find(name.name, this);

            int n = 0;
            while (n < subscriptions.size()) {
                if (!(var instanceof RuntimeListValue) && !(var instanceof RuntimeDictValue)) // TODO: collection, also check that the index is string for dict and int for list
                    RuntimeValue.runtimeError("Variable of type '" + var.typeName() +
                                              "' does not support subscription", this);

                if (n == subscriptions.size() - 1)
                    var.evalAssignElem(subscriptions.get(n).eval(curScope), val, this);

                RuntimeValue subscription = subscriptions.get(n++).eval(curScope);
                var = var.evalSubscription(subscription, this);
                tracedAssignment += "[" + subscription + "]";
            }

        } else {
            curScope.assign(name.name, val);
        }

        trace(tracedAssignment + " = " + val.showInfo());
        
        return null;
    }
}
