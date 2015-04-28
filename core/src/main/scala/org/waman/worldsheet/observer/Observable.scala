package org.waman.worldsheet.observer

abstract class Observable[S, V](name:String, datatype:Class[V])
    extends ObservableSet[S]{

  require(name != null)
  require(datatype != null)

  override def dataEntries: Set[String] = Set(name)

  override def supportDataEntry(name: String): Boolean = this.name == name

  override def getDatatype(name: String): Option[Class[V]] =
    if(this.name == name)
      Option(this.datatype)
    else
      Option.empty

  override def apply(state:S): Map[String, Any] = Map(name -> calculate(state))

  protected def calculate(state:S):V
}