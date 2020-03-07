package com.knoldus

import java.io.{File, PrintWriter}
import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Producer extends App {

  def produceMessage(topic: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    val message =
      """{
        "id": 1,
        "name": "mansi",
        "password": "mansibabbar"
    }"""
    val record = new ProducerRecord[String, String](topic, "key", message)
    writeMessage(message)
    producer.send(record)
    producer.close()
  }

  def writeMessage(message: String): Unit = {
    val file = new File("./src/main/resources/messages.txt")
    val fileWrite = new PrintWriter(file)
    fileWrite.append(message)
    fileWrite.close()
  }

  produceMessage("kafka-assignment")
}
