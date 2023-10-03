package no.uio.ifi.asp.parser.aspatom;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspNoneLiteral extends AspAtom {
    public AspNoneLiteral(int n) {
        super(n);
    }

    public static AspNoneLiteral parse(Scanner s) {
        enterParser("none literal");

        leaveParser("none literal");
        return new AspNoneLiteral(s.curLineNum());
    }

    @Override
    public void prettyPrint() {
        prettyWrite(" None ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
