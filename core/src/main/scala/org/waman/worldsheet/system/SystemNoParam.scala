package org.waman.worldsheet.system

import org.waman.worldsheet.PhysicalSystem

trait SystemNoParam extends PhysicalSystem{

  type Param = Unit

  val initialState: State
  protected def newInitialState(param: Param):State = initialState

  val stateEvolver: State => State
  protected def newStateEvolver(param: Param) = stateEvolver

  def newPhysicalProcess(): Seq[State] = newPhysicalProcess(())
}
