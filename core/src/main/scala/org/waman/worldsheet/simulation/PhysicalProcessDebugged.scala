package org.waman.worldsheet.simulation

trait PhysicalProcessDebugged extends ConsoleOutput{

  type Data = String

  val observer = (state:State) => formatState(state)

  protected def formatState(state:State):String = state.toString
}
