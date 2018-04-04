package net.cackerman.samples.ProducerConsumer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestBuffer.class,
        TestConsumer.class,
        TestProducer.class
})
public class TestSuite {
}
