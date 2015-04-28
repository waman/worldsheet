package org.waman.worldsheet.observer

/**
 * This class is stateful.
 */
abstract class ObservableSetWithoutDatatype[S](val dataInfo:Set[String])
  extends ObservableSet[S]{

  require(dataEntries != null && dataEntries.nonEmpty)

  private var isValidateResult = false

  override def dataEntries: Set[String] = this.dataInfo
  override def supportDataEntry(name: String): Boolean = this.dataInfo.contains(name)

  override def getDatatype(name: String): Option[Class[_]] =
    if(supportDataEntry(name))
      Option(classOf[Any])
    else
      Option.empty

  override def apply(state: S): Map[String, Any] = {
    val result = calculate(state)
    validateResult(result)
    result
  }

  protected def calculate(state:S):Map[String,Any]

  private def validateResult(result:Map[String, Any]):Unit = {
    if(!this.isValidateResult){
      validateDataEntries(result)
      this.isValidateResult = true
    }
  }
}