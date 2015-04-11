package org.waman.worldsheet.simulation

import org.waman.worldsheet.{DataOutputter, PhysicalSimulation}

trait NoParams extends PhysicalSimulation{

  type Params = Unit

  protected val outputters: List[DataOutputter[Data]]

  def outputterProviders: List[Params => DataOutputter[Data]] =
    this.outputters.map(out => (params:Params) => out)

  def newPhysicalProcess(): Seq[State] = newPhysicalProcess(())
  def newDataSeq(): Seq[Data] = newDataSeq(())
  def simulate(): Unit = simulate(())
}
