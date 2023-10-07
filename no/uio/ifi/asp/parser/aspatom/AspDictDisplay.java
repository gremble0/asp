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

        // TODO: progress the scanner? Or maybe only progress in atoms?
        while (s.curToken().kind != rightBracketToken) {
            dictDisplay.keys.add(AspStringLiteral.parse(s));
            dictDisplay.values.add(AspExpr.parse(s));
        }
        
        leaveParser("dict display");
        return dictDisplay;
    }

    @Override
    public void prettyPrint() {
        int n = 0;

        prettyWrite("{\n");
        while (keys.get(n) != null) {
            keys.get(n).prettyPrint();
            prettyWrite(": ");
            values.get(n).prettyPrint();
        }
        prettyWrite("}");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
