package net.cackerman.samples.ProducerConsumer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestProducer {

    private CircularBuffer<Double> buffer;
    private final long iterations = 1000;

    @Before
    public void before() {
        buffer = new CircularBuffer<>(Double.class);
    }

    @Test
    public void testWaitOnFullBuffer() throws InterruptedException {
        BufferTestUtil.fillBuffer(buffer);

        Thread producerThread = new Thread(new Producer(buffer, iterations));
        producerThread.start();
        Thread.sleep(250);

        assertEquals(Thread.State.WAITING, producerThread.getState());

        producerThread.interrupt();
    }
}
