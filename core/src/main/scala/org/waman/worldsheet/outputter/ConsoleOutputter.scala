package org.waman.worldsheet.outputter

import org.waman.worldsheet.DataOutputter
import org.waman.worldsheet.SimulationUtil.defaultFormatter

class ConsoleOutputter[D](val formatter:D => String = defaultFormatter) extends DataOutputter[D]{

  def prepare(): Unit = ()

  def output(data: D): Unit = {
    val message = formatter(data)
    println(message)
  }

  def dispose(): Unit = ()
}

object ConsoleOutputter{

  def apply[D](header:String, formatter:D => String = defaultFormatter):ConsoleOutputter[D] =  header match{
    case null =>
      new ConsoleOutputter[D](formatter)
    case "" =>
      new ConsoleOutputter[D](formatter)
    case h =>
      new ConsoleOutputter[D](data => h + " " + formatter(data))
  }
}