package no.uio.ifi.asp.runtime.runtimevalue;

import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue.RuntimeIntValue;

public class RuntimeDictValue extends RuntimeValue {
    private HashMap<RuntimeValue, RuntimeValue> dict = new HashMap<>();

    public RuntimeDictValue(HashMap<RuntimeValue, RuntimeValue> dict) {
        this.dict = dict;
    }

    @Override
    public String typeName() {
        return "dict";
    }

    @Override
    public String toString() {
        String res = "{\n";

        int n = 0;
        for (RuntimeValue key : dict.keySet()) {
            res += dict.get(key);
            res += ":";
            res += key;
            if (n != dict.size() - 1)
                res += ",\n";
            ++n;
        }
        res += "}";

        return res;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return dict.size() != 0;
    }

    @Override
    public HashMap<RuntimeValue, RuntimeValue> getDictValue(String what, AspSyntax where) {
        return dict;
    }

    @Override
    public ArrayList<RuntimeValue> getDictKeys(String what, AspSyntax where) {
        ArrayList<RuntimeValue> keys = new ArrayList<>();
        keys.addAll(dict.keySet());
        return keys;
    }

    @Override
    public ArrayList<RuntimeValue> getDictValues(String what, AspSyntax where) {
        ArrayList<RuntimeValue> values = new ArrayList<>();
        values.addAll(dict.values());
        return values;
    }

    @Override
    public RuntimeIntValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(dict.size());
    }

    @Override
    public RuntimeBoolValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(dict.size() == 0);
    }

    @Override
    public RuntimeBoolValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeDictValue)
            return new RuntimeBoolValue(
                dict.equals(v.getDictValue("== operand", where))
            );

        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeBoolValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        return evalEqual(v, where).evalNot(where);
    }
}
