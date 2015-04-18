package org.waman.worldsheet.simulation

import java.nio.charset.Charset
import java.nio.file.{Paths, Path}

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.outputter.{ConsoleOutputter, FileOutputter}

trait StringData extends PhysicalSimulation{

  override type Data = String

  override val observer = (state:State) => formatState(state)

  protected def formatState(state:State):String = state.toString

  override protected val dataFormatter: Data => String = identity

  //***** DataOutputter factory methods *****
  protected def newConsoleOutputter():ConsoleOutputter[Data] =
    new ConsoleOutputter[Data](dataFormatter)

//  protected def newFileOutputter(path:Path,
//                                 charset:Charset = Charset.defaultCharset(),
//                                 isOverride:Boolean = false):FileOutputter[Data] =
//    new FileOutputter[Data](path, charset, isOverride, dataFormatter)

  protected def newFileOutputter(path:String,
                                 charset:Charset = Charset.defaultCharset(),
                                 isOverride:Boolean = false):FileOutputter[Data] =
    new FileOutputter[Data](Paths.get(path), charset, isOverride, dataFormatter)
}