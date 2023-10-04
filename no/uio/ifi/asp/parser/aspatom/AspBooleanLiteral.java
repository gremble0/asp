package no.uio.ifi.asp.parser.aspatom;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspBooleanLiteral extends AspAtom {
    private boolean tokBool;
    
    public AspBooleanLiteral(int n) {
        super(n);
    }

    public static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");

        AspBooleanLiteral booleanLiteral = new AspBooleanLiteral(s.curLineNum());

        if (s.curToken().kind == trueToken)
            booleanLiteral.tokBool = true;
        else
            booleanLiteral.tokBool = false;

        leaveParser("boolean literal");
        return booleanLiteral;
    }

    @Override
    public void prettyPrint() {
        if (tokBool)
            prettyWrite("True");
        else
            prettyWrite("False");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
