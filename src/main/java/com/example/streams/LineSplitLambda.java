package com.example.streams;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

public class LineSplitLambda {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-linesplit");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream("streams-plaintext-input");

        /*KStream<String, String> words = source.flatMapValues(new ValueMapper<String,Iterable<String>>() {

            public Iterable<String> apply(String value) {
                return Arrays.asList(value.split("\\W+"));
            }
            
        });
        source.to("streams-linesplit-output");*/

        // can do the same thing above via a Lambda
        //KStream<String, String> words = 
        source.flatMapValues(value -> Arrays.asList( value.split( "\\W+" )))
                                            .to("streams-linesplit-output");

        source.to("streams-linesplit-output");

        // could have done this
        //builder.stream("streams-plaintext-input").to("streams-pipe-output");

        final Topology topology = builder.build();

        System.out.println("our topology: " + topology.describe());

        final KafkaStreams streams = new KafkaStreams(topology, props);

        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
            System.out.println("*** Shutting down...");
        } catch (Throwable e) {
            System.exit(1);
        }

        System.exit(0); 
    }

}
