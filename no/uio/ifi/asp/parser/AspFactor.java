package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspFactor extends AspSyntax {
    public ArrayList<AspFactorPrefix> prefixes = new ArrayList<>();
    public ArrayList<AspPrimary> primaries = new ArrayList<>();
    public ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();
    
    public AspFactor(int n) {
        super(n);
    }

    static AspFactor parse(Scanner s) {
        enterParser("factor");

        AspFactor factor = new AspFactor(s.curLineNum());

        // TODO fix while true loop
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

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
