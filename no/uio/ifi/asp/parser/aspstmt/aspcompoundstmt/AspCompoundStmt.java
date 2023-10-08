package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import no.uio.ifi.asp.parser.aspstmt.AspStmt;
import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspCompoundStmt extends AspStmt {
    public AspCompoundStmt(int n) {
        super(n);
    }

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
}
