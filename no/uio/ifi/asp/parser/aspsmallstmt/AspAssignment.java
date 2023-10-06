package no.uio.ifi.asp.parser.aspsmallstmt;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.aspatom.AspName;
import no.uio.ifi.asp.parser.aspprimarysuffix.AspSubscription;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.
runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspAssignment extends AspSmallStmt {
    public AspName name;
    public ArrayList<AspSubscription> subscriptions;
    public AspExpr expr;
    
    public AspAssignment(int n) {
        super(n);
    }

    public static AspAssignment parse(Scanner s) {
        enterParser("assignment");

        AspAssignment assignment = new AspAssignment(s.curLineNum());
        assignment.name = AspName.parse(s);

        // TODO: Handle subscriptions
        // while ... rightBracketToken ... 
        
        skip(s, equalToken);
        assignment.expr = AspExpr.parse(s);

        leaveParser("assignment");
        return assignment;
    }

    @Override
    public void prettyPrint() {
        name.prettyPrint();
        // TODO uncomment when AspSubscription is written
        // for (AspSubscription subscription : subscriptions)
        //     subscription.prettyPrint();

        prettyWrite(" = ");
        
        expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
