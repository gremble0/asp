package no.uio.ifi.asp.runtime.runtimevalue;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeBoolValue extends RuntimeValue {
    boolean boolValue;

    public RuntimeBoolValue(boolean v) {
        boolValue = v;
    }

    @Override
    String typeName() {
        return "boolean";
    }

    @Override
    public String toString() {
        return (boolValue ? "True" : "False");
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return boolValue;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeBoolValue))
            return new RuntimeBoolValue(false);

        return new RuntimeBoolValue(boolValue == v.getBoolValue("== operand", where));
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!boolValue);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeBoolValue))
            return new RuntimeBoolValue(false);

        return new RuntimeBoolValue(boolValue != v.getBoolValue("!= operand", where));
    }
}
