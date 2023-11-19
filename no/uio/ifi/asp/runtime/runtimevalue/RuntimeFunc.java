package no.uio.ifi.asp.runtime.runtimevalue;

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
        return "None";
    }

    @Override
    public String toString() {
        return "None";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return false;
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(v instanceof RuntimeNoneValue);
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(!(v instanceof RuntimeNoneValue));
    }
}
