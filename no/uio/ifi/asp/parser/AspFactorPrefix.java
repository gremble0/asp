package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactorPrefix extends AspSyntax {
    public static TokenKind[] factorPrefixes = { plusToken, minusToken };
    public TokenKind factorPrefixKind;
    
    public AspFactorPrefix(int n) {
        super(n);
    }

    public static boolean isFactorPrefix(TokenKind tokKind) {
        for (TokenKind factorPrefix : AspFactorPrefix.factorPrefixes) {
            if (tokKind == factorPrefix)
                return true;
        }

        return false;
    }

    public static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix factorPrefix = new AspFactorPrefix(s.curLineNum());
        factorPrefix.factorPrefixKind = s.curToken().kind;
        
        leaveParser("factor prefix");
        return factorPrefix;
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
