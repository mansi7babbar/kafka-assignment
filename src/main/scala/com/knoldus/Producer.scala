package com.knoldus

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Producer {
  def main(args: Array[String]): Unit = {
    createProducer("user")
  }

  def createProducer(topic: String): Unit = {
    val properties: Properties = new Properties()
    properties.put("bootstrap.servers", "localhost:9092")
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.put("value.serializer", "com.knoldus.CustomSerializer")
    properties.put("acks", "all")
    val producer = new KafkaProducer[String, User](properties)
    produceMessage(producer, topic)
  }

  def produceMessage(producer: KafkaProducer[String, User], topic: String): Unit = {
    val userList = new Users
    try {
      @scala.annotation.tailrec
      def sendUser(users: List[User]) {
        users match {
          case user :: rest =>
            producer.send(new ProducerRecord[String, User](topic, user.id.toString, user))
            sendUser(rest)
          case user :: Nil => producer.send(new ProducerRecord[String, User](topic, user.id.toString, user))
          case Nil => -1
        }
      }

      sendUser(userList.getUsers)
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      producer.close()
    }
  }
}
