package no.uio.ifi.asp.parser.aspstmt;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt.AspCompoundStmt;
import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspStmt extends AspSyntax {
    public AspStmt(int n) {
        super(n);
    }

    /**
      * Calls the appropriate {@code AspStmt} subclass parse method
      *
      * @param s {@code Scanner} used and mutated to parse the {@code AspStmt}
      * @return  Instance of a subclass of {@code AspStmt} containing
      *          information about the statement
      */
    public static AspStmt parse(Scanner s) {
        enterParser("stmt");
        AspStmt stmt = null;

        if (AspCompoundStmt.isCompoundStmt(s.curToken().kind))
            stmt = AspCompoundStmt.parse(s);
        else
            stmt = AspSmallStmtList.parse(s);

        leaveParser("stmt");
        return stmt;
    }
}
