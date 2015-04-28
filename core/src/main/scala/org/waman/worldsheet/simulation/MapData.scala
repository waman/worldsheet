package org.waman.worldsheet.simulation

import java.nio.charset.Charset
import java.nio.file.Path

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.observer.{MapObserver, ObservableSet}
import org.waman.worldsheet.outputter.{ConsoleOutputter, FileOutputter}

trait MapData extends PhysicalSimulation with DataOutputterFactory{

  override type Data = Map[String, Any]

  protected def observableSets:List[ObservableSet[State]]
  override val observer = new MapObserver[State](observableSets)


  //***** ObservableSet Factory methods *****
  protected def observable[V](name:String, datatype:Class[V])(algorithm:State => V)
    :ObservableSet[State] = ObservableSet.createObservable(name, datatype)(algorithm)

  protected def observable(name:String)(algorithm:State => Any)
    :ObservableSet[State] = ObservableSet.createObservable(name)(algorithm)


  protected def observableSet(dataInfo:Map[String,Class[_]])(algorithm:State => Map[String, Any])
    :ObservableSet[State] = ObservableSet.createObservableSet(dataInfo)(algorithm)

  protected def observableSet(names:String*)(algorithm:State => Map[String, Any])
    :ObservableSet[State] = ObservableSet.createObservableSet(names:_*)(algorithm)

  protected def observableSet(algorithm:State => Map[String, Any])
    :ObservableSet[State] = ObservableSet.createObservableSet(algorithm)


  //***** DataOutputter factory methods *****
  protected def console(dataEntries:List[String] = Nil,
                        header:String = "",
                        sep:String = " ",
                        pad:Int = 20):ConsoleOutputter[Data] =
    newConsoleOutputter(header, consoleDataFormatter(dataEntries, sep, pad))

  private def consoleDataFormatter(dataEntries:List[String],
                                   sep:String,
                                   pad:Int):Data => String =
    dataEntries match {
      case Nil =>
        (data:Data) => data.values.map(formatAndPad(_, pad)).mkString(sep)
      case entries:List[String] =>
        (data:Data) => entries.map(data.get).map(formatAndPad(_, pad)).mkString(sep)
    }

  private def formatAndPad(value:Any, pad:Int):String = value.toString.padTo(pad, ' ')


  protected def file(path:Path,
                     charset:Charset = Charset.defaultCharset(),
                     isOverride:Boolean = false,
                     dataEntries:List[String] = Nil,
                     sep:String):FileOutputter[Data] =
    newFileOutputter(path, charset, isOverride, fileDataFormatter(dataEntries, sep))

  private def fileDataFormatter(dataEntries:List[String],
                                sep:String):Data => String =
    dataEntries match {
      case Nil =>
        (data:Data) => data.values.mkString(sep)
      case entries:List[String] =>
        (data:Data) => entries.map(data.get).mkString(sep)
    }

  protected def ssv(path:Path,
                    charset:Charset = Charset.defaultCharset(),
                    isOverride:Boolean = false,
                    dataEntries:List[String] = Nil):FileOutputter[Data] =
    file(path, charset, isOverride, dataEntries, " ")

  protected def tsv(path:Path,
                    charset:Charset = Charset.defaultCharset(),
                    isOverride:Boolean = false,
                    dataEntries:List[String] = Nil):FileOutputter[Data] =
    file(path, charset, isOverride, dataEntries, "\t")

  protected def csv(path:Path,
                    charset:Charset = Charset.defaultCharset(),
                    isOverride:Boolean = false,
                    dataEntries:List[String] = Nil):FileOutputter[Data] =
    file(path, charset, isOverride, dataEntries, ",")
}