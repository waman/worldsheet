package org.waman.worldsheet.system

import org.waman.worldsheet.PhysicalSystem

trait SystemNoParams extends PhysicalSystem{

  override type Params = Unit

  val initialState: State
  val stateEvolver: State => State

  override protected def newInitialState(params:Params):State = initialState
  override protected def newStateEvolver(params:Params) = stateEvolver

  def createPhysicalProcess(): Seq[State] = createPhysicalProcess(())
}
