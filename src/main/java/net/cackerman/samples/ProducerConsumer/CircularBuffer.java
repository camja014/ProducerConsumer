package net.cackerman.samples.ProducerConsumer;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * Buffer class, implemented as a circular buffer.
 */
public class CircularBuffer<T> {

    final int bufferSize;

    T[] data;

    int start;
    int size;

    public CircularBuffer(Class<T> tClass) {
        bufferSize = 1000;

        this.start = 0;
        this.size = 0;

        this.data = (T[]) Array.newInstance(tClass, bufferSize);
    }

    public CircularBuffer(Class<T> tClass, int bufferSize) {
        this.bufferSize = bufferSize;

        this.start = 0;
        this.size = 0;

        this.data = (T[]) Array.newInstance(tClass, bufferSize);
    }

    public int size() {
        return size;
    }

    public boolean isFull() {
        return size == bufferSize;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T get() {
        if (isEmpty()) {
            throw new NoSuchElementException("Buffer is empty");
        }

        // retrieve element
        T d = data[start];
        start = (start + 1) % bufferSize;
        size--;

        // if size is zero, reset start and end indices
        if (isEmpty()) {
            start = 0;
        }

        return d;
    }

    public void put(T data) {
        if (isFull()) {
            throw new IndexOutOfBoundsException("Buffer is full");
        }

        size++;
        this.data[getEnd()] = data;
    }

    // Gets the next available space in the buffer.
    int getEnd() {
        return (start + (size - 1)) % bufferSize;
    }
}
