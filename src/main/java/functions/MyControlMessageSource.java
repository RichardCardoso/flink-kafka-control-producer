package functions;

import misc.Constants;
import models.*;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyControlMessageSource extends RichParallelSourceFunction<ControlMessage> {

    private final long interval;
    private int count = 0;

    private AtomicBoolean running = new AtomicBoolean(true);

    public MyControlMessageSource(long interval) {

        this.interval = interval;
    }

    @Override
    public void run(SourceContext<ControlMessage> ctx) throws Exception {

        ControlMessage controlMessage = null;
        while(running.get()) {
            if (count == 0) {
                controlMessage = new ControlMessage(
                        "$.value", Comparison.of(ComparisonRange.of(RangeAggregationType.AVERAGE, 10L, 1L, TimeUnit.SECONDS)), 3L, 1L
                );
            } else if (count == 1) {
                controlMessage = new ControlMessage(
                        "$.value", Comparison.of(ComparisonRange.of(RangeAggregationType.AVERAGE, 10L, 1L, TimeUnit.SECONDS)), Constants.GLOBAL_CUSTOMER_ID, 2L
                );
            } else {
                controlMessage = null;
            }
            if (Objects.nonNull(controlMessage)) {
                controlMessage.getComparison().addRule(ComparisonRule.of(Comparator.BETWEEN, 2500, 3500));
                ctx.collect(controlMessage);
                count++;
            }
            Thread.sleep(interval);
        }
    }

    @Override
    public void cancel() {

        running.set(false);
    }
}
