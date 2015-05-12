package org.waman.worldsheet

object SimulationUtil {

  val sep = System.getProperty("line.separator")
  
  def defaultFormatter[A]:A => String = any => any.toString


  def quotedJoin(names:Set[String]):String = quotedJoin(names.toSeq)
  def quotedJoin(names:Seq[String]):String = names.map("\""+_+"\"").mkString(", ")

  def classAndValue(any:Any):String = any.getClass.getName + " (value : " + any + ")"
}
