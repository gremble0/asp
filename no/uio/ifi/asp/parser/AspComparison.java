package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspComparison extends AspSyntax {
    public ArrayList<AspTerm> terms = new ArrayList<>();
    public ArrayList<AspCompOpr> compOprs = new ArrayList<>();
    
    public AspComparison(int n) {
        super(n);
    }

    public static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison comparison = new AspComparison(s.curLineNum());

        // TODO refactor while true loop
        while (true) {
            comparison.terms.add(AspTerm.parse(s));

            if (!AspCompOpr.isCompOpr(s.curToken().kind))
                break;

            comparison.compOprs.add(AspCompOpr.parse(s));
        }

        leaveParser("comparison");
        return comparison;
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < terms.size()) {
            if (n > 0)
                compOprs.get(n - 1).prettyPrint();

            terms.get(n).prettyPrint();
            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
