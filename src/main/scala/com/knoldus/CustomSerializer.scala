package com.knoldus

import java.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer

class CustomSerializer extends Serializer[User] {

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
  }

  override def serialize(topic: String, data: User): Array[Byte] = {
    try {
      val mapper = new ObjectMapper()
      val result = mapper.writeValueAsString(data).getBytes()
      result
    }
    catch {
      case ex: JsonProcessingException => throw new IllegalArgumentException(ex);
    }
  }

  override def close(): Unit = {
  }
}
