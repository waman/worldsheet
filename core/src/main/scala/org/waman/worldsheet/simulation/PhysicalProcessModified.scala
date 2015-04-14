package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait PhysicalProcessModified extends PhysicalSimulation{

  protected def modifyPhysicalProcess(s:Seq[State]):Seq[State]

  override
  def newPhysicalProcess(param: Param) = modifyPhysicalProcess(super.newPhysicalProcess(param))
}
