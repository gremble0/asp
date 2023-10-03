package no.uio.ifi.asp.parser.aspatom;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspBooleanLiteral extends AspAtom {
    public AspBooleanLiteral(int n) {
        super(n);
    }

    public static AspBooleanLiteral parse(Scanner s) {
        return null;
    }

    @Override
    public void prettyPrint() {

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
