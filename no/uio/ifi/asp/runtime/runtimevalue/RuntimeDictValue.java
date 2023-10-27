package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {
    private ArrayList<RuntimeValue> rtKeys = new ArrayList<>();
    private ArrayList<RuntimeValue> rtValues = new ArrayList<>();

    public RuntimeDictValue(ArrayList<RuntimeValue> ks, ArrayList<RuntimeValue> vs) {
        rtKeys = ks;
        rtValues = vs;
    }

    @Override
    public String typeName() {
        return "dict";
    }

    @Override
    public String toString() {
        String res = "{\n";
        for (int i = 0; i < rtKeys.size(); i++) {
            res += rtKeys.get(i);
            res += ":";
            res += rtValues.get(i);
            res += ",\n";
        }
        res += "}";

        return res;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return rtKeys.size() != 0;
    }

    @Override
    public ArrayList<RuntimeValue> getDictKeys(String what, AspSyntax where) {
        return rtValues;
    }

    @Override
    public ArrayList<RuntimeValue> getDictValues(String what, AspSyntax where) {
        return rtValues;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeDictValue))
            return new RuntimeBoolValue(false);

        return new RuntimeBoolValue(
            rtValues.equals(v.getListValue("== operand", where)) &&
            rtKeys.equals(v.getListValue("== operand", where))
        );
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(rtKeys.size() == 0);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeDictValue))
            return new RuntimeBoolValue(true);

        return new RuntimeBoolValue(!rtValues.equals(v.getListValue("!= operand", where)));
    }
}
