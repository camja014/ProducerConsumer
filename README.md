# ProducerConsumer

I have posted this as a small sample project to showcase some of my Java development experience.

This is a refactored version of an assignment for an operating systems class that I took in 2016. The goal of the assignment was 
to build a solution to the "Producer-Consumer" problem, in which you may have a system that produces more information 
than can be consumed by the consumer system (or vice-versa).

Our assignment requirements were to build a buffer, producer, and consumer classes. The producer and consumer systems 
must each run in their own threads, operating on the same instance of the buffer object. The producer object had to 
produce so many thousands of data elements, and the consumer had to consume all of those elements. Each system had
to sum the produced/consumed elements, and print the sum out to the console to show that both systems were operating as 
they should.

As they are running in separate threads, there is no way to predict in which order the thread operations will be executed.
In my solution, both the consumer and producer classes will synchronize on the single buffer object. The producer will 
`wait()`  on the buffer when it is full, while the consumer will `wait()` on the buffer when it is empty.

I have added test cases to verify functionality, and test a few edge cases. I have also added the `pom.xml` file so that
it can be built and tested using Maven and is IDE agnostic.

This project, among others, can be found on my GitHub account at [https://github.com/camja014](https://github.com/camja014)
