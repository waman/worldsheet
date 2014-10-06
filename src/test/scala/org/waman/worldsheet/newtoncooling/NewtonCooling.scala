package org.waman.worldsheet.newtoncooling

import org.waman.worldsheet.{PhysicalSystem, PhysicalSimulation}
import org.waman.worldsheet.SimulationUtil.dec1

case class NewtonCoolingState(time:BigDecimal, temper:Double)

class NewtonCoolingSystem extends PhysicalSystem {

  override type State = NewtonCoolingState
  override type Params = (Double, BigDecimal, Double) // (temper0, dt, temperEx)
  override val stateClass: Class[State] = classOf[NewtonCoolingState]

  override val initialStateFactory: (Params => State) =
    params => NewtonCoolingState(dec1(0.1), params._1)

  override val stateMapperFactory: (Params => (State => State)) =
    params => {
      val (_, dt, temperEx) = params

      val dTime = createIncrementalMapper("time", dt)
      val dTemper = createDecrementalMapper("temper") { (c: State, n: State, p: Params) =>
        0.03 * (c.temper - temperEx) * dt.doubleValue()
      }

      current => {
        var next = current.copy()
        next = next.copy(time = dTime(current, next, params))
        next = next.copy(temper = dTemper(current, next, params))
        next
      }
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
