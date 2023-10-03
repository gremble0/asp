package no.uio.ifi.asp.parser.aspatom;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspStringLiteral extends AspAtom {
    String tokStringLit;
    
    AspStringLiteral(int n) {
        super(n);
    }

    public static AspStringLiteral parse(Scanner s) {
        enterParser("string literal");

        AspStringLiteral stringLiteral = new AspStringLiteral(s.curLineNum());
        stringLiteral.tokStringLit = s.curToken().stringLit;

        leaveParser("string literal");
        return stringLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(String.format("\"%s\"", tokStringLit));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
