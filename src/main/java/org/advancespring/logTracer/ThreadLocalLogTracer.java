package org.advancespring.logTracer;

import org.advancespring.trace.TraceId;
import org.advancespring.trace.TraceStatus;

public class ThreadLocalLogTracer implements LogTracer{

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        TraceId traceId = getTraceId();
        return new TraceStatus(traceId,System.currentTimeMillis(),message);
    }

    private TraceId getTraceId() {
        TraceId savedTraceId = traceIdHolder.get();
        if(savedTraceId == null){
            TraceId traceId = new TraceId();
            traceIdHolder.set(traceId);
        }
        else{
            TraceId next = savedTraceId.createNext();
            traceIdHolder.set(next);
        }

        return traceIdHolder.get();
    }

    @Override
    public void end(TraceStatus status) {

    }

    @Override
    public void exception(TraceStatus status, Exception e) {

    }
}
