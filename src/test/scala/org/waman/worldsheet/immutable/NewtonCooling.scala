package org.waman.worldsheet.immutable

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.Util.dec

case class NewtonCoolingState(t:BigDecimal, T:Double)

class NewtonCoolingSystem(T0: Double, Tex: Double) extends PhysicalSystem{

  type State = NewtonCoolingState

  val dt = dec(0.1)

  val initialState = NewtonCoolingState(dec(0.0), T0)
  val stateMapper = (s: NewtonCoolingState) => NewtonCoolingState(
      s.t + dt,
      s.T - 0.03 * (s.T - Tex) * dt.doubleValue()
    )
}

case class NewtonCoolingData(time:BigDecimal, temperature:Double)

class NewtonCoolingSimulation extends PhysicalSimulation{

  type State = NewtonCoolingState
  type Data = NewtonCoolingData

  val physicalSystem = new NewtonCoolingSystem(82.3, 17.0)
  val observer = (s:State) => new Data(s.t, s.T)
}
