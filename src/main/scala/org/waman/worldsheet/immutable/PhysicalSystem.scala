package org.waman.worldsheet.immutable

trait PhysicalSystem{

  type State
  val initialState: State
  val stateMapper: (State => State)

  def createPhysicalProcess(): Stream[State] = Stream.iterate(initialState)(stateMapper)
}
