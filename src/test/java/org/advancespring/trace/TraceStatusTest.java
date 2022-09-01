package org.advancespring.trace;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TraceStatusTest {

    @Test
    @DisplayName("[positive] traceId, 시작시간, message 로 초기화 한다")
    void test1(){
        TraceId id = new TraceId();
        Long startMs = System.currentTimeMillis();
        TraceStatus traceStatus = new TraceStatus(id,startMs,"객체생성테스트");

        assertThat(traceStatus.getTraceId().getId()).hasSize(8);
        assertThat(traceStatus.getStartMs()).isPositive();
        assertThat(traceStatus.getFormattedMessage()).isEqualTo("객체생성테스트");
    }

    @Test
    @DisplayName("[positive] trace의 depth가 0일 때, message가 원본과 동일함")
    void test2() {
        TraceId id = new TraceId();
        TraceStatus status = new TraceStatus(id, System.currentTimeMillis(), "원본");

        assertThat(status.getFormattedMessage()).isEqualTo("원본");
    }
}