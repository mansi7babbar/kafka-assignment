package com.knoldus

import net.liftweb.json.DefaultFormats

import scala.io.{BufferedSource, Source}

class Users {
  val bufferedSource: BufferedSource = Source.fromFile("./src/main/resources/message.txt")
  val jsonData: String = bufferedSource.mkString
  implicit val formats: DefaultFormats.type = DefaultFormats

  def getUsers: List[User] = {
    val parsedJsonData = net.liftweb.json.parse(jsonData)
    parsedJsonData.children map { user =>
      user.extract[User]
    }
  }
}
