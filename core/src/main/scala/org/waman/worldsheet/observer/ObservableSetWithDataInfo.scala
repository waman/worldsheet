package org.waman.worldsheet.observer

/**
 * This class is stateful.
 */
abstract class ObservableSetWithDataInfo[S](val dataInfo:Map[String, Class[_]])
  extends ObservableSet[S]{

  require(dataInfo != null && dataInfo.nonEmpty)

  private var isValidateResult = false

  override def dataEntries: Set[String] = this.dataInfo.keySet
  override def supportDataEntry(name: String): Boolean = this.dataInfo.contains(name)

  override def getDatatype(name: String): Option[Class[_]] = this.dataInfo.get(name)

  override def apply(state: S): Map[String, Any] = {
    val result = this.calculate(state)
    validateResult(result)
    result
  }

  protected def calculate(state:S):Map[String, Any]

  private def validateResult(result:Map[String, Any]):Unit = {
    if(!this.isValidateResult){
      validateDataEntries(result)
      validateDatatypes(result)
      this.isValidateResult = true
    }
  }

  private def validateDatatypes(result:Map[String, Any]):Unit = {
    val mismatches = result.filter{
      case (key, value) => !getDatatype(key).get.isInstance(value)
    }.keySet

    if(mismatches.nonEmpty)
      throw new DatatypeMismatchException(mismatches, this.dataInfo, result)
  }
}