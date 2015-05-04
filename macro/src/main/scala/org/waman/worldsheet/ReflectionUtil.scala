package org.waman.worldsheet

import scala.reflect.macros.blackbox

object ReflectionUtil {

  def getProperty(obj:Any, name:String):Any = macro getPropertyImpl

  def getPropertyImpl(c: blackbox.Context)(obj:c.Tree, name:c.Tree) = {
    import c.universe._
    println(showRaw(name))
    val Literal(Constant(propName: String)) = name
    val propTName = TermName(propName)
    q"$obj $propTName"
  }
}
