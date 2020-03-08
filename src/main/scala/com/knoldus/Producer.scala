package com.knoldus

import java.io.{File, PrintWriter}
import java.util.Properties

import net.liftweb.json.{DefaultFormats, parse}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Producer extends App {

  def produceMessage(topic: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", classOf[CustomSerializer])
    val producer = new KafkaProducer[String, User](props)
    val message =
      """{
        "id": 1,
        "name": "mansi",
        "age": 21
        "address": "Delhi"
    }"""
    implicit val formats: DefaultFormats.type = DefaultFormats
    val user = parse(message).extract[User]
    val record = new ProducerRecord[String, User](topic, "key", user)
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

  produceMessage("user")
}
