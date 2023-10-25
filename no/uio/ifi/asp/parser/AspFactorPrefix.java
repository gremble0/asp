package no.uio.ifi.asp.parser;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspFactorPrefix extends AspSyntax {
    private static TokenKind[] factorPrefixes = {
        plusToken,
        minusToken,
    };
    public TokenKind factorPrefixKind;
    
    public AspFactorPrefix(int n) {
        super(n);
    }

    /**
     * @param tokKind {@code TokenKind} to check if is a factor prefix
     * @return        {@code true} if parameter is a factor prefix, {@code false} if not
     */
    public static boolean isFactorPrefix(TokenKind tokKind) {
        for (TokenKind factorPrefix : AspFactorPrefix.factorPrefixes) {
            if (tokKind == factorPrefix)
                return true;
        }

        return false;
    }

    /**
      * @param s {@code Scanner} used for parsing the {@code AspFactorPrefix}
      * @return  {@code AspFactorPrefix} with information about what type of
      *          factor prefix it is
      */
    public static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix factorPrefix = new AspFactorPrefix(s.curLineNum());

        factorPrefix.factorPrefixKind = s.curToken().kind;
        s.readNextToken();
        
        leaveParser("factor prefix");
        return factorPrefix;
    }

    @Override
    public void prettyPrint() {
        // " " to match reference interpreter
        prettyWrite(factorPrefixKind.toString() + " ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
