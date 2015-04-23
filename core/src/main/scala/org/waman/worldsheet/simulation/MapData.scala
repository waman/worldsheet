package org.waman.worldsheet.simulation

import java.nio.charset.Charset
import java.nio.file.{Paths, Path}

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.outputter.{FileOutputter, ConsoleOutputter}

trait MapData extends PhysicalSimulation with DataOutputterFactory{

  override type Data = Map[String, Any]

  //***** DataOutputter factory methods *****
  protected def newConsoleOutputter(dataEntries:List[String] = Nil,
                                    sep:String = " ",
                                    pad:Int = 20):ConsoleOutputter[Data] =
    newConsoleOutputter(consoleDataFormatter(dataEntries, sep, pad))

  private def consoleDataFormatter(dataEntries:List[String],
                                   sep:String,
                                   pad:Int):Data => String =
    dataEntries match {
      case Nil =>
        (data:Data) => data.values.map(form(_, pad)).mkString(sep)
      case entries:List[String] =>
        (data:Data) => entries.map(data.get).map(form(_, pad)).mkString(sep)
    }

  private def form(value:Any, pad:Int):String = value.toString.padTo(pad, ' ')


  protected def newFileOutputter(path:Path,
                                 charset:Charset,
                                 isOverride:Boolean,
                                 dataEntries:List[String],
                                 sep:String):FileOutputter[Data] =
    newFileOutputter(path, charset, isOverride, fileDataFormatter(dataEntries, sep))

  protected def newFileOutputter(path:String,
                                 charset:Charset = Charset.defaultCharset(),
                                 isOverride:Boolean = false,
                                 dataEntries:List[String] = Nil,
                                 sep:String = " "):FileOutputter[Data] =
    newFileOutputter(Paths.get(path), charset, isOverride, fileDataFormatter(dataEntries, sep))

  private def fileDataFormatter(dataEntries:List[String],
                                sep:String):Data => String =
    dataEntries match {
      case Nil =>
        (data:Data) => data.values.mkString(sep)
      case entries:List[String] =>
        (data:Data) => entries.map(data.get).mkString(sep)
    }
}