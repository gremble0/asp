package no.uio.ifi.asp.parser.aspsmallstmt;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspSmallStmt extends AspSyntax {
    public AspSmallStmt(int n) {
        super(n);
    }

    public static AspSmallStmt parse(Scanner s) {
        enterParser("small stmt");
        AspSmallStmt smallStmt = null;

        if (s.anyEqualToken())
            smallStmt = AspAssignment.parse(s);
        else if (s.curToken().kind == globalToken)
            smallStmt = AspGlobalStmt.parse(s);
        else if (s.curToken().kind == passToken)
            smallStmt = AspPassStmt.parse(s);
        else if (s.curToken().kind == returnToken)
            smallStmt = AspReturnStmt.parse(s);
        else
            smallStmt = AspExprStmt.parse(s);

        leaveParser("small stmt");
        return smallStmt;
    }
}
