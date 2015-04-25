package org.waman.worldsheet.simulation

import java.nio.charset.Charset
import java.nio.file.Path

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.outputter.{ConsoleOutputter, FileOutputter}

trait StringData extends PhysicalSimulation with DataOutputterFactory{

  override type Data = String

  override val observer = (state:State) => formatState(state)

  protected def formatState(state:State):String = state.toString

  //***** DataOutputter factory methods *****
  protected def console(header:String = ""):ConsoleOutputter[Data] =
    newConsoleOutputter(header)

  protected def file(path:Path,
                     charset:Charset = Charset.defaultCharset(),
                     isOverride:Boolean = false):FileOutputter[Data] =
    newFileOutputter(path, charset, isOverride, identity[String])
}