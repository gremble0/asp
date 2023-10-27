package no.uio.ifi.asp.runtime.runtimevalue;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeNumberValue;

public class RuntimeBoolValue extends RuntimeValue {
    private boolean boolValue;

    public RuntimeBoolValue(boolean v) {
        boolValue = v;
    }

    @Override
    public String typeName() {
        return "bool";
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
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == v.getBoolValue("== operand", where));
        else if (v instanceof RuntimeNumberValue)
            return new RuntimeBoolValue(
                boolValue == true && v.getIntValue("== operand", where) == 1 ||
                boolValue == false && v.getIntValue("== operand", where) == 0
            );

        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeBoolValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == true && v.getBoolValue("> operand", where) == false);
        else if (v instanceof RuntimeNumberValue)
            return new RuntimeBoolValue(
                (boolValue == true && v.getIntValue("> operand", where) < 1) ||
                (boolValue == false && v.getIntValue("> operand", where) < 0)
            );

        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == true && v.getBoolValue(">= operand", where) == false);
        else if (v instanceof RuntimeNumberValue)
            return new RuntimeBoolValue(
                (boolValue == true && v.getIntValue(">= operand", where) <= 1) ||
                (boolValue == false && v.getIntValue(">= operand", where) <= 0)
            );

        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == false && v.getBoolValue("< operand", where) == true);
        else if (v instanceof RuntimeNumberValue)
            return new RuntimeBoolValue(
                (boolValue == true && v.getIntValue("< operand", where) > 1) ||
                (boolValue == false && v.getIntValue("< operand", where) > 0)
            );

        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == false && v.getBoolValue("< operand", where) == true);
        else if (v instanceof RuntimeNumberValue)
            return new RuntimeBoolValue(
                (boolValue == true && v.getIntValue("< operand", where) >= 1) ||
                (boolValue == false && v.getIntValue("< operand", where) >= 0)
            );

        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!boolValue);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue != v.getBoolValue("!= operand", where));
        else if (v instanceof RuntimeNumberValue)
            return new RuntimeBoolValue(
                boolValue == true && v.getIntValue("!= operand", where) != 1 ||
                boolValue == false && v.getIntValue("!= operand", where) != 0
            );

        return new RuntimeBoolValue(true);
    }
}
