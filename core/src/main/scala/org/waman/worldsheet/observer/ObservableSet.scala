package org.waman.worldsheet.observer

import org.waman.worldsheet.SimulationUtil._

trait ObservableSet[S] extends (S => Map[String, Any]){

  def dataEntries:Set[String]
  def supportDataEntry(name:String):Boolean
  def getDatatype(name:String):Option[Class[_]]

  def apply(state:S):Map[String, Any]

  /** This method is equivalent to apply() method. */
  def observe(state:S):Map[String, Any] = apply(state)

  protected def validateDataEntries(result:Map[String, Any]):Unit = {
    if (dataEntries != result.keySet) {
      throw new DataEntryMismatchException(dataEntries, result.keySet)
    }
  }
}


object ObservableSet{

  def createObservable[S, V](name:String, datatype:Class[V])(algorithm:S => V) =
    new Observable[S, V](name, datatype) {
      override protected def calculate(state:S):V = algorithm(state)
    }

  def createObservable[S](name:String)(algorithm:S => Any) =
    createObservable[S, Any](name, classOf[Any])(algorithm)


  def createObservableSet[S](dataInfo:Map[String,Class[_]])(algorithm:S => Map[String,Any]):ObservableSet[S] =
    new ObservableSetWithDataInfo[S](dataInfo){
      override def calculate(state:S):Map[String,Any] = algorithm(state)
    }

  def createObservableSet[S](names:String*)(algorithm:S => Map[String,Any]):ObservableSet[S] =
    new ObservableSetWithoutDatatype[S](names.toSet){
      override def calculate(state:S):Map[String,Any] = algorithm(state)
    }

  def createObservableSet[S](algorithm:S => Map[String,Any]):ObservableSet[S] =
    new ObservableSetWithoutDataInfo[S]{
      override def apply(state: S): Map[String, Any] = algorithm(state)
    }
}