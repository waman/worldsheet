package org.waman.worldsheet.immutable

trait PhysicalSystem{

  type State
  type Params

  val initialStateFactory: Params => State
  val stateMapperFactory: Params => (State => State)

  def createPhysicalProcess(params: Params): Stream[State] =
    Stream.iterate(initialStateFactory(params))(stateMapperFactory(params))
}
