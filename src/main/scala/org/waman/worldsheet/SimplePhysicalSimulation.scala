package org.waman.worldsheet

trait SimplePhysicalSimulation extends PhysicalSimulation{

  override type Params = Unit

  def createPhysicalProcess(): Stream[State] = createPhysicalProcess(())
  def createDataStream(): Stream[Data] = createDataStream(())
}
