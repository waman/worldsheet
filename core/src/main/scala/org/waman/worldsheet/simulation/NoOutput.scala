package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait NoOutput extends PhysicalSimulation{

  val outputterProviders = Nil
}
