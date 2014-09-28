package org.waman.worldsheet.immutable

trait PhysicalSimulation {

  type State
  type Params
  type Data

  val physicalSystem: PhysicalSystem{
    type State = PhysicalSimulation.this.State
    type Params = PhysicalSimulation.this.Params
  }

  val observer: State => Data

  def createPhysicalProcess(params:Params): Stream[State] =
    physicalSystem.createPhysicalProcess(params)

  def createDataStream(params:Params): Stream[Data] =
    physicalSystem.createPhysicalProcess(params).map(observer)
}