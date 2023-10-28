package no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeBoolValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeNoneValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeStringValue;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;

public class RuntimeIntValue extends RuntimeNumberValue {
    private long intValue;
    private Map<String, List<Class<? extends RuntimeValue>>> supportedTypes = new HashMap<>() {{
        // Math operations
        put("evalAdd", new ArrayList<>(List.of(RuntimeFloatValue.class, RuntimeIntValue.class)));
        put("evalSubtract", new ArrayList<>(List.of(RuntimeFloatValue.class, RuntimeIntValue.class)));
        put("evalMultiply", new ArrayList<>(List.of(
            RuntimeFloatValue.class, RuntimeIntValue.class, RuntimeStringValue.class
        )));
        put("evalDivide", new ArrayList<>(List.of(RuntimeFloatValue.class, RuntimeIntValue.class)));
        put("evalIntDivide", new ArrayList<>(List.of(RuntimeFloatValue.class, RuntimeIntValue.class)));
        put("evalIntDivide", new ArrayList<>(List.of(RuntimeFloatValue.class, RuntimeIntValue.class)));
        put("evalModulo", new ArrayList<>(List.of(RuntimeFloatValue.class, RuntimeIntValue.class)));

        // Comparisons
        put("evalEqual", new ArrayList<>(List.of(
            RuntimeFloatValue.class, RuntimeIntValue.class, RuntimeNoneValue.class, RuntimeBoolValue.class
        )));
        put("evalNotEqual", new ArrayList<>(List.of(
            RuntimeFloatValue.class, RuntimeIntValue.class, RuntimeBoolValue.class
        )));
        put("evalGreater", new ArrayList<>(List.of(
            RuntimeFloatValue.class, RuntimeIntValue.class, RuntimeBoolValue.class
        )));
        put("evalLess", new ArrayList<>(List.of(
            RuntimeFloatValue.class, RuntimeIntValue.class, RuntimeBoolValue.class
        )));
        put("evalGreaterEqual", new ArrayList<>(List.of(
            RuntimeFloatValue.class, RuntimeIntValue.class, RuntimeBoolValue.class
        )));
        put("evalLessEqual", new ArrayList<>(List.of(
            RuntimeFloatValue.class, RuntimeIntValue.class, RuntimeBoolValue.class
        )));
    }};

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
    public RuntimeIntValue evalPositive(AspSyntax where) {
        return new RuntimeIntValue(+intValue);
    }

    @Override
    public RuntimeIntValue evalNegate(AspSyntax where) {
        return new RuntimeIntValue(-intValue);
    }

    @Override
    public RuntimeNumberValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalAdd").contains(v.getClass()))
            runtimeError("+", typeName(), v.typeName(), where);
            
        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(intValue + v.getIntValue("+ operand", where));
        else // v is RuntimeFloatValue
            return new RuntimeFloatValue(intValue + v.getFloatValue("+ operand", where));
    }

    @Override
    public RuntimeNumberValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalSubtract").contains(v.getClass()))
            runtimeError("-", typeName(), v.typeName(), where);

        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(intValue - v.getIntValue("+ operand", where));
        else // v is RuntimeFloatValue
            return new RuntimeFloatValue(intValue - v.getFloatValue("+ operand", where));
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalMultiply").contains(v.getClass()))
            runtimeError("*", typeName(), v.typeName(), where);

        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(intValue * v.getIntValue("* operand", where));
        else if (v instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(intValue * v.getFloatValue("* operand", where));
        else // v is RuntimeStringValue
            return new RuntimeStringValue(v.toString().repeat((int)intValue));
    }

    @Override
    public RuntimeFloatValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalDivide").contains(v.getClass()))
            runtimeError("/", typeName(), v.typeName(), where);

        return new RuntimeFloatValue((double)intValue / v.getFloatValue("/ operand", where));
    }

    @Override
    public RuntimeNumberValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalIntDivide").contains(v.getClass()))
            runtimeError("//", typeName(), v.typeName(), where);

        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(intValue / v.getIntValue("// operand", where));
        else // v is RuntimeFloatValue
            return new RuntimeFloatValue(Math.floor(intValue / v.getFloatValue("// operand", where)));
    }

    @Override
    public RuntimeNumberValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalModulo").contains(v.getClass()))
            runtimeError("%", typeName(), v.typeName(), where);

        if (v instanceof RuntimeIntValue)
            return new RuntimeIntValue(pythonModulo(intValue, v.getIntValue("% operand", where)));
        else //v is RuntimeFloatValue
            return new RuntimeFloatValue(pythonModulo(intValue, v.getFloatValue("% operand", where)));
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(intValue == 0);
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalEqual").contains(v.getClass()))
            runtimeError("==", typeName(), v.typeName(), where);

        if (v instanceof RuntimeNumberValue)
            return new RuntimeBoolValue(intValue == v.getFloatValue("== operand", where));
        else if (v instanceof RuntimeNoneValue)
            return new RuntimeBoolValue(false);
        else // v is a RuntimeBoolValue
            return new RuntimeBoolValue(
                intValue == 1 && v.getBoolValue("== operand", where) == true ||
                intValue == 0 && v.getBoolValue("== operand", where) == false
            );
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        // Will be done in evalEqual but we do it here as well to log correct error message
        if (!supportedTypes.get("evalEqual").contains(v.getClass()))
            runtimeError("!=", typeName(), v.typeName(), where);

        return evalEqual(v, where).evalNot(where);
    }

    @Override
    public RuntimeBoolValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalGreater").contains(v.getClass()))
            runtimeError(">", typeName(), v.typeName(), where);

        if (v instanceof RuntimeNumberValue)
            return new RuntimeBoolValue(intValue > v.getFloatValue("> operand", where));
        else // v is RuntimeBoolValue
            return new RuntimeBoolValue(
                intValue > 1 && v.getBoolValue("< operand", where) == true ||
                intValue > 0 && v.getBoolValue("< operand", where) == false
            );
    }

    @Override
    public RuntimeBoolValue evalLess(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalLess").contains(v.getClass()))
            runtimeError("<", typeName(), v.typeName(), where);

        RuntimeBoolValue notGreater = evalGreater(v, where).evalNot(where);
        boolean notGreaterBool = notGreater.getBoolValue("< operand", where);
        
        RuntimeBoolValue notEqual = evalEqual(v, where).evalNot(where);
        boolean notEqualBool = notEqual.getBoolValue("< operand", where);

        return new RuntimeBoolValue(notGreaterBool && notEqualBool);
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

        runtimeError(">=", typeName(), v.typeName(), where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeBoolValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalLessEqual").contains(v.getClass()))
            runtimeError("<=", typeName(), v.typeName(), where);

        RuntimeBoolValue notGreater = v.evalGreater(v, where).evalNot(where);
        boolean notGreaterBool = notGreater.getBoolValue("> operand", where);

        return new RuntimeBoolValue(notGreaterBool);
    }
}
