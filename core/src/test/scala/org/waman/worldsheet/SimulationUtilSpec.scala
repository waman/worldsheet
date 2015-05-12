package org.waman.worldsheet

import org.scalatest.{FlatSpec, Matchers}


class SimulationUtilSpec extends FlatSpec with Matchers {

  "A SimulationUtil" should "join a seq of strings with quotation" in {
    SimulationUtil.quotedJoin(Seq("abc", "def", "ghi")) should be ("\"abc\", \"def\", \"ghi\"")
  }

  it should "return a string representation of its class with value" in {
    SimulationUtil.classAndValue("string") should be ("java.lang.String (value : string)")
  }
}