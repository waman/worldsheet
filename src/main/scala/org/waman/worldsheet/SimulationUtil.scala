package org.waman.worldsheet

import java.math.MathContext
import scala.reflect.runtime.universe._

object SimulationUtil {
  
  val mc1 = new MathContext(1)
  val mc2 = new MathContext(2)
  val mc3 = new MathContext(3)
  val mc4 = new MathContext(4)

  def dec1(d: Double) = BigDecimal(d, mc1)
  def dec2(d: Double) = BigDecimal(d, mc2)
  def dec3(d: Double) = BigDecimal(d, mc3)
  def dec4(d: Double) = BigDecimal(d, mc4)

  // Reflection Utilities
  lazy val mirror: Mirror = runtimeMirror(getClass.getClassLoader)

//  def getFieldSymbol[T](name: String): TermSymbol = typeOf[T].decl(TermName(name)).asTerm
//
//  def getMethodSymbol[T](name: String): MethodSymbol = typeOf[T].decl(TermName(name)).asMethod
//
//  def getField(obj: Any, field: TermSymbol): FieldMirror = mirror.reflect(obj).reflectField(field)
//  def getMethod(obj: Any, method: MethodSymbol): MethodMirror = mirror.reflect(obj).reflectMethod(method)
}
