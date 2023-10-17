package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.Scanner;

public class AspFactor extends AspSyntax {
    public ArrayList<AspFactorPrefix> prefixes = new ArrayList<>();
    public ArrayList<AspPrimary> primaries = new ArrayList<>();
    public ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();
    
    public AspFactor(int n) {
        super(n);
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspFactor}
      * @return  {@code AspFactor} with parsed information about the factor
      *          consisting of 1 or more {@code AspPrimary}s and any potential
      *          {@code AspFactorPrefix}es and {@code AspFactorOpr}s
      */
    public static AspFactor parse(Scanner s) {
        enterParser("factor");
        AspFactor factor = new AspFactor(s.curLineNum());

        while (true) {
            if (AspFactorPrefix.isFactorPrefix(s.curToken().kind)) {
                factor.prefixes.add(AspFactorPrefix.parse(s));
            } else if (AspFactorOpr.isFactorOpr(s.curToken().kind)) {
                factor.factorOprs.add(AspFactorOpr.parse(s));
            } else {
                factor.primaries.add(AspPrimary.parse(s));
                if (!AspFactorOpr.isFactorOpr(s.curToken().kind))
                    break;
            }
        }
        
        leaveParser("factor");
        return factor;
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < primaries.size()) {
            if (n < prefixes.size())
                prefixes.get(n).prettyPrint();

            primaries.get(n).prettyPrint();

            if (n < factorOprs.size())
                factorOprs.get(n).prettyPrint();

            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
