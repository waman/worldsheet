package org.waman.worldsheet

import java.math.MathContext

object SimulationUtil {
  
  val mc1 = new MathContext(1)
  val mc2 = new MathContext(2)
  val mc3 = new MathContext(3)
  val mc4 = new MathContext(4)

  def dec1(d: Double) = BigDecimal(d, mc1)
  def dec2(d: Double) = BigDecimal(d, mc2)
  def dec3(d: Double) = BigDecimal(d, mc3)
  def dec4(d: Double) = BigDecimal(d, mc4)
}
