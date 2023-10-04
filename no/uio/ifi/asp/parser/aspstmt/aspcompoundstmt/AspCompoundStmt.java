package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

public abstract class AspCompoundStmt extends AspStmt {
    public AspCompoundStmt(int n) {
        super(n);
    }

    public static AspStmt parse(Scanner s) {
        enterParser("stmt");

        AspStmt stmt = null;

        // TODO some logic to call either AspSmallStmtList.parse or AspCompoundStmt.parse
        if (s.curToken().kind == TokenKind.defToken)
            stmt = new AspFuncDef(s.curLineNum());
        
        leaveParser("stmt");

        return stmt;
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
