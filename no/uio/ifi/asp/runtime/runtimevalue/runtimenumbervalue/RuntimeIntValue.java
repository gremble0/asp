package no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeBoolValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeNoneValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeStringValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;

public class RuntimeIntValue extends RuntimeNumberValue {
    private long intValue;

    public RuntimeIntValue(long v) {
        intValue = v;
    }

    @Override
    public String typeName() {
        return "int";
    }

    @Override
    public String toString() {
        return String.valueOf(intValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return intValue != 0;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double)intValue;
    }

    @Override
    public RuntimeNumberValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(intValue + v.getIntValue("+ operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(intValue + v.getFloatValue("+ operand", where));

        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeFloatValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeFloatValue((double)intValue / (double)v.getIntValue("/ operand", where));
        } else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue((double)intValue / v.getFloatValue("/ operand", where));
        }

        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(intValue == v.getIntValue("== operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(intValue == v.getFloatValue("== operand", where));
        else if (v instanceof RuntimeNoneValue)
            return new RuntimeBoolValue(false);
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                intValue == 1 && v.getBoolValue("== operand", where) == true ||
                intValue == 0 && v.getBoolValue("== operand", where) == false
            );

        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(intValue > v.getIntValue("> operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(intValue > v.getFloatValue("> operand", where));
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                intValue > 1 && v.getBoolValue("< operand", where) == true ||
                intValue > 0 && v.getBoolValue("< operand", where) == false
            );

        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(intValue >= v.getIntValue(">= operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(intValue >= v.getFloatValue(">= operand", where));
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                intValue >= 1 && v.getBoolValue("< operand", where) == true ||
                intValue >= 0 && v.getBoolValue("< operand", where) == false
            );

        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeNumberValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(intValue / v.getIntValue("// operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(Math.floor(intValue / v.getFloatValue("// operand", where)));

        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeBoolValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(intValue < v.getIntValue("< operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(intValue < v.getFloatValue("< operand", where));
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                intValue < 1 && v.getBoolValue("< operand", where) == true ||
                intValue < 0 && v.getBoolValue("< operand", where) == false
            );

        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(intValue <= v.getIntValue("<= operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(intValue <= v.getFloatValue("<= operand", where));
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                intValue <= 1 && v.getBoolValue("< operand", where) == true ||
                intValue <= 0 && v.getBoolValue("< operand", where) == false
            );

        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeNumberValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(pythonModulo(intValue, v.getIntValue("% operand", where)));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(pythonModulo(intValue, v.getFloatValue("% operand", where)));

        runtimeError("Type error for %.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(intValue * v.getIntValue("* operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(intValue * v.getFloatValue("* operand", where));
        else if (v instanceof RuntimeStringValue)
            return new RuntimeStringValue(v.toString().repeat((int)intValue));

        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeIntValue evalNegate(AspSyntax where) {
        return new RuntimeIntValue(-intValue);
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        if (intValue == 0)
            return new RuntimeBoolValue(true);
        
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(intValue != v.getIntValue("!= operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(intValue != v.getFloatValue("!= operand", where));
        else if (v instanceof RuntimeNoneValue)
            return new RuntimeBoolValue(true);
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                intValue != 1 && v.getBoolValue("== operand", where) == true ||
                intValue != 0 && v.getBoolValue("== operand", where) == false
            );

        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeIntValue evalPositive(AspSyntax where) {
        return new RuntimeIntValue(+intValue);
    }

    @Override
    public RuntimeNumberValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(intValue - v.getIntValue("+ operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(intValue - v.getFloatValue("+ operand", where));

        runtimeError("Type error for -.", where);
        return null;
    }
}
