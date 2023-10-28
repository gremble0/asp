package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeIntValue;

public class RuntimeListValue extends RuntimeValue {
    private ArrayList<RuntimeValue> rtValues = new ArrayList<>();
    // This is mostly unnecessary for this class but i'll add it for extensibility
    private Map<String, List<Class<? extends RuntimeValue>>> supportedTypes = new HashMap<>() {{
        // Math operations
        put("evalAdd", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));
        put("evalMultiply", new ArrayList<>(List.of(
            RuntimeIntValue.class
        )));

        // Comparisons
        put("evalEqual", new ArrayList<>(List.of(
            RuntimeListValue.class, RuntimeNoneValue.class
        )));
        put("evalGreater", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));
        put("evalLess", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));
        put("evalGreaterEqual", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));
        put("evalLessEqual", new ArrayList<>(List.of(
            RuntimeListValue.class
        )));
    }};

    public RuntimeListValue(ArrayList<RuntimeValue> vs) {
        rtValues = vs;
    }

    @Override
    public String typeName() {
        return "list";
    }

    @Override
    public String toString() {
        String res = "[";
        for (int i = 0; i < rtValues.size(); i++) {
            RuntimeValue v = rtValues.get(i);
            if (v instanceof RuntimeStringValue)
                res += v.showInfo();
            else
                res += v.toString();

            if (i != rtValues.size() - 1)
                res += ", ";
        }
        res += "]";
            
        return res;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return rtValues.size() != 0;
    }

    @Override
    public ArrayList<RuntimeValue> getListValue(String what, AspSyntax where) {
        return rtValues;
    }

    @Override
    public RuntimeIntValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(rtValues.size());
    }

    @Override
    public RuntimeListValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalAdd").contains(v.getClass()))
            runtimeError("+", typeName(), v.typeName(), where);

        RuntimeListValue newRtValues = new RuntimeListValue(rtValues);
        newRtValues.rtValues.addAll(v.getListValue("+ operand", where));

        return newRtValues;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (!supportedTypes.get("evalMultiply").contains(v.getClass()))
            runtimeError("*", typeName(), v.typeName(), where);

       ArrayList<RuntimeValue> newRtValues = new ArrayList<>();

       for (int i = 0; i < v.getIntValue("* operand", where); i++)
           newRtValues.addAll(rtValues);

       return new RuntimeListValue(newRtValues);
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(rtValues.size() == 0);
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        // We don't raise error if none of the supported types, just return false
        if (v instanceof RuntimeListValue)
            return new RuntimeBoolValue(rtValues.equals(v.getListValue("== operand", where)));

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

        ArrayList<RuntimeValue> vRtValues = v.getListValue("> operand", where);
            
        if (rtValues.size() != vRtValues.size())
            return new RuntimeBoolValue(rtValues.size() > vRtValues.size());
        
        for (int i = 0; i < rtValues.size(); i++) {
            if (rtValues.get(i) != rtValues.get(i)) {
                return rtValues.get(i).evalGreater(vRtValues.get(i), where);
            }
        }

        return new RuntimeBoolValue(false);
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
