package org.waman.worldsheet.observer

abstract class ObservableSetWithoutDataInfo[S] extends ObservableSet[S]{

  private val message = "This ObservableSet don't support to access data information : "

  /**
   *  This method throws UnsupportedOperationException
   *  because of having no idea that any data is supported
   */
  override def dataEntries: Set[String] =
    throw new UnsupportedOperationException(this.message + "dataEntries")

  /**
   *  This method throws UnsupportedOperationException
   *  because of having no idea that any data is supported
   */
  override def supportDataEntry(name: String): Boolean =
    throw new UnsupportedOperationException(this.message + "supportDataEntry("+name+")")

  /**
   *  This method throws UnsupportedOperationException
   *  because of having no idea that any data is supported
   */
  override def getDatatype(name: String): Option[Class[_]] =
    throw new UnsupportedOperationException(this.message + "getDatatype("+name+")")
}