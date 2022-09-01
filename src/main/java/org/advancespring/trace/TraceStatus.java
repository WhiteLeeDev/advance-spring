package org.advancespring.trace;

import org.springframework.util.ObjectUtils;

public class TraceStatus {

    private static final String NORMAL_PADDING = "----";
    private static final String EXCEPTION_PADDING = "■■■■";

    private final TraceId traceId;
    private final Long startMs;
    private final String message;
    private final Exception exception;

    public TraceStatus(TraceId traceId, Long startMs, String message) {
        this.traceId = traceId;
        this.startMs = startMs;
        this.message = message;
        this.exception = null;
    }

    public TraceStatus(TraceId traceId, Long startMs, String message,Exception e) {
        this.traceId = traceId;
        this.startMs = startMs;
        this.message = message;
        this.exception = e;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartMs() {
        return startMs;
    }

    public String getFormattedMessage() {
        return String.format("%s%s",getLeftPad(),this.message);
    }

    private String getLeftPad() {
        String leftPad = "";
        if(ObjectUtils.isEmpty(this.exception))
            leftPad = addLeftPad(this.traceId.getDepth(),NORMAL_PADDING);
        else
            leftPad = addLeftPad(this.traceId.getDepth(),EXCEPTION_PADDING);
        return leftPad;
    }

    public String getMessage(){
        return this.message;
    }

    private String addLeftPad(int depth,String padFormat){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(padFormat);
        }
        return sb.toString();
    }
}
