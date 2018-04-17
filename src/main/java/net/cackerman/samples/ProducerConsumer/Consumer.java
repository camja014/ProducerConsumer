package net.cackerman.samples.ProducerConsumer;

/**
 * Consumes elements from the buffer and sums them
 */
public class Consumer implements Runnable {

    private final CircularDBuffer buffer;
    private final long iterations;

    public Consumer(CircularDBuffer circularDBuffer, long iterations) {
        this.buffer = circularDBuffer;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        // retrieve and sum data from the buffer.
        // print the sums so we can compare with Producer.
        double sum = 0;
        for (int i = 0; i < iterations; i++) {

            try {
                sum += consume();
                if (i % 100000 == 0 && i != 0)
                    System.out.printf("CONSUMER  [iteration: %d]: Sum of consumed items = %f.\n", i, sum);
            } catch (InterruptedException e) {
                System.err.println("CONSUMER [iteration: %d]: Interrupted!");
                break;
            }
        }
        System.out.printf("CONSUMER [iteration: 1000000]: DONE - Sum of consumed items = %f\n", sum);
    }

    private double consume() throws InterruptedException {
        synchronized (buffer) {

            while (buffer.isEmpty()) {
                buffer.wait();
            }

            double item = buffer.get();

            buffer.notifyAll();

            return item;
        }
    }
}