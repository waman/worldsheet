package org.waman.worldsheet

class SimulationException(message:String, ex:Exception) extends Exception(message, ex){

  def this(message:String) = this(message, null)
}
