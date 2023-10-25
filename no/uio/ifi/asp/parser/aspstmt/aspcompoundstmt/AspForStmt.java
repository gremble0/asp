package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.aspatom.AspName;
import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.parser.aspsuite.AspSuite;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
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
        return null;
    }
}
