package no.uio.ifi.asp.parser.aspprimarysuffix;

import static no.uio.ifi.asp.scanner.TokenKind.leftParToken;
import static no.uio.ifi.asp.scanner.TokenKind.rightParToken;
import static no.uio.ifi.asp.scanner.TokenKind.commaToken;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspArguments extends AspPrimarySuffix {
    public ArrayList<AspExpr> args = new ArrayList<>();
    
    public AspArguments(int n) {
        super(n);
    }

    public static AspArguments parse(Scanner s) {
        enterParser("arguments");
        AspArguments arguments = new AspArguments(s.curLineNum());

        skip(s, leftParToken);

        while (s.curToken().kind != rightParToken) {
            arguments.args.add(AspExpr.parse(s));

            if (s.curToken().kind == commaToken)
                s.readNextToken();
        }
        
        skip(s, rightParToken);

        leaveParser("arguments");
        return arguments;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("(");
        
        int n = 0;
        while (n < args.size()) {
            args.get(n).prettyPrint();
            if (n < args.size() - 1)
                prettyWrite(", ");
            ++n;
        }

        prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
