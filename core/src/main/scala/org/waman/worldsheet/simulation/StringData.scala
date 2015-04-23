package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait StringData extends PhysicalSimulation{

  override type Data = String

  override val observer = (state:State) => formatState(state)

  protected def formatState(state:State):String = state.toString
}