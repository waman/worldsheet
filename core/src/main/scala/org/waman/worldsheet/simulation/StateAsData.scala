package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait StateAsData extends PhysicalSimulation{

  override type Data = State

  override val observer = (s:State) => s
}
