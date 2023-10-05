package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.parser.AspSuite;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

public class AspWhileStmt extends AspCompoundStmt {
    public AspExpr test;
    public AspSuite body;
    
    public AspWhileStmt(int n) {
        super(n);
    }

    public static AspWhileStmt parse(Scanner s) {
        enterParser("while stmt");
        AspWhileStmt whileStmt = new AspWhileStmt(s.curLineNum());

        skip(s, whileToken);
        whileStmt.test = AspExpr.parse(s);
        skip(s, colonToken);
        whileStmt.body = AspSuite.parse(s);
        
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
