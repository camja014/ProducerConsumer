package net.cackerman.samples.ProducerConsumer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestConsumer {

    private CircularBuffer<Double> buffer;
    private final long iterations = 1000;

    @Before
    public void before() {
        buffer = new CircularBuffer<>(Double.class);
    }

    @Test
    public void testWaitsOnEmptyBuffer() throws InterruptedException {
        Thread consumerThread = new Thread(new Consumer(buffer, iterations));
        consumerThread.start();
        Thread.sleep(250);

        assertEquals(Thread.State.WAITING, consumerThread.getState());

        consumerThread.interrupt();
    }
}
