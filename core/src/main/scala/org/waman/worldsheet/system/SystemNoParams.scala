package org.waman.worldsheet.system

import org.waman.worldsheet.PhysicalSystem

trait SystemNoParams extends PhysicalSystem{

  type Params = Unit

  val initialState: State
  protected def newInitialState(params:Params):State = initialState

  val stateEvolver: State => State
  protected def newStateEvolver(params:Params) = stateEvolver

  def newPhysicalProcess(): Seq[State] = newPhysicalProcess(())
}
