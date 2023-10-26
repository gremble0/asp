package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

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
            if (AspFactorPrefix.isFactorPrefix(s.curToken().kind))
                factor.prefixes.add(AspFactorPrefix.parse(s));
            else 
                factor.prefixes.add(null);

            factor.primaries.add(AspPrimary.parse(s));
            if (!AspFactorOpr.isFactorOpr(s.curToken().kind))
                break;

            if (AspFactorOpr.isFactorOpr(s.curToken().kind))
                factor.factorOprs.add(AspFactorOpr.parse(s));
        }
        
        leaveParser("factor");
        return factor;
    }

    @Override
    public void prettyPrint() {
        int n = 0;
        while (n < primaries.size()) {
            if (prefixes.get(n) != null)
                prefixes.get(n).prettyPrint();

            primaries.get(n).prettyPrint();

            if (n < factorOprs.size())
                factorOprs.get(n).prettyPrint();

            ++n;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO: refactor to foreach with v = null etc.
        RuntimeValue v = primaries.get(0).eval(curScope);

        for (int i = 1; i < primaries.size(); ++i) {
            AspFactorPrefix factorPrefix = prefixes.get(i - 1);
            if (factorPrefix != null) {
                switch (factorPrefix.factorPrefixKind) {
                case plusToken:
                    v = v.evalPositive(this);
                    break;
                case minusToken:
                    v = v.evalNegate(this);
                    break;
                default:
                    Main.panic("Illegal factor prefix: " + factorPrefix.factorPrefixKind + "!");
                }
            }

            TokenKind factorOprKind = factorOprs.get(i - 1).factorOprKind;
            switch (factorOprKind) {
            case astToken:
                v = v.evalMultiply(primaries.get(i).eval(curScope), this);
                break;
            case slashToken:
                v = v.evalDivide(primaries.get(i).eval(curScope), this);
                break;
            case percentToken:
                v = v.evalModulo(primaries.get(i).eval(curScope), this);
                break;
            case doubleSlashToken:
                v = v.evalIntDivide(primaries.get(i).eval(curScope), this);
                break;
            default:
                Main.panic("Illegal factor operator: " + factorOprKind + "!");
            }
        }

        return v;
    }
}
