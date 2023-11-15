package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;

public class RuntimeScope {
    private RuntimeScope outer;
    private HashMap<String, RuntimeValue> decls = new HashMap<>(); // TODO: <AspName, RuntimeValue>, change name to bindings
    private ArrayList<String> globalNames = new ArrayList<>();

    public RuntimeScope() {
        // Used by the library (and when testing expressions in part 3)
        outer = null;
    }

    public RuntimeScope(RuntimeScope outer) {
        // Used by all user scopes
        this.outer = outer;
    }

    public void assign(String id, RuntimeValue val) {
        if (globalNames.contains(id))
            Main.globalScope.decls.put(id, val);
        else
            decls.put(id, val);
    }

    public RuntimeValue find(String id, AspSyntax where) {
        if (globalNames.contains(id)) {
            RuntimeValue v = Main.globalScope.decls.get(id);
            if (v != null)
                return v;
        } else {
            RuntimeScope scope = this;
            while (scope != null) {
                RuntimeValue v = scope.decls.get(id);
                if (v != null)
                    return v;
                scope = scope.outer;
            }
        }
        RuntimeValue.runtimeError("Name " + id + " not defined!", where);
        return null; // Required by the compiler.
    }

    public boolean hasDefined(String id) {
        return decls.get(id) != null;
    }

    public boolean hasGlobalName(String id) {
        return globalNames.contains(id);
    }

    public void registerGlobalName(String id) {
        globalNames.add(id);
    }
}
