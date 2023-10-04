package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

public abstract class AspWhileStmt extends AspCompoundStmt {
    public AspWhileStmt(int n) {
        super(n);
    }

    public static AspWhileStmt parse(Scanner s) {
        enterParser("while stmt");

        AspWhileStmt whileStmt = null;
        
        leaveParser("while stmt");

        return whileStmt;
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
