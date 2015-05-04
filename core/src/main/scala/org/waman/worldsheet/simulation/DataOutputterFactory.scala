package org.waman.worldsheet.simulation

import java.io.File
import java.nio.charset.Charset
import java.nio.file.{Paths, Path}

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.outputter.{FileOutputter, ConsoleOutputter}

trait DataOutputterFactory extends PhysicalSimulation{

  protected val dataFormatter:Data => String = data => data.toString

  //***** DataOutputter factory methods *****
  protected def newConsoleOutputter(header:String = "",
                                    formatter:Data => String = this.dataFormatter):ConsoleOutputter[Data] =
    ConsoleOutputter[Data](header, formatter)

  protected def newFileOutputter(path:Path,
                                 charset:Charset = Charset.defaultCharset(),
                                 isOverride:Boolean = false,
                                 formatter:Data => String = this.dataFormatter):FileOutputter[Data] =
    new FileOutputter[Data](path, charset, isOverride, formatter)

  //***** implicit transformation to Path *****
  implicit def string2Path(fileName:String):Path = Paths.get(fileName)

  implicit def file2Path(file:File):Path = file.toPath
}