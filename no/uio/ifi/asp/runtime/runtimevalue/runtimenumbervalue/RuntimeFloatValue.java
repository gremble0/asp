package no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeBoolValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeNoneValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;

public class RuntimeFloatValue extends RuntimeNumberValue {
    private double floatValue;

    public RuntimeFloatValue(double v) {
        floatValue = v;
    }

    @Override
    public String typeName() {
        return "float";
    }

    @Override
    public String toString() {
        return String.valueOf(floatValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return floatValue != 0;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (long)floatValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }

    @Override
    public RuntimeNumberValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue + v.getIntValue("+ operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(floatValue + v.getFloatValue("+ operand", where));

        runtimeError("+", typeName(), v.typeName(), where);
        return null;
    }

    @Override
    public RuntimeNumberValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue / v.getIntValue("/ operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(floatValue / v.getFloatValue("/ operand", where));

        runtimeError("/", typeName(), v.typeName(), where);
        return null;
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue == v.getIntValue("== operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue == v.getFloatValue("== operand", where));
        else if (v instanceof RuntimeNoneValue)
            return new RuntimeBoolValue(false);
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                floatValue == 1 && v.getBoolValue("== operand", where) == true ||
                floatValue == 0 && v.getBoolValue("== operand", where) == false
            );

        runtimeError("==", typeName(), v.typeName(), where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue > v.getIntValue("> operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue > v.getFloatValue("> operand", where));
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                floatValue > 1 && v.getBoolValue("< operand", where) == true ||
                floatValue > 0 && v.getBoolValue("< operand", where) == false
            );

        runtimeError(">", typeName(), v.typeName(), where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue >= v.getIntValue(">= operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue >= v.getFloatValue(">= operand", where));
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                floatValue >= 1 && v.getBoolValue("< operand", where) == true ||
                floatValue >= 0 && v.getBoolValue("< operand", where) == false
            );

        runtimeError(">=", typeName(), v.typeName(), where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeFloatValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(Math.floor(floatValue / v.getIntValue("// operand", where)));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(Math.floor(floatValue / v.getFloatValue("// operand", where)));

        runtimeError("//", typeName(), v.typeName(), where);
        return null;
    }

    @Override
    public RuntimeBoolValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue < v.getIntValue("< operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue < v.getFloatValue("< operand", where));
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                floatValue < 1 && v.getBoolValue("< operand", where) == true ||
                floatValue < 0 && v.getBoolValue("< operand", where) == false
            );

        runtimeError("<", typeName(), v.typeName(), where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue <= v.getIntValue("<= operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue <= v.getFloatValue("<= operand", where));
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                floatValue <= 1 && v.getBoolValue("< operand", where) == true ||
                floatValue <= 0 && v.getBoolValue("< operand", where) == false
            );

        runtimeError("<=", typeName(), v.typeName(), where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeNumberValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(pythonModulo(floatValue, v.getIntValue("% operand", where)));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(pythonModulo(floatValue, v.getFloatValue("% operand", where)));

        runtimeError("%", typeName(), v.typeName(), where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeNumberValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue * v.getIntValue("* operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(floatValue * v.getFloatValue("* operand", where));

        runtimeError("*", typeName(), v.typeName(), where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeFloatValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(-floatValue);
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        if (floatValue == 0.0)
            return new RuntimeBoolValue(true);
        
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeBoolValue(floatValue != v.getIntValue("!= operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeBoolValue(floatValue != v.getFloatValue("!= operand", where));
        else if (v instanceof RuntimeNoneValue)
            return new RuntimeBoolValue(true);
        else if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(
                floatValue != 1 && v.getBoolValue("== operand", where) == true ||
                floatValue != 0 && v.getBoolValue("== operand", where) == false
            );

        runtimeError("!=", typeName(), v.typeName(), where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeFloatValue evalPositive(AspSyntax where) {
        return new RuntimeFloatValue(+floatValue);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue)
            return new RuntimeFloatValue(floatValue - v.getIntValue("+ operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(floatValue - v.getFloatValue("+ operand", where));

        runtimeError("-", typeName(), v.typeName(), where);
        return null;
    }
}
