package no.uio.ifi.asp.parser.aspsmallstmt;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspExprStmt extends AspSmallStmt {
    public AspExpr expr;
    
    public AspExprStmt(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspExprStm}
      * @return  {@code AspExprStmt} containing the parsed {@code AspExpr}
      */
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
        return expr.eval(curScope);
    }
}
