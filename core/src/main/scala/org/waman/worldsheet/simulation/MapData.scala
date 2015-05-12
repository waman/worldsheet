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
  protected def observableSet(dataInfo:Map[String,Class[_]])(algorithm:State => Map[String, Any])
  :ObservableSet[State] = ObservableSet.createObservableSet(dataInfo)(algorithm)

  protected def observableSet(names:String*)(algorithm:State => Map[String, Any])
  :ObservableSet[State] = ObservableSet.createObservableSet(names:_*)(algorithm)

  protected def observableSet(algorithm:State => Map[String, Any])
  :ObservableSet[State] = ObservableSet.createObservableSet(algorithm)


  protected def observable[V](name:String, datatype:Class[V])(algorithm:State => V)
  :ObservableSet[State] = ObservableSet.createObservable(name, datatype)(algorithm)

  protected def observable(name:String)(algorithm:State => Any)
  :ObservableSet[State] = ObservableSet.createObservable(name)(algorithm)


  //***** DataOutputter factory methods *****
  protected def console(dataEntries:List[String] = Nil,
                        header:String = "",
                        separator:String = " ",
                        pad:Int = 20):ConsoleOutputter[Data] = {
    if(pad <= 0)throw new IllegalArgumentException("Argument \"pad\" must be greater than 1: actual "+pad)
    newConsoleOutputter(header, consoleDataFormatter(dataEntries, separator, pad))
  }

  private def consoleDataFormatter(dataEntries:List[String],
                                   sep:String,
                                   pad:Int):Data => String =
    dataEntries match {
      case Nil =>
        data => data.values.map(formatAndPad(_, pad)).mkString(sep)
      case entries:List[String] =>
        data => entries.map(data(_)).map(formatAndPad(_, pad)).mkString(sep)
    }

  private def formatAndPad(value:Any, pad:Int):String = {
    val s = value.toString
    s.length match {
      case n if n == pad => s
      case n if n <  pad => (" " * (pad - s.length)) + s
      case n if n >  pad => s.substring(0, pad-1)+"_"
    }
  }


  protected def file(path:Path,
                     dataEntries:List[String] = Nil,
                     charset:Charset = Charset.defaultCharset,
                     isOverride:Boolean = false,
                     separator:String):FileOutputter[Data] =
    newFileOutputter(path, charset, isOverride, fileDataFormatter(dataEntries, separator))

  private def fileDataFormatter(dataEntries:List[String],
                                separator:String):Data => String =
    dataEntries match {
      case Nil =>
        data => data.values.mkString(separator)
      case entries:List[String] =>
        data => entries.map(data(_)).mkString(separator)
    }

  protected def ssv(path:Path,
                    dataEntries:List[String] = Nil,
                    charset:Charset = Charset.defaultCharset,
                    isOverride:Boolean = false):FileOutputter[Data] =
    file(path, dataEntries, charset, isOverride, " ")

  protected def tsv(path:Path,
                    dataEntries:List[String] = Nil,
                    charset:Charset = Charset.defaultCharset,
                    isOverride:Boolean = false):FileOutputter[Data] =
    file(path, dataEntries, charset, isOverride, "\t")

  protected def csv(path:Path,
                    dataEntries:List[String] = Nil,
                    charset:Charset = Charset.defaultCharset,
                    isOverride:Boolean = false):FileOutputter[Data] =
    file(path, dataEntries, charset, isOverride, ",")
}