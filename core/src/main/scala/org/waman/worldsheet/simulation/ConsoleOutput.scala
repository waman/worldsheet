package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation
import org.waman.worldsheet.outputter.ConsoleOutputter

trait ConsoleOutput extends PhysicalSimulation{

  protected def formatter:(Data => String) = (data:Data) => data.toString

  override val outputterProviders = List(
    (param:Param) => new ConsoleOutputter[Data](formatter)
  )
}
