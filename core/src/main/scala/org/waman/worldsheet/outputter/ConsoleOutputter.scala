package org.waman.worldsheet.outputter

import org.waman.worldsheet.DataOutputter

class ConsoleOutputter[D](val header:String, val formatter:D => String) extends DataOutputter[D]{

  def prepare(): Unit = ()

  def output(data: D): Unit = {
    val message = this.header + formatter(data)
    println(message)
  }

  def dispose(): Unit = ()
}