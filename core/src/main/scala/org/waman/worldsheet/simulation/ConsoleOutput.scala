package org.waman.worldsheet.simulation

trait ConsoleOutput extends OneOutput with DataOutputterFactory{

  override val outputter = newConsoleOutputter()
}
