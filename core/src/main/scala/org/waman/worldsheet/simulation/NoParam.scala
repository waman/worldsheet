package org.waman.worldsheet.simulation

import org.waman.worldsheet.{DataOutputter, PhysicalSimulation}

trait NoParam extends PhysicalSimulation{

  override type Param = Unit

  protected val outputters: List[DataOutputter[Data]] = Nil

  override val outputterProviders: List[Param => DataOutputter[Data]] =
    this.outputters.map(out => (param:Param) => out)

  def newPhysicalProcess(): Seq[State] = newPhysicalProcess(())
  def newDataSeq(): Seq[Data] = newDataSeq(())
  def simulate(): Unit = simulate(())
}
