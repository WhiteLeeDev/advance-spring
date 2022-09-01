package org.advancespring.logTracer;

import org.advancespring.trace.TraceStatus;

public interface LogTracer {
    TraceStatus begin(String message);

    TraceStatus end(TraceStatus status);

    TraceStatus exception(TraceStatus status, Exception e);
}
