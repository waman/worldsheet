package org.waman.worldsheet.observer

trait ObservableSet[S] extends (S => Map[String, Any]){

  def dataEntries:Set[String]
  def supportDataEntry(name:String):Boolean
  def getDatatype(name:String):Option[Class[_]]
}

abstract class ObservableSetWithDatatype[S](val dataInfo:Map[String, Class[_]])
    extends ObservableSet[S]{

  override def dataEntries: Set[String] = this.dataInfo.keySet

  override def supportDataEntry(name: String): Boolean = this.dataInfo.contains(name)

  override def getDatatype(name: String): Option[Class[_]] = this.dataInfo.get(name)
}

abstract class ObservableSetWithoutDatatype[S](val dataInfo:Set[String])
  extends ObservableSet[S]{

  override def dataEntries: Set[String] = this.dataInfo

  override def supportDataEntry(name: String): Boolean = this.dataInfo.contains(name)

  override def getDatatype(name: String): Option[Class[_]] =
    if(supportDataEntry(name))
      Option.empty// TODO Option(Class[Any])
    else
      Option.empty
}

object ObservableSet{

  def createObservableSet[S](dataInfo:Map[String,Class[_]])(algorithm:S => Map[String,Any]):ObservableSet[S] =
    new ObservableSetWithDatatype[S](dataInfo){
      override def apply(state:S):Map[String,Any] = algorithm(state)
    }

  def createObservableSet[S](names:Set[String])(algorithm:S => Map[String,Any]):ObservableSet[S] =
    new ObservableSetWithoutDatatype[S](names){
      override def apply(state:S):Map[String,Any] = algorithm(state)
    }

  def createObservable[S, V](name:String, datatype:Class[V])(algorithm:S => V) =
    new Observable[S, V](name, datatype) {
      override protected def calculate(state:S):V = algorithm(state)
    }

  def createObservable[S](name:String)(algorithm:S => Any) =
    createObservable[S, Any](name, classOf[Any])(algorithm)
}