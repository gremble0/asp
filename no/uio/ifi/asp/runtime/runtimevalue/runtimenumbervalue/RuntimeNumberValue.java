package no.uio.ifi.asp.runtime.runtimevalue.runtimenumbervalue;

import no.uio.ifi.asp.runtime.runtimevalue.RuntimeValue;

abstract public class RuntimeNumberValue extends RuntimeValue {
    protected long pythonModulo(long a, long n) {
        return ((a % n) + n) % n;

        // if (n < 0)
        //     return -(((a % n) + n) % n);
        // else
        //     return ((a % n) + n) % n;

        // if (n < 0)
        //     return -Math.abs(((a % n) + n) % n);
        // else
        //     return ((a % n) + n) % n;
    }

    protected double pythonModulo(double a, double n) {
        return ((a % n) + n) % n;

        // if (n < 0)
        //     return -(((a % n) + n) % n);
        // else
        //     return ((a % n) + n) % n;

        // if (n < 0)
        //     return -Math.abs(((a % n) + n) % n);
        // else
        //     return ((a % n) + n) % n;
    }
}
