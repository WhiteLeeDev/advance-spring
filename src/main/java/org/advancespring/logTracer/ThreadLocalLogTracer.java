package org.advancespring.logTracer;

import org.advancespring.trace.TraceId;
import org.advancespring.trace.TraceStatus;

public class ThreadLocalLogTracer implements LogTracer{

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        if(traceIdHolder.get() == null){
            TraceId traceId = new TraceId();
            traceIdHolder.set(traceId);
        }
        else{
            TraceId next = traceIdHolder.get().createNext();
            traceIdHolder.set(next);
        }

        return new TraceStatus(traceIdHolder.get(),System.currentTimeMillis(),message);
    }

    @Override
    public void end(TraceStatus status) {

    }

    @Override
    public void exception(TraceStatus status, Exception e) {

    }
}
