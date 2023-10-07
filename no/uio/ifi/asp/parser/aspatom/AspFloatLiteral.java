package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.floatToken;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspFloatLiteral extends AspAtom {
    double tokFloat;

    public AspFloatLiteral(int n) {
        super(n);
    }

    public static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");
        AspFloatLiteral floatLiteral = new AspFloatLiteral(s.curLineNum());

        floatLiteral.tokFloat = s.curToken().floatLit;
        skip(s, floatToken);
        
        leaveParser("float literal");
        return floatLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(Double.toString(tokFloat));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
