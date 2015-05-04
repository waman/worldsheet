package org.waman.worldsheet.outputter

import org.waman.worldsheet.DataOutputter

class ConsoleOutputter[D](val formatter:D => String) extends DataOutputter[D]{

  def prepare(): Unit = ()

  def output(data: D): Unit = {
    val message = formatter(data)
    println(message)
  }

  def dispose(): Unit = ()
}

object ConsoleOutputter{

  def apply[D](header:String, formatter:D => String):ConsoleOutputter[D] =  header match{
    case null =>
      new ConsoleOutputter[D](data => formatter(data))
    case "" =>
      new ConsoleOutputter[D](data => formatter(data))
    case h =>
      new ConsoleOutputter[D](data => h + " " + formatter(data))
  }
}