package net.cackerman.samples.ProducerConsumer;

/**
 * This was taken from a homework assignment where tasked with solving the producer/consumer problem.
 */
public class Main {

    private static final long numIterations = 10000000; // 10 million iterations

    public static void main(String[] argv) {
        CircularDBuffer circularDBuffer = new CircularDBuffer();

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
}