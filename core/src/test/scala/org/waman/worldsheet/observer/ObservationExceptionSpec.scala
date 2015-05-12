package org.waman.worldsheet.observer

import org.scalatest.{FlatSpec, Matchers}

class ObservationExceptionSpec extends FlatSpec with Matchers {

  "A DataEntryMismatchException" should "return desirable error message for too mach observation" in {
    val obs = new DataEntryMismatchException(Set("Current"), Set("Current", "Next"))
    obs.getMessage shouldBe """Observable mismatch:
                              |    "Next" must not be observed""".stripMargin
  }

  it should "return desirable error message for too little observation" in {
    val obs = new DataEntryMismatchException(Set("Current", "Next"), Set("Current"))
    obs.getMessage shouldBe """Observable mismatch:
                              |    "Next" must be observed""".stripMargin
  }

  it should "return desirable error message" in {
    val obs = new DataEntryMismatchException(Set("Current", "Next"), Set("Current", "next"))
    obs.getMessage shouldBe """Observable mismatch:
                              |    "next" must not be observed
                              |    "Next" must be observed""".stripMargin
  }

  "A DatatypeMismatchException" should "return desirable error message" in {
    val obs = new DatatypeMismatchException(
      Set("Current", "Next"),
      Map("Current" -> classOf[Integer], "Next" -> classOf[BigInt]),
      Map("Current" -> true, "Next" -> 1)
    )

    obs.getMessage shouldBe """Observable type mismatch:
                              |    "Current", "Next" are invalid datatype
                              |    "Current" must be java.lang.Integer, but actual be java.lang.Boolean (value : true)
                              |    "Next" must be scala.math.BigInt, but actual be java.lang.Integer (value : 1)""".stripMargin
  }
}
