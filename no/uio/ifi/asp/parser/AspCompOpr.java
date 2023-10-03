package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspCompOpr extends AspSyntax {
    // ArrayList<AspFactorPrefix> prefixes = new ArrayList<AspFactorPrefix>();
    // ArrayList<AspPrimary> primaries = new ArrayList<AspPrimary>();
    
    public AspCompOpr(int n) {
        super(n);
    }

    static AspCompOpr parse(Scanner s) {
        return null;
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
