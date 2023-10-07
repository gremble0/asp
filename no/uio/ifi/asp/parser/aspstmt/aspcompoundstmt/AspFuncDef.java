package no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.aspatom.AspName;
import no.uio.ifi.asp.parser.aspsuite.AspSuite;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;

public class AspFuncDef extends AspCompoundStmt {
    public AspName funcName;
    public ArrayList<AspName> params =  new ArrayList<>();
    public AspSuite body;
    
    public AspFuncDef(int n) {
        super(n);
    }

    public static AspFuncDef parse(Scanner s) {
        enterParser("func def");
        AspFuncDef funcDef = new AspFuncDef(s.curLineNum());

        skip(s, defToken);
        funcDef.funcName = AspName.parse(s);
        skip(s, leftParToken);

        while (s.curToken().kind != rightParToken) {
            funcDef.params.add(AspName.parse(s));

            test(s, commaToken, rightParToken);
            if (s.curToken().kind == commaToken)
                s.readNextToken();
        }

        skip(s, rightParToken);
        skip(s, colonToken);
        funcDef.body = AspSuite.parse(s);
        
        leaveParser("func def");
        return funcDef;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("def ");
        funcName.prettyPrint();
        // In my opinion its "prettier" to not have the space before the "("
        // but i'll add it to match the reference interpreter
        prettyWrite(" (");

        int n = 0;
        while (n < params.size()) {
            params.get(n).prettyPrint();
            if (n < params.size() - 1)
                prettyWrite(", ");
            ++n;
        }

        prettyWrite("): ");
        body.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
