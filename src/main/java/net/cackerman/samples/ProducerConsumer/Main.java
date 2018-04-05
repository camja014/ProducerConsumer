package net.cackerman.samples.ProducerConsumer;

/**
 * This was taken from a homework assignment where tasked with solving the producer/consumer problem.
 */
public class Main {

    private static final long NUM_ITERATIONS = 10000000; // 10 million iterations

    public static void main(String[] argv) {
        CircularBuffer<Double> circularBuffer = new CircularBuffer<>(Double.class);

        System.out.println("MAIN: Starting Producer...");
        Thread producerThread = new Thread(new Producer(circularBuffer, NUM_ITERATIONS));

        System.out.println("MAIN: Starting Consumer...");
        Thread consumerThread = new Thread(new Consumer(circularBuffer, NUM_ITERATIONS));

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
}