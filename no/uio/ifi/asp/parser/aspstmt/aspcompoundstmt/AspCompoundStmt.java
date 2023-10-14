package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public abstract class AspCompoundStmt extends AspStmt {
    public AspCompoundStmt(int n) {
        super(n);
    }

    /**
      * Calls the appropriate {@code AspCompoundStmt} subclass parse method
      *
      * @param s {@code Scanner} used and mutated to parse the {@code AspCompoundStmt}
      * @return  Instance of a subclass of {@code AspCompoundStmt} containing
      *          information about the compound statement
      */
    public static AspStmt parse(Scanner s) {
        enterParser("compound stmt");
        AspStmt stmt = null;

        switch (s.curToken().kind) {
        case forToken:
            stmt = AspForStmt.parse(s);
            break;
        case ifToken:
            stmt = AspIfStmt.parse(s);
            break;
        case whileToken:
            stmt = AspWhileStmt.parse(s);
            break;
        case defToken:
            stmt = AspFuncDef.parse(s);
            break;
        default: parserError("Illegal token for compound statement: " + s.curToken().kind, s.curLineNum());
        }
        
        leaveParser("compound stmt");
        return stmt;
    }

    public static boolean isCompoundStmt(TokenKind tk) {
        if (tk == forToken || tk == defToken || tk == ifToken || tk == whileToken)
            return true;

        return false;
    }
}
