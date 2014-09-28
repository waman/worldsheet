package org.waman.worldsheet.immutable

import org.waman.worldsheet.Util.dec

case class NewtonCoolingState(time:BigDecimal, temper:Double)

class NewtonCoolingSystem extends PhysicalSystem {

  override type State = NewtonCoolingState
  override type Params = (Double, BigDecimal, Double) // (temper0, dt, temperEx)

  override val initialStateFactory: (Params => State) =
    params => NewtonCoolingState(dec(0.0), params._1)

  override val stateMapperFactory: (Params => (State => State)) =
    params => s => {
      val (_, dt, temperEx) = params
      new State(
        s.time + dt,
        s.temper - 0.03 * (s.temper - temperEx) * dt.doubleValue()
      )
    }
}

case class NewtonCoolingData(time:BigDecimal, temperature:Double)

class NewtonCoolingSimulation extends PhysicalSimulation{

  override type State = NewtonCoolingState
  override type Params = (Double, BigDecimal, Double)  // (temper0, dt, temperEx)
  override type Data = NewtonCoolingData

  override val physicalSystem = new NewtonCoolingSystem
  override val observer = (s:State) => new Data(s.time, s.temper)
}
