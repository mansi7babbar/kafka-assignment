package com.knoldus

import java.io.{File, PrintWriter}

class WriteMessage {
  def writeMessage(message: String): Unit = {
    val file = new File("./src/main/resources/messageLog.txt")
    val fileWrite = new PrintWriter(file)
    fileWrite.append(message)
    fileWrite.close()
  }
}
