package no.uio.ifi.asp.parser.aspstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt.AspCompoundStmt;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public abstract class AspStmt extends AspSyntax {
    public AspStmt(int n) {
        super(n);
    }

    public static AspStmt parse(Scanner s) {
        enterParser("stmt");

        AspStmt stmt = null;

        TokenKind curTokenKind = s.curToken().kind;
        if (curTokenKind == forToken ||
            curTokenKind == defToken ||
            curTokenKind == ifToken ||
            curTokenKind == whileToken)
            stmt = AspCompoundStmt.parse(s);
        else
            stmt = AspSmallStmtList.parse(s);

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
