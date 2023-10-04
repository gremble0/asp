package no.uio.ifi.asp.parser.aspsmallstmt;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspPassStmt extends AspSmallStmt {
    public AspPassStmt(int n) {
        super(n);
    }

    public static AspPassStmt parse(Scanner s) {
        enterParser("small stmt");

        AspPassStmt smallStmt = new AspPassStmt(s.curLineNum());

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
