package no.uio.ifi.asp.parser.aspsmallstmt;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspExprStmt extends AspSmallStmt {
    public AspExprStmt(int n) {
        super(n);
    }

    public static AspExprStmt parse(Scanner s) {
        enterParser("small stmt");

        AspExprStmt smallStmt = new AspExprStmt(s.curLineNum());

        leaveParser("small stmt");
        return smallStmt;
    }

    @Override
    public void prettyPrint() {

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
