package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

public class AspForStmt extends AspCompoundStmt {
    public AspForStmt(int n) {
        super(n);
    }

    public static AspStmt parse(Scanner s) {
        enterParser("for stmt");

        AspForStmt forStmt = null;
        
        leaveParser("for stmt");

        return forStmt;
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
