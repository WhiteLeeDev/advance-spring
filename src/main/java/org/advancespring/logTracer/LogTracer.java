package org.advancespring.logTracer;

import org.advancespring.trace.TraceStatus;

public interface LogTracer {
    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status,Exception e);
}
