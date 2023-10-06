package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspComparison extends AspSyntax {
    public ArrayList<AspTerm> aspTerms = new ArrayList<>();
    public ArrayList<AspCompOpr> compOprs = new ArrayList<>();
    
    public AspComparison(int n) {
        super(n);
    }

    public static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison comparison = new AspComparison(s.curLineNum());

        // TODO fix while true loop (do while?)
        while (true) {
            comparison.aspTerms.add(AspTerm.parse(s));
            if (AspCompOpr.isCompOpr(s.curToken().kind))
                comparison.compOprs.add(AspCompOpr.parse(s));
            else
                break;
        }

        leaveParser("comparison");
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
