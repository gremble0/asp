// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser.aspstmt;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

public abstract class AspStmt extends AspSyntax {
    public AspStmt(int n) {
        super(n);
    }

    public static AspStmt parse(Scanner s) {
        enterParser("stmt");

        AspStmt statement = null;

        // TODO some logic to call either AspSmallStmtList.parse or AspCompoundStmt.parse
        
        leaveParser("stmt");

        return statement;
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
