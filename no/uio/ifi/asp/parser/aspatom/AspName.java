package no.uio.ifi.asp.parser.aspatom;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspName extends AspAtom {
    private String tokName;
    
    public AspName(int n) {
        super(n);
    }

    public static AspName parse(Scanner s) {
        enterParser("name");

        AspName name = new AspName(s.curLineNum());
        name.tokName = s.curToken().name;
        
        leaveParser("name");
        return name;
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
