package org.advancespring.trace;

public class TraceStatus {
    private final TraceId traceId;
    private final Long startMs;
    private final String message;
    private String leftPad;

    public TraceStatus(TraceId traceId, Long startMs, String message) {
        this.traceId = traceId;
        this.startMs = startMs;
        this.message = message;
        this.leftPad = "";
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartMs() {
        return startMs;
    }

    public String getMessage() {
        return message;
    }

    public void setLeftPadding(String leftPad) {
        this.leftPad = leftPad;
    }
}
