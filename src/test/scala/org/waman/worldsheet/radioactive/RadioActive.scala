package org.waman.worldsheet.radioactive

import org.waman.worldsheet.SimulationUtil.dec1
import org.waman.worldsheet.{SimplePhysicalSimulation, SimplePhysicalSystem, StateMapper}

case class RadioActiveState(t:BigDecimal, n:Double)

case class RadioActiveMapper(dt:BigDecimal, lambda:Double)
  extends StateMapper[RadioActiveState]{

  override def apply(system: RadioActiveState): RadioActiveState =
    RadioActiveState(
      system.t + dt,
      system.n - lambda * system.n * dt.doubleValue()
    )
}

class RadioActiveSystem(n0: Double, lambda: Double)
    extends SimplePhysicalSystem{

  override type State = RadioActiveState
  override val initialState = RadioActiveState(dec1(0.0), n0)
  override val stateMapper = RadioActiveMapper(0.1, lambda)
}

case class RadioActiveData(time:BigDecimal, numberOfNuclei:Double)

class RadioActiveSimulation extends SimplePhysicalSimulation{

  override type State = RadioActiveState
  override type Data = RadioActiveData

  override val physicalSystem = new RadioActiveSystem(1.0e6, Math.log(2.0)/5.0)
  override val observer = (s: State) => new Data(s.t, s.n)
}