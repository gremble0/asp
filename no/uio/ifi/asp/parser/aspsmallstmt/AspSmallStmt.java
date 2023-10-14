package no.uio.ifi.asp.parser.aspsmallstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspSmallStmt extends AspSyntax {
    public AspSmallStmt(int n) {
        super(n);
    }

    /**
      * Calls the appropriate {@code AspSmallStmt} subclass parse method
      *
      * @param s {@code Scanner} used and mutated to parse the {@code AspSmallStmt}
      * @return  Instance of a subclass of {@code AspSmallStmt} containing
      *          information about the small statement
      */
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
