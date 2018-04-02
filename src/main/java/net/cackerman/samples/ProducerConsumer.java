package net.cackerman.samples;

import java.util.Random;

/**
 * This was taken from a homework assignment where tasked with solving the producer/consumer problem.
 */
public class ProducerConsumer {

    public static void main(String[] argv) {

        CircularDBuffer circularDBuffer = new CircularDBuffer();

        long numIterations = 10000000; // 10 million iterations

        System.out.println("MAIN: Starting Producer...");
        Thread producerThread = new Thread(new Producer(circularDBuffer, numIterations));

        System.out.println("MAIN: Starting Consumer...");
        Thread consumerThread = new Thread(new Consumer(circularDBuffer, numIterations));

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            System.out.println("MAIN: Producer joined.");

            consumerThread.join();
            System.out.println("MAIN: Consumer joined.");
        } catch (InterruptedException e) {
            System.err.println("ERROR: One or more threads were interrupted.");
            e.printStackTrace();
        }

        System.out.println("MAIN: Exiting.");
    }

    /**
     * Consumes elements from the buffer and sums them
     */
    public static class Consumer implements Runnable {

        private CircularDBuffer circularDBuffer;
        private long iterations;

        public Consumer(CircularDBuffer circularDBuffer, long iterations) {
            this.circularDBuffer = circularDBuffer;
            this.iterations = iterations;
        }

        @Override
        public void run() {

            // retrieve and sum data from the buffer.
            // print the sums so we can compare with Producer.
            double sum = 0;
            for (int i = 0; i < iterations; i++) {
                try {

                    sum += circularDBuffer.get();
                    if (i % 100000 == 0 && i != 0)
                        System.out.printf("CONSUMER  [iteration: %d]: Sum of consumed items = %f.\n", i, sum);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("CONSUMER [iteration: 1000000]: DONE - Sum of consumed items = %f\n", sum);
        }
    }

    /**
     * Generates data to be placed into the buffer.
     */
    public static class Producer implements Runnable {

        private CircularDBuffer circularDBuffer;
        private long iterations;

        public Producer(CircularDBuffer circularDBuffer, long iterations) {
            this.circularDBuffer = circularDBuffer;
            this.iterations = iterations;
        }

        @Override
        public void run() {

            double sum = 0;
            Random random = new Random();

            // Fill the buffer with sample data
            // Sum and print the data so we can compare with consumer.
            for (int i = 0; i < iterations; i++) {
                Double bufferElement = random.nextDouble() * 100.0;

                try {
                    this.circularDBuffer.put(bufferElement);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sum += bufferElement;
                    if (i % 100000 == 0 && i != 0)
                        System.out.printf("PRODUCER  [iteration: %d]: Sum of produced items = %f.\n", i, sum);
            }
            System.out.printf("PRODUCER [iteration: 1000000]: DONE - Sum of produced items = %f\n", sum);
        }
    }

    /**
     * Buffer class, implemented as a circular buffer.
     */
    public static class CircularDBuffer {

        private static final int BUFFER_SIZE = 1000;

        private double[] data;

        private int start;
        private int size;

        public CircularDBuffer() {
            this.data = new double[BUFFER_SIZE];
            this.start = 0;
            this.size = 0;
        }

        public synchronized double get() throws InterruptedException {

            // if the buffer is empty, wait for elements to be produced.
            while (isEmpty()) {
                wait();
            }

            // retrieve element
            double d = data[start];
            start = (start + 1) % BUFFER_SIZE;
            size--;

            // if size is zero, reset start and end indices
            if (isEmpty()) {
                start = 0;
            }

            notify();

            return d;
        }

        public synchronized void put(double data) throws InterruptedException {

            // if the buffer is full, wait for elements to be consumed.
            while (isFull()) {
                wait();
            }

            // insert element
            size++;
            this.data[getEnd()] = data;

            notify();
        }

        // Gets the next available space in the buffer.
        private int getEnd() {
            return (start + (size - 1)) % BUFFER_SIZE;
        }

        public int size() {
            return size;
        }

        public boolean isFull() {
            return size() == BUFFER_SIZE;
        }

        public boolean isEmpty() {
            return size() == 0;
        }
    }
}