package org.advancespring.trace;

public class TraceStatus {
    private final TraceId traceId;
    private final Long startMs;
    private final String message;

    public TraceStatus(TraceId traceId, Long startMs, String message) {
        this.traceId = traceId;
        this.startMs = startMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartMs() {
        return startMs;
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.getTraceId().getDepth(); i++) {
            sb.append("----");
        }
        return String.format("%s%s",sb,message);
    }
}
