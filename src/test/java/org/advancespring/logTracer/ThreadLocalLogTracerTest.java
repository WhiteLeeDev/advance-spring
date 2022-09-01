package org.advancespring.logTracer;

import org.advancespring.trace.TraceId;
import org.advancespring.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;

class ThreadLocalLogTracerTest {

    private LogTracer logTracer = new ThreadLocalLogTracer();

    @Test
    @DisplayName("[positive] begin을 하면 traceStatus가 생성되고 TraceId가 ThreadLocal에 저장됨")
    void test1(){
        TraceStatus log_begin = logTracer.begin("log begin");

        ThreadLocal<TraceId> traceIdHolder = (ThreadLocal<TraceId>) getField(logTracer, "traceIdHolder");

        assertThat(log_begin.getTraceId()).isEqualTo(traceIdHolder.get());
    }

    @Test
    @DisplayName("[positive] begin을 할 때 마다 TraceId가 증가되어 ThreadLocal에 저장됨")
    void test2(){
        TraceStatus beginStatus = logTracer.begin("parent method");
        ThreadLocal<TraceId> traceIdHolder = (ThreadLocal<TraceId>) getField(logTracer, "traceIdHolder");
        assertThat(beginStatus.getTraceId()).isEqualTo(traceIdHolder.get());
        assertThat(beginStatus.getTraceId().getDepth()).isEqualTo(0);

        TraceStatus nextStatus= logTracer.begin("child method");
        traceIdHolder = (ThreadLocal<TraceId>) getField(logTracer, "traceIdHolder");
        assertThat(nextStatus.getTraceId()).isEqualTo(traceIdHolder.get());
        assertThat(nextStatus.getTraceId().getId()).isEqualTo(beginStatus.getTraceId().getId());
        assertThat(nextStatus.getTraceId().getDepth()).isEqualTo(1);
    }

    @Test
    @DisplayName("[positive] 출력될 로그는 depth * 4칸씩 들여쓰기 됨")
    void test3(){
        logTracer.begin("parent method");
        TraceStatus child_method = logTracer.begin("child method");

        assertThat(child_method.getTraceId().getDepth()).isEqualTo(1);
        assertThat(child_method.getFormattedMessage()).hasSize("child method".length()+4);
    }

    @Test
    @DisplayName("[positive] begin 후 end를 하면 수행 시간이 출력됨")
    void test4(){
        TraceStatus parent_method = logTracer.begin("parent method");
        logTracer.end(parent_method);
        //로그 검증은 어떻게 하지?
    }

    @Test
    @DisplayName("[positive] top level까지 end가 끝나면 TraceIdHolder가 초기화 됨")
    void test6(){
        TraceStatus parent_method = logTracer.begin("parent method");
        logTracer.end(parent_method);

        ThreadLocal<TraceId> traceIdHolder = (ThreadLocal<TraceId>)getField(logTracer, "traceIdHolder");

        assertThat(traceIdHolder.get()).isNull();
    }

    @Test
    @DisplayName("[positive] end를 할 때도 TraceStatus를 반환함")
    void test7(){
        TraceStatus parent_method = logTracer.begin("parent method");
        TraceStatus end_log = logTracer.end(parent_method);

        assertThat(end_log).isNotNull();
    }

    @Test
    @DisplayName("[positive] 두단계로 log 출력이 되어야 함")
    void test8(){
        TraceStatus function1 = logTracer.begin("function1");
        TraceStatus function2 = logTracer.begin("function2");
        TraceStatus end2 = logTracer.end(function2);
        TraceStatus end1 = logTracer.end(function1);

        assertThat(function1.getFormattedMessage()).isEqualTo("function1");
        assertThat(function2.getFormattedMessage()).isEqualTo("----function2");
        assertThat(end2.getFormattedMessage()).isEqualTo("----function2");
        assertThat(end1.getFormattedMessage()).isEqualTo("function1");
    }

    @Test
    @DisplayName("[positive] exception발생 시 message는 exception 포맷으로 변경되어야 함")
    void test9(){
        TraceStatus exception_test = logTracer.begin("exception test");
        TraceStatus exception_log = logTracer.exception(exception_test,new Exception()); //발생한 실제 exception을 어떻게 보여줄지는 고민되는 사항

        assertThat(exception_log.getFormattedMessage()).isEqualTo("exception test");
    }

    @Test
    @DisplayName("[positive] depth가 1이상일 때 exception발생 시 message는 exception 포맷으로 변경되어야 함")
    void test10(){

        TraceStatus exception_test = logTracer.begin("exception test");
        TraceStatus exception_test2 = logTracer.begin("exception test");
        TraceStatus exception_log2 = logTracer.exception(exception_test2,new Exception());
        TraceStatus exception_log1 = logTracer.exception(exception_test,new Exception());

        assertThat(exception_log2.getFormattedMessage()).isEqualTo("■■■■exception test");
        assertThat(exception_log1.getFormattedMessage()).isEqualTo("exception test");
    }
}