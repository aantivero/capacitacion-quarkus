package ar.gov.agip;

import ar.gov.agip.model.Impuesto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.annotation.Identifier;

@QuarkusTest
public class ImpuestoProcessorTest {

    @Inject
    @Identifier("default-kafka-broker")
    Map<String, Object> kafkaConfig;

    KafkaProducer<String, String> impuestoRequestProducer;
    KafkaConsumer<String, Impuesto> impuestoConsumer;

    @Test
    void testProcessor() {
        impuestoConsumer.subscribe(Collections.singleton("impuestos"));
        UUID nombre = UUID.randomUUID();
        impuestoRequestProducer.send(new ProducerRecord<>("impuesto-requests", nombre.toString()));
        ConsumerRecords<String, Impuesto> records = impuestoConsumer.poll(Duration.ofMillis(10000));
        Impuesto impuesto = records.records("impuestos").iterator().next().value();
        assertEquals(impuesto.nombre, nombre.toString());
    }


    @BeforeEach
    void setUp() {
        impuestoConsumer = new KafkaConsumer<>(consumerConfig(), new StringDeserializer(), new ObjectMapperDeserializer<>(Impuesto.class));
        impuestoRequestProducer = new KafkaProducer<>(kafkaConfig, new StringSerializer(), new StringSerializer());
    }

    @AfterEach
    void tearDown() {
        impuestoRequestProducer.close();
        impuestoConsumer.close();
    }

    Properties consumerConfig() {
        Properties properties = new Properties();
        properties.putAll(kafkaConfig);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-id");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }
}
