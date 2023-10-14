package no.uio.ifi.asp.parser.aspprimarysuffix;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.scanner.Scanner;

public abstract class AspPrimarySuffix extends AspSyntax {
    public AspPrimarySuffix(int n) {
        super(n);
    }
    
    /**
      * @param s {@code Scanner} used and mutated to parse the {@code AspPrimarySuffix}
      * @return  Instance of a subclass of {@code AspPrimarySuffix} depending on the
      *          scanners current token
      */
    public static AspPrimarySuffix parse(Scanner s) {
        enterParser("primary suffix");
        AspPrimarySuffix primarySuffix = null;

        test(s, leftParToken, leftBracketToken);

        if (s.curToken().kind == leftBracketToken)
            primarySuffix = AspSubscription.parse(s);
        else
            primarySuffix = AspArguments.parse(s);

        leaveParser("primary suffix");
        return primarySuffix;
    }
}
