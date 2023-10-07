package no.uio.ifi.asp.parser.aspsmallstmt;

import static no.uio.ifi.asp.scanner.TokenKind.globalToken;
import static no.uio.ifi.asp.scanner.TokenKind.newLineToken;
import static no.uio.ifi.asp.scanner.TokenKind.commaToken;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspatom.AspName;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspGlobalStmt extends AspSmallStmt {
    public ArrayList<AspName> globals = new ArrayList<>();

    public AspGlobalStmt(int n) {
        super(n);
    }

    public static AspGlobalStmt parse(Scanner s) {
        enterParser("global stmt");
        AspGlobalStmt globalStmt = new AspGlobalStmt(s.curLineNum());

        skip(s, globalToken);
        while (s.curToken().kind != newLineToken) {
            globalStmt.globals.add(AspName.parse(s));
            test(s, commaToken, newLineToken);
            if (s.curToken().kind == commaToken)
                s.readNextToken();
        }

        leaveParser("global stmt");
        return globalStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("global ");

        int n = 0;
        while (n < globals.size()) {
            globals.get(n).prettyPrint();
            if (n != globals.size() - 1)
                prettyWrite(", ");
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
