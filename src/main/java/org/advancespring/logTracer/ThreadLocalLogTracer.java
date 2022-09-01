package org.advancespring.logTracer;

import org.advancespring.trace.TraceId;
import org.advancespring.trace.TraceStatus;
import org.springframework.util.ObjectUtils;

public class ThreadLocalLogTracer implements LogTracer{

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        TraceId traceId = getTraceId();
        String indentedMessage = addIndent(traceId.getDepth(), message);
        return new TraceStatus(traceId,System.currentTimeMillis(),indentedMessage);
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

        if(traceId.isFirstDepth()){
            traceIdHolder.remove();
        }
        else{
            traceIdHolder.set(traceId.createPrevious());
        }
        return new TraceStatus(traceId,System.currentTimeMillis(),status.getMessage());
    }

    @Override
    public TraceStatus exception(TraceStatus status, Exception e) {

        return status;
    }

    private String addIndent(int depth,String message){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("----");
        }
        return String.format("%s%s",sb,message);
    }
}
