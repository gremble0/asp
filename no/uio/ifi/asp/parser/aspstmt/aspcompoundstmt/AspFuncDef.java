package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

public abstract class AspFuncDef extends AspCompoundStmt {
    public AspFuncDef(int n) {
        super(n);
    }

    public static AspFuncDef parse(Scanner s) {
        enterParser("func def");

        AspFuncDef funcDef = null;
        
        leaveParser("func def");

        return funcDef;
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
