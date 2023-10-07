package no.uio.ifi.asp.parser.aspsuite;

import no.uio.ifi.asp.parser.aspstmt.AspSmallStmtList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspSuiteSmallStmtList extends AspSuite {
    public AspSmallStmtList smallStmtList;
    
    public AspSuiteSmallStmtList(int n) {
        super(n);
    }

    /**
      * This method parses {@code AspSuite}s that consist of a {@code AspSmallStmtList}
      *
      * @param s {@code Scanner} used for parsing the {@code AspSuiteSmallStmtList}
      * @return  {@code AspSmallStmtList} with the body of a compound statement
      */
    public static AspSuiteSmallStmtList parse(Scanner s) {
        // To make the logfile represent the architectural changes uncomment the line below
        // enterParser("suite small stmt list");
        AspSuiteSmallStmtList suite = new AspSuiteSmallStmtList(s.curLineNum());

        suite.smallStmtList = AspSmallStmtList.parse(s);

        // To make the logfile represent the architectural changes uncomment the line below
        // leaveParser("suite small stmt list");
        return suite;
    }

    @Override
    public void prettyPrint() {
        smallStmtList.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
