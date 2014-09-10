package org.waman.worldsheet

import java.math.MathContext

/**
 * Created by higashida on 2014/09/11.
 */
object Util {
  val mc = new MathContext(1)
  def dec(d: Double) = BigDecimal(d, mc)
}
