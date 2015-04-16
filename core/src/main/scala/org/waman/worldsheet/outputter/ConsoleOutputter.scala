package org.waman.worldsheet.outputter

import org.waman.worldsheet.DataOutputter

class ConsoleOutputter[D](formatter:D => String) extends DataOutputter[D]{

  def prepare(): Unit = ()

  def output(data: D): Unit = println(formatter(data))

  def dispose(): Unit = ()
}

object ConsoleOutputter{

  def apply[D](): ConsoleOutputter[D] = apply[D]((data:D) => data.toString)

  def apply[D](formatter:D => String): ConsoleOutputter[D] = new ConsoleOutputter[D](formatter)
}