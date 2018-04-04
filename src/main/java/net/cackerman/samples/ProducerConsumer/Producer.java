package net.cackerman.samples.ProducerConsumer;

import java.util.Random;

/**
 * Generates data to be placed into the buffer.
 */
public class Producer implements Runnable {

    private final CircularBuffer<Double> buffer;
    private final long iterations;

    Producer(CircularBuffer<Double> circularBuffer, long iterations) {
        this.buffer = circularBuffer;
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
                produce(bufferElement);
            } catch (InterruptedException e) {
                System.err.println("PRODUCER [iteration: %d]: Interrupted!");
                break;
            }
            sum += bufferElement;
            if (i % 100000 == 0 && i != 0)
                System.out.printf("PRODUCER  [iteration: %d]: Sum of produced items = %f.\n", i, sum);
        }
        System.out.printf("PRODUCER [iteration: %d]: DONE - Sum of produced items = %f\n", iterations, sum);
    }

    private void produce(Double bufferElement) throws InterruptedException {
        synchronized (buffer) {

            while (buffer.isFull()) {
                buffer.wait();
            }

            buffer.put(bufferElement);

            buffer.notify();
        }
    }
}