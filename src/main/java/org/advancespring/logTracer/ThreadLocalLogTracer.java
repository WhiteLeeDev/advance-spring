package org.advancespring.logTracer;

import org.advancespring.trace.TraceId;
import org.advancespring.trace.TraceStatus;
import org.springframework.util.ObjectUtils;

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
    public TraceStatus end(TraceStatus status) {
        TraceId traceId = traceIdHolder.get();
        if(ObjectUtils.isEmpty(traceId))
            return new TraceStatus(null,0L,"");

        releaseTraceId(traceId);
        TraceStatus traceStatus = new TraceStatus(traceId,
                System.currentTimeMillis(),
                status.getMessage());

        return traceStatus;
    }

    private void releaseTraceId(TraceId traceId) {
        if(traceId.isFirstDepth()){
            traceIdHolder.remove();
        }
        else{
            traceIdHolder.set(traceId.createPrevious());
        }
    }

    @Override
    public TraceStatus exception(TraceStatus status, Exception e) {
        TraceId traceId = traceIdHolder.get();
        if(ObjectUtils.isEmpty(traceId))
            return new TraceStatus(null,0L,"");

        releaseTraceId(traceId);
        TraceStatus traceStatus = new TraceStatus(traceId,
                System.currentTimeMillis(),
                status.getMessage(),e);

        return traceStatus;
    }


}
