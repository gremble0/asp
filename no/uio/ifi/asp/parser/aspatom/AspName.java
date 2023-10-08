package no.uio.ifi.asp.parser.aspatom;

import static no.uio.ifi.asp.scanner.TokenKind.nameToken;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspName extends AspAtom {
    public String name;
    
    public AspName(int n) {
        super(n);
    }

    public static AspName parse(Scanner s) {
        enterParser("name");
        AspName name = new AspName(s.curLineNum());

        name.name = s.curToken().name;
        skip(s, nameToken);
        
        leaveParser("name");
        return name;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(name);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
