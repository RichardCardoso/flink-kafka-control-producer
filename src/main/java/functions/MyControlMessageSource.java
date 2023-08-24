package functions;

import misc.Constants;
import misc.Operator;
import models.ControlMessage;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyNumberSequenceSource extends RichParallelSourceFunction<ControlMessage> {

    private final long interval;
    private int count = 0;

    private AtomicBoolean running = new AtomicBoolean(true);

    public MyNumberSequenceSource(long interval) {

        this.interval = interval;
    }

    @Override
    public void run(SourceContext<ControlMessage> ctx) throws Exception {

        ControlMessage controlMessage = null;
        while(running.get()) {
            if (count == 0) {
                controlMessage = new ControlMessage("$.value", Operator.GREATER_OR_EQUAL_TO, 3000L, 1.0, 5L, 1L, Constants.GLOBAL_CUSTOMER_ID, 1L);
            } else if (count == 1) {
                controlMessage = new ControlMessage("$.value", Operator.GREATER_OR_EQUAL_TO, 3000L, 1.0, 5L, 1L, Constants.GLOBAL_CUSTOMER_ID, 1L);
            }
            if (Objects.nonNull(controlMessage)) {
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
