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
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == v.getBoolValue("== operand", where));
        else if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(
                boolValue == true && v.getIntValue("== operand", where) == 1 ||
                boolValue == false && v.getIntValue("== operand", where) == 0
            );

        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == true && v.getBoolValue("> operand", where) == false);
        else if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(
                (boolValue == true && v.getIntValue("> operand", where) < 1) ||
                (boolValue == false && v.getIntValue("> operand", where) < 0)
            );

        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == true && v.getBoolValue(">= operand", where) == false);
        else if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(
                (boolValue == true && v.getIntValue(">= operand", where) <= 1) ||
                (boolValue == false && v.getIntValue(">= operand", where) <= 0)
            );

        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == false && v.getBoolValue("< operand", where) == true);
        else if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(
                (boolValue == true && v.getIntValue("< operand", where) > 1) ||
                (boolValue == false && v.getIntValue("< operand", where) > 0)
            );

        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == false && v.getBoolValue("< operand", where) == true);
        else if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(
                (boolValue == true && v.getIntValue("< operand", where) >= 1) ||
                (boolValue == false && v.getIntValue("< operand", where) >= 0)
            );

        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!boolValue);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue != v.getBoolValue("!= operand", where));
        else if (v instanceof RuntimeIntValue || v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(
                boolValue == true && v.getIntValue("!= operand", where) != 1 ||
                boolValue == false && v.getIntValue("!= operand", where) != 0
            );

        return new RuntimeBoolValue(true);
    }
}
