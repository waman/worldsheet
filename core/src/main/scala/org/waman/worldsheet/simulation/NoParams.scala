package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait NoParams extends PhysicalSimulation{

  override type Params = Unit

  def createDataStream(): Seq[Data] = createDataStream(())

}
