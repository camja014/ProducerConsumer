package net.cackerman.samples.ProducerConsumer;

import java.util.NoSuchElementException;

/**
 * Buffer class, implemented as a circular buffer.
 */
public class CircularDBuffer {

    public final int bufferSize;

    double[] data;

    int start;
    int size;

    public CircularDBuffer() {
        bufferSize = 1000;

        this.start = 0;
        this.size = 0;

        this.data = new double[bufferSize];
    }

    public CircularDBuffer(int bufferSize) {
        this.bufferSize = bufferSize;

        this.start = 0;
        this.size = 0;

        this.data = new double[this.bufferSize];
    }

    public double get() {
        if (isEmpty())
            throw new NoSuchElementException("Buffer is empty");

        // retrieve element
        double d = data[start];
        start = (start + 1) % bufferSize;
        size--;

        // if size is zero, reset start and end indices
        if (isEmpty())
            start = 0;

        return d;
    }

    public void put(double data) {
        if (isFull())
            throw new IndexOutOfBoundsException("Buffer is full");

        size++;
        this.data[getEnd()] = data;
    }

    // Gets the next available space in the buffer.
    int getEnd() {
        return (start + (size - 1)) % bufferSize;
    }

    public int size() {
        return size;
    }

    public boolean isFull() {
        return size() == bufferSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
