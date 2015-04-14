package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait StateFiltered extends PhysicalSimulation{

  val stateFilter: State => Boolean

  abstract override
  def newPhysicalProcess(param: Param):Seq[State] =
    super.newPhysicalProcess(param).filter(this.stateFilter)
}
