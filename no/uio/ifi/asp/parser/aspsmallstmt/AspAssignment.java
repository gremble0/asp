package no.uio.ifi.asp.parser.aspsmallstmt;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspAssignment extends AspSmallStmt {
    public AspAssignment(int n) {
        super(n);
    }

    public static AspAssignment parse(Scanner s) {
        enterParser("assignment");

        AspAssignment assignment = new AspAssignment(s.curLineNum());

        leaveParser("assignment");
        return assignment;
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
