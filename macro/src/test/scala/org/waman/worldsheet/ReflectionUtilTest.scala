package org.waman.worldsheet

import org.scalatest.{FlatSpec, Matchers}

class ReflectionUtilTest extends FlatSpec with Matchers {

  "A ReflectionUtil" should "get property" in {
    val me = Person("waman", 100)

    ReflectionUtil.getProperty(me, "name") should be ("waman")
    ReflectionUtil.getProperty(me, "age") should be (100)
  }
}