package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspatom.AspName;
import no.uio.ifi.asp.parser.aspsuite.AspSuite;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeFunc;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeNoneValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspFuncDef extends AspCompoundStmt {
    public AspName funcName;
    public ArrayList<AspName> params = new ArrayList<>();
    public AspSuite body;
    
    public AspFuncDef(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspFuncDef}
      * @return  {@code AspFuncDef} with parsed information about the function
      *          including its name, parameters and body
      */
    public static AspFuncDef parse(Scanner s) {
        enterParser("func def");
        AspFuncDef funcDef = new AspFuncDef(s.curLineNum());

        skip(s, defToken);
        funcDef.funcName = AspName.parse(s);
        skip(s, leftParToken);

        while (s.curToken().kind != rightParToken) {
            funcDef.params.add(AspName.parse(s));

            test(s, commaToken, rightParToken);
            ignore(s, commaToken);
        }

        skip(s, rightParToken);
        skip(s, colonToken);
        funcDef.body = AspSuite.parse(s);
        
        leaveParser("func def");
        return funcDef;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("def ");
        funcName.prettyPrint();
        // In my opinion its "prettier" to not have the space before the "("
        // but i'll add it to match the reference interpreter
        prettyWrite(" (");

        int n = 0;
        while (n < params.size()) {
            params.get(n).prettyPrint();
            if (n < params.size() - 1)
                prettyWrite(", ");
            ++n;
        }

        prettyWrite("): ");

        body.prettyPrint();
    }

    /**
     * Adds binding to this function definition in current scope.
     * Only called for side effects, always returns a new {@code RuntimeNoneValue}
     *
     * @param curScope Scope to bind function definition to
     */
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        trace("def " + funcName.name);
        curScope.assign(funcName.name, new RuntimeFunc(this, new RuntimeScope(curScope), funcName.name));
        return new RuntimeNoneValue();
    }
}
