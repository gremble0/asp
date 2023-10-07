package no.uio.ifi.asp.parser.aspsmallstmt;

import static no.uio.ifi.asp.scanner.TokenKind.returnToken;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspReturnStmt extends AspSmallStmt {
    public AspExpr expr;
    
    public AspReturnStmt(int n) {
        super(n);
    }

    public static AspReturnStmt parse(Scanner s) {
        enterParser("return stmt");
        AspReturnStmt returnStmt = new AspReturnStmt(s.curLineNum());

        skip(s, returnToken);
        returnStmt.expr = AspExpr.parse(s);

        leaveParser("return stmt");
        return returnStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("return ");
        expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
