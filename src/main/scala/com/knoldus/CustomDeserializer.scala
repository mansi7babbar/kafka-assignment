package com.knoldus

import java.io.{ByteArrayInputStream, ObjectInputStream}
import java.util

import org.apache.kafka.common.serialization.Deserializer

class CustomDeserializer extends Deserializer[User] {

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def deserialize(topic: String, bytes: Array[Byte]): User = {
    val byteIn = new ByteArrayInputStream(bytes)
    val objIn = new ObjectInputStream(byteIn)
    val obj = objIn.readObject().asInstanceOf[User]
    byteIn.close()
    objIn.close()
    obj
  }

  override def close(): Unit = {

  }
}
