package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> aspTerms = new ArrayList<AspTerm>();
    
    public AspComparison(int n) {
        super(n);
    }

    public static AspComparison parse(Scanner s) {
        return null;
    }

    @Override
    public void prettyPrint() {
        int n = 0;

        for (AspTerm aspTerm: aspTerms) {
            if (n > 0)
                prettyWrite(" and ");
            aspTerm.prettyPrint();
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
