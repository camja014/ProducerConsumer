package net.cackerman.samples.ProducerConsumer;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

public class TestBuffer {

    private CircularBuffer<Double> buffer;
    private Random random = new Random();

    @Before
    public void before() {
        buffer = new CircularBuffer<>(Double.class);
    }

    @Test
    public void testGet() {
        double testItem = random.nextDouble();

        buffer.data[0] = testItem;
        buffer.size = 1;
        buffer.start = 0;

        assertEquals(testItem, buffer.get(), 0.0);
    }

    @Test
    public void testPut() {
        double testItem = random.nextDouble();

        buffer.put(testItem);

        assertEquals(1, buffer.size);
        assertEquals(0, buffer.getEnd());
        assertEquals(0, buffer.start);
        assertEquals(testItem, buffer.data[0], 0.0);
    }

    @Test
    public void testGetEndEmpty() {
        assertEquals(0, buffer.start);
    }

    @Test
    public void testGetEndFull() {
        BufferTestUtil.fillBuffer(buffer);
        assertEquals(buffer.bufferSize - 1, buffer.getEnd());
    }

    @Test
    public void testGetEndPartial() {
        int numItems = 443;
        BufferTestUtil.fillPartial(buffer, numItems);
        // end will be one less than size
        assertEquals(numItems - 1, buffer.getEnd());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowsOnOverFill() {
        BufferTestUtil.fillBuffer(buffer);
        buffer.put(random.nextDouble());
    }

    @Test(expected = NoSuchElementException.class)
    public void testThrowsOnGetEmpty() {
        buffer.get();
    }

    @Test
    public void testIsEmptyOnFull() {
        BufferTestUtil.fillBuffer(buffer);
        assertFalse(buffer.isEmpty());
    }

    @Test
    public void testIsEmptyOnEmpty() {
        BufferTestUtil.fillBuffer(buffer);
        BufferTestUtil.emptyBuffer(buffer);
        assertTrue(buffer.isEmpty());
    }

    @Test
    public void testIsFullOnFull() {
        BufferTestUtil.fillBuffer(buffer);
        assertTrue(buffer.isFull());
    }

    @Test
    public void testIsFullOnEmpty() {
        BufferTestUtil.fillBuffer(buffer);
        BufferTestUtil.emptyBuffer(buffer);
        assertFalse(buffer.isFull());
    }
}
