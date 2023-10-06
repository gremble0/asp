package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspTerm extends AspSyntax {
    ArrayList<AspFactor> factors = new ArrayList<AspFactor>();

    public AspTerm(int n) {
        super(n);
    }
    
    static AspTerm parse(Scanner s) {
        enterParser("term");
        AspTerm term = new AspTerm(s.curLineNum());

        // TODO fix while true loop (do while?)
        while (true) {
            term.factors.add(AspFactor.parse(s));
            if (!AspCompOpr.isCompOpr(s.curToken().kind))
                break;
        }
        
        leaveParser("term");
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
