package org.waman.worldsheet

import org.waman.worldsheet.immutable.PhysicalSystem

trait PhysicalSimulation {

  type State
  type Data

  val physicalSystem: PhysicalSystem{ type State = PhysicalSimulation.this.State}
  val observer: (State => Data)

  def createDataStream(): Stream[Data] =
    physicalSystem.createPhysicalProcess().map(observer)
}