package net.cackerman.samples.ProducerConsumer;

import java.util.Random;

public class BufferTestUtil {

    /**
     * Fill buffer completely
     *
     * @param buffer The buffer to fill
     */
    static void fillBuffer(CircularDBuffer buffer) {
        Random random = new Random();

        for (int i = 0; i < buffer.bufferSize; i++) {
            buffer.data[i] = random.nextDouble();
        }

        buffer.size = buffer.bufferSize;
        buffer.start = 0;
    }

    /**
     * Empty the buffer, then fill it partially. It will never fill the buffer completely.
     *
     * @param buffer The buffer to fill
     * @param n      How many items to try adding to the buffer
     * @return number of items actually added to the buffer
     */
    static int fillPartial(CircularDBuffer buffer, int n) {
        emptyBuffer(buffer);

        Random random = new Random();

        int i = 0;
        while (i < n || i < buffer.bufferSize - 1) {
            buffer.data[i] = random.nextDouble();
            i++;
        }

        buffer.size = n;
        buffer.start = 0;

        return i;
    }

    /**
     * empty the given buffer completely
     *
     * @param buffer the buffer to empty
     */
    static void emptyBuffer(CircularDBuffer buffer) {
        buffer.data = new double[buffer.bufferSize];
        buffer.size = 0;
        buffer.start = 0;
    }

    /**
     * Partially empty the given buffer. The buffer will never be fully emptied.
     *
     * @param buffer the buffer to remove elements from the back of
     * @param n      the number of elements to empty
     */
    static void emptyPartial(CircularDBuffer buffer, int n) {
        if (n >= buffer.size) buffer.size = 1;
        else buffer.size -= n;
    }
}
