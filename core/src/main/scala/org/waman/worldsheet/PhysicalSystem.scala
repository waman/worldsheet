package org.waman.worldsheet

import spire.math.Numeric

trait PhysicalSystem {

  type State
  type Param

  protected def newInitialState(param: Param):State
  protected def newStateEvolver(param: Param):State => State

  def newPhysicalProcess(param: Param): Seq[State] =
    Stream.iterate(newInitialState(param))(newStateEvolver(param))


  protected def newCumulativeEvolver[T](
    name: String,
    calculator: (State, State, Param) => T,
    reducer: (T, T) => T
  ) = StateElementEvolver.newCumulativeEvolver[State, Param, T](name, calculator, reducer)

  protected def d[T: Numeric](name: String)(calculator: (State, State, Param) => T) =
    StateElementEvolver.newIncrementalEvolver[State, Param, T](name)(calculator)

  protected def d[T: Numeric](name: String, diff: T) =
    StateElementEvolver.newIncrementalEvolver[State, Param, T](name)((c, n, p) => diff)
}