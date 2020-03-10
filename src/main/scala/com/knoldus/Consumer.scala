package com.knoldus

import java.io.{File, PrintWriter}
import java.time.Duration
import java.util
import java.util.Properties

import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._

object Consumer {
  def main(args: Array[String]): Unit = {
    createConsumer("user")
  }

  def createConsumer(topic: String): Unit = {
    val properties = new Properties()
    properties.put("bootstrap.servers", "localhost:9092")
    properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.put("value.deserializer", "com.knoldus.CustomDeserializer")
    properties.put("auto.offset.reset", "latest")
    properties.put("group.id", "consumer-group")
    val consumer: KafkaConsumer[String, User] = new KafkaConsumer[String, User](properties)
    consumeMessage(consumer, topic)
  }

  def consumeMessage(consumer: KafkaConsumer[String, User], topic: String): Unit = {
    val file = new File("./src/main/resources/messageLog.txt")
    val fileWrite = new PrintWriter(file)
    consumer.subscribe(util.Arrays.asList(topic))
    while (true) {
      implicit val formats: DefaultFormats.type = DefaultFormats
      val timeout = 1000
      val record = consumer.poll(Duration.ofMillis(timeout)).asScala
      val userDataInJson = for (data <- record.iterator) yield write(data.value())
      for (user <- userDataInJson) {
        fileWrite.append(user)
        println(user)
      }
    }
  }
}
