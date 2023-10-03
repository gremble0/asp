package no.uio.ifi.asp.parser.aspatom;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspExpr;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDictDisplay extends AspAtom {
    ArrayList<AspStringLiteral> stringLiterals = new ArrayList<>();
    ArrayList<AspExpr> expressions = new ArrayList<>();
    
    public AspDictDisplay(int n) {
        super(n);
    }

    public static AspDictDisplay parse(Scanner s) {
        enterParser("dict display");

        AspDictDisplay dictDisplay = new AspDictDisplay(s.curLineNum());
        // TODO: progress the scanner? Or maybe only progress in atoms?
        while (s.curToken().kind != rightBracketToken) {
            dictDisplay.stringLiterals.add(AspStringLiteral.parse(s));
            dictDisplay.expressions.add(AspExpr.parse(s));
        }
        
        leaveParser("dict display");
        return dictDisplay;
    }

    @Override
    public void prettyPrint() {

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
