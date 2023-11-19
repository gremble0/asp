package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.aspstmt.aspcompoundstmt.AspFuncDef;
import no.uio.ifi.asp.runtime.RuntimeScope;

public class RuntimeFunc extends RuntimeValue {
    public AspFuncDef def;
    public String name;
    public RuntimeScope defScope;
    
    public RuntimeFunc(AspFuncDef def, String name, RuntimeScope defScope) {
        this.def = def;
        this.name = name;
        this.defScope = defScope;
    }

    @Override
    public String typeName() {
        return "function";
    }

    // Maybe write this? Python writes mem address
    // @Override
    // public String toString() {}

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        // Python behavior
        return true;
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(this == v);
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(!evalEqual(v, where).getBoolValue("!= operand", where));
    }
    
    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
        // TODO:
        return null;
    }
}
