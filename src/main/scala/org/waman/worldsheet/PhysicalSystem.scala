package org.waman.worldsheet

import spire.math.Numeric

trait PhysicalSystem {

  type State
  type Params

  val initialStateFactory: Params => State
  val stateMapperFactory: Params => (State => State)
  val stateClass: Class[State]

  def createPhysicalProcess(params: Params): Stream[State] =
    Stream.iterate(initialStateFactory(params))(stateMapperFactory(params))

  lazy val stateElementMapperFactory: StateElementMapperFactory[State, Params] =
    new StateElementMapperFactory(stateClass)

  def createCumulativeMapper[T](
    name: String,
    calculator: (State, State, Params) => T,
    reducer: (T, T) => T
  ) = stateElementMapperFactory.createCumulativeMapper(name, calculator, reducer)

  def createIncrementalMapper[T: Numeric](
    name: String
  )(
    calculator: (State, State, Params) => T
  ) = stateElementMapperFactory.createIncrementalMapper(name)(calculator)

  def createIncrementalMapper[T: Numeric](
    name: String,
    diff: T
  ) = stateElementMapperFactory.createIncrementalMapper(name)((c, n, p) => diff)

  def createDecrementalMapper[T: Numeric](
    name: String
  )(
    calculator: (State, State, Params) => T
  ) = stateElementMapperFactory.createDecrementalMapper(name)(calculator)

  def createDecrementalMapper[T: Numeric](
    name: String,
    diff: T
  ) = stateElementMapperFactory.createDecrementalMapper(name)((c, n, p) => diff)
}