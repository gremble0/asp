package no.uio.ifi.asp.parser.aspsmallstmt;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspExprStmt extends AspSmallStmt {
    public AspExpr expr;
    
    public AspExprStmt(int n) {
        super(n);
    }

    public static AspExprStmt parse(Scanner s) {
        enterParser("expr stmt");
        AspExprStmt exprStmt = new AspExprStmt(s.curLineNum());

        exprStmt.expr = AspExpr.parse(s);

        leaveParser("expr stmt");
        return exprStmt;
    }

    @Override
    public void prettyPrint() {
        expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
