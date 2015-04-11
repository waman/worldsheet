package org.waman.worldsheet.outputter

import org.waman.worldsheet.DataOutputter

class ConsoleOutputter[D](formatter:D => String) extends DataOutputter[D]{

  def this() = this(data => data.toString)

  def setUp(): Unit = ()

  def output(data: D): Unit = println(formatter(data))

  def tearDown(): Unit = ()
}
