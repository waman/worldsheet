package org.waman.worldsheet.immutable

trait SimplePhysicalSystem extends PhysicalSystem{

  override type Params = Unit

  val initialState: State
  val stateMapper: State => State

  override val initialStateFactory = (p:Params) => initialState
  override val stateMapperFactory = (p:Params) => stateMapper

  def createPhysicalProcess(): Stream[State] = createPhysicalProcess(())
}