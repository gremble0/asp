package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.*;

public class RuntimeBoolValue extends RuntimeValue {
    private boolean boolValue;
    private Map<String, List<Class<? extends RuntimeValue>>> supportedTypes = new HashMap<>() {{
        // Math operations
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
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!boolValue);
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        // We don't raise error if none of the supported types, just return false
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
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        return evalEqual(v, where).evalNot(where);
    }

    @Override
    public RuntimeBoolValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalGreater").contains(v.getClass()))
            runtimeError(">", typeName(), v.typeName(), where);

        if (v instanceof RuntimeBoolValue)
            return new RuntimeBoolValue(boolValue == true && v.getBoolValue("> operand", where) == false);
        else // v is RuntimeNumberValue
            return new RuntimeBoolValue(
                (boolValue == true && v.getIntValue("> operand", where) < 1) ||
                (boolValue == false && v.getIntValue("> operand", where) < 0)
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
        if (!supportedTypes.get("evalGreaterEqual").contains(v.getClass()))
            runtimeError(">=", typeName(), v.typeName(), where);

        RuntimeBoolValue notGreater = evalLess(v, where).evalNot(where);
        boolean notGreaterBool = notGreater.getBoolValue("> operand", where);

        return new RuntimeBoolValue(notGreaterBool);
    }

    @Override
    public RuntimeBoolValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalLessEqual").contains(v.getClass()))
            runtimeError("<=", typeName(), v.typeName(), where);

        RuntimeBoolValue notGreater = evalGreater(v, where).evalNot(where);
        boolean notGreaterBool = notGreater.getBoolValue("> operand", where);

        return new RuntimeBoolValue(notGreaterBool);
    }
}
