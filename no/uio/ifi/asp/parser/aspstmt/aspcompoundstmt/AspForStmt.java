package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.aspatom.AspName;
import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.parser.aspsuite.AspSuite;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeDictValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeListValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeStringValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeIntValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspForStmt extends AspCompoundStmt {
    public AspName iterator;
    public AspExpr iterable;
    public AspSuite body;
    
    public AspForStmt(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspForStmt}
      * @return  {@code AspForStmt} with parsed information about the for loop
      *          including its iterator, iterable and body
      */
    public static AspStmt parse(Scanner s) {
        enterParser("for stmt");
        AspForStmt forStmt = new AspForStmt(s.curLineNum());

        skip(s, forToken);
        forStmt.iterator = AspName.parse(s);
        skip(s, inToken);
        forStmt.iterable = AspExpr.parse(s);
        skip(s, colonToken);
        forStmt.body = AspSuite.parse(s);

        leaveParser("for stmt");
        return forStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("for ");
        iterator.prettyPrint();
        prettyWrite(" in ");
        iterable.prettyPrint();
        prettyWrite(": ");
        body.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeIterable = iterable.eval(curScope);
        if (runtimeIterable instanceof RuntimeListValue) {
            return evalList(curScope, (RuntimeListValue)runtimeIterable);
        } else if (runtimeIterable instanceof RuntimeDictValue) {
            return evalDict(curScope, (RuntimeDictValue)runtimeIterable);
        }

        RuntimeValue.runtimeError("Cannot iterate over expression of type: " + runtimeIterable.typeName(), body);
        return null; // required by the compiler
    }
    
    private RuntimeValue evalDict(RuntimeScope curScope, RuntimeDictValue dict) throws RuntimeReturnValue {
        RuntimeValue it;
        int n = 0;
        ArrayList<RuntimeStringValue> keys = dict.getDictKeys("for loop", this);
        while (n < dict.evalLen(this).getIntValue("for loop", this)) {
            it = dict.evalSubscription(keys.get(n), body);
            curScope.assign(iterator.name, it);
            body.eval(curScope);
        }

        return null;
    }

    private RuntimeValue evalList(RuntimeScope curScope, RuntimeListValue list) throws RuntimeReturnValue {
        RuntimeValue it;
        int n = 0;
        while (n < list.evalLen(this).getIntValue("for loop", this)) {
            it = list.evalSubscription(new RuntimeIntValue(n++), body);
            curScope.assign(iterator.name, it);
            body.eval(curScope);
        }

        return null;
    }
}
