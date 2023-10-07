package no.uio.ifi.asp.parser.aspprimarysuffix;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.scanner.*;

public abstract class AspPrimarySuffix extends AspSyntax {
    public AspPrimarySuffix(int n) {
        super(n);
    }
    
    public static AspPrimarySuffix parse(Scanner s) {
        enterParser("primary suffix");
        AspPrimarySuffix primarySuffix = null;

        leaveParser("primary suffix");
        return primarySuffix;
    }
}
