package no.uio.ifi.asp.parser.aspatom;

import java.util.ArrayList;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;


public class AspDictDisplay extends AspAtom {
    ArrayList<AspStringLiteral> keys = new ArrayList<>();
    ArrayList<AspExpr> values = new ArrayList<>();
    
    public AspDictDisplay(int n) {
        super(n);
    }

    public static AspDictDisplay parse(Scanner s) {
        enterParser("dict display");
        AspDictDisplay dictDisplay = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);

        while (s.curToken().kind != rightBraceToken) {
            dictDisplay.keys.add(AspStringLiteral.parse(s));
            skip(s, colonToken);
            dictDisplay.values.add(AspExpr.parse(s));

            test(s, commaToken, rightBraceToken);
            if (s.curToken().kind == commaToken)
                s.readNextToken();
        }

        skip(s, rightBraceToken);
        
        leaveParser("dict display");
        return dictDisplay;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("{");

        int n = 0;
        while (n < keys.size()) {
            keys.get(n).prettyPrint();
            prettyWrite(":");
            values.get(n).prettyPrint();
            if (n != keys.size() - 1)
                prettyWrite(", ");
            ++n;
        }
        prettyWrite("}");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
