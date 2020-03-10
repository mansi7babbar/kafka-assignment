package com.knoldus

import net.manub.embeddedkafka.{EmbeddedKafka, EmbeddedKafkaConfig}
import org.apache.kafka.common.serialization.{Deserializer, Serdes, Serializer}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec

class UserSpec extends AnyFlatSpec with EmbeddedKafka with BeforeAndAfterAll {
  val topic = "test-user"
  implicit val config: EmbeddedKafkaConfig = EmbeddedKafkaConfig(kafkaPort = Constants.kafkaPort, zooKeeperPort = Constants.zookeeperPort)
  implicit val serializer: Serializer[String] = Serdes.String().serializer()
  implicit val deserializer: Deserializer[String] =Serdes.String().deserializer()
  "runs with embedded kafka" should "work" in {
    withRunningKafka {
      EmbeddedKafka.publishToKafka(topic, "test user")
      println(EmbeddedKafka.consumeFirstStringMessageFrom(topic).isEmpty)
    }
  }
}
