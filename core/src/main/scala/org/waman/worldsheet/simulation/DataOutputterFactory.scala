package org.waman.worldsheet.simulation

import java.nio.charset.Charset
import java.nio.file.{Paths, Path}

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.outputter.{FileOutputter, ConsoleOutputter}

trait DataOutputterFactory extends PhysicalSimulation{

  //***** DataOutputter factory methods *****
  protected def newConsoleOutputter(formatter:Data => String = this.dataFormatter):ConsoleOutputter[Data] =
    new ConsoleOutputter[Data](formatter)

  protected def newFileOutputter(path:Path,
                                 charset:Charset,
                                 isOverride:Boolean,
                                 formatter:Data => String):FileOutputter[Data] =
    new FileOutputter[Data](path, charset, isOverride, formatter)

  protected def newFileOutputter(path:String,
                                 charset:Charset = Charset.defaultCharset(),
                                 isOverride:Boolean = false,
                                 formatter:Data => String = this.dataFormatter):FileOutputter[Data] =
    newFileOutputter(Paths.get(path), charset, isOverride, formatter)
}
