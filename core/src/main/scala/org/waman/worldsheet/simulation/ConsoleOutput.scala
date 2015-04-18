package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.outputter.ConsoleOutputter

trait ConsoleOutput extends PhysicalSimulation{

  override val outputterProviders = List(
    (param:Param) => new ConsoleOutputter[Data](dataFormatter)
  )
}
