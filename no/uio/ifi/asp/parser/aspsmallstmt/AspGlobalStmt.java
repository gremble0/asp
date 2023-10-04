package no.uio.ifi.asp.parser.aspsmallstmt;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspGlobalStmt extends AspSmallStmt {
    public AspGlobalStmt(int n) {
        super(n);
    }

    public static AspGlobalStmt parse(Scanner s) {
        enterParser("global stmt");

        AspGlobalStmt globalStmt = new AspGlobalStmt(s.curLineNum());

        leaveParser("global stmt");
        return globalStmt;
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
