package org.waman.worldsheet.simulation

trait PhysicalProcessDebugged extends ConsoleOutput{

  override type Data = String

  override val observer = (state:State) => formatState(state)

  protected def formatState(state:State):String = state.toString
}
