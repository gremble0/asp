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

    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = primaries.get(0).eval(curScope);
        AspFactorPrefix factorPrefix = prefixes.get(0);
        if (factorPrefix != null)
            v = evalFactorPrefixKind(v, factorPrefix);

        for (int i = 1; i < primaries.size(); ++i) {
            factorPrefix = prefixes.get(i);
            if (factorPrefix != null)
                v = evalFactorPrefixKind(v, factorPrefix);
 
            TokenKind factorOprKind = factorOprs.get(i - 1).kind;
            v = evalFactorOprKind(v, primaries.get(i).eval(curScope), factorOprKind);
        }

        return v;
    }

    private RuntimeValue evalFactorPrefixKind(RuntimeValue v, AspFactorPrefix factorPrefix) {
        switch (factorPrefix.kind) {
            case plusToken:
                return v.evalPositive(this);
            case minusToken:
                return v.evalNegate(this);
            default:
                Main.panic("Illegal factor prefix: " + factorPrefix.kind + "!");
                return null; // unreachable, added to avoid compiler error
        }
    }

    private RuntimeValue evalFactorOprKind(RuntimeValue v1, RuntimeValue v2, TokenKind kind) {
        switch (kind) {
        case astToken:
            return v1.evalMultiply(v2, this);
        case slashToken:
            return v1.evalDivide(v2, this);
        case percentToken:
            return v1.evalModulo(v2, this);
        case doubleSlashToken:
            return v1.evalIntDivide(v2, this);
        default:
            Main.panic("Illegal factor operator: " + kind + "!");
            return null; // unreachable, added to avoid compiler error
        }
    }
}
