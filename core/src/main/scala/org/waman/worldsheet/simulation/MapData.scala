package org.waman.worldsheet.simulation
import java.nio.charset.Charset
import java.nio.file.{Paths, Path}

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.outputter.{ConsoleOutputter, FileOutputter}

trait MapData extends PhysicalSimulation{

  override type Data = Map[String, Any]

  //***** DataOutputter factory methods *****
  protected def newConsoleOutputter(formatter:Data => String = dataFormatter)
  : ConsoleOutputter[Data] =
    new ConsoleOutputter[Data](formatter)

//  protected def newFileOutputter(path:Path,
//                                 charset:Charset = Charset.defaultCharset(),
//                                 isOverride:Boolean = false,
//                                  formatter:Data => String = dataFormatter):FileOutputter[Data] =
//    new FileOutputter[Data](path, charset, isOverride, formatter)

  protected def newFileOutputter(path:String,
                                 charset:Charset = Charset.defaultCharset(),
                                 isOverride:Boolean = false,
                                  formatter:Data => String = dataFormatter):FileOutputter[Data] =
    new FileOutputter[Data](Paths.get(path), charset, isOverride, formatter)
}