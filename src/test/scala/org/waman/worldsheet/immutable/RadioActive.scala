package org.waman.worldsheet.immutable

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.Util.dec

case class RadioActiveState(t:BigDecimal, n:Double)

case class RadioActiveMapper(dt:BigDecimal, lambda:Double)
  extends StateMapper[RadioActiveState]{

  def apply(system: RadioActiveState): RadioActiveState =
    RadioActiveState(
      system.t + dt,
      system.n - lambda * system.n * dt.doubleValue()
    )
}

class RadioActiveSystem(n0: Double, lambda: Double) extends PhysicalSystem{

  type State = RadioActiveState
  val initialState = RadioActiveState(dec(0.0), n0)
  val stateMapper = RadioActiveMapper(0.1, lambda)
}

case class RadioActiveData(time:BigDecimal, numberOfNuclei:Double){}

class RadioActiveSimulation extends PhysicalSimulation{

  type State = RadioActiveState
  type Data = RadioActiveData

  val physicalSystem = new RadioActiveSystem(1.0e6, Math.log(2.0)/5.0)
  val observer = (s: State) => new Data(s.t, s.n)
}