package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait StateFiltered extends PhysicalSimulation{

  val stateFilter: State => Boolean

  abstract override
  def newPhysicalProcess(params:Params):Seq[State] =
    super.newPhysicalProcess(params).filter(this.stateFilter)
}
