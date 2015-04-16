package org.waman.worldsheet.simulation

import org.waman.worldsheet.{DataOutputter, PhysicalSimulation}

trait NoParam extends PhysicalSimulation{

  override type Param = Unit

  protected val outputters: List[DataOutputter[Data]] = Nil

  override val outputterProviders: List[Param => DataOutputter[Data]] =
    this.outputters.map(out => (param:Param) => out)

  def newPhysicalProcess(): Seq[State] = newPhysicalProcess(())
  def newDataSeq(): Seq[Data] = newDataSeq(())


  def simulateWhileStateIs(takeWhile:State => Boolean): Unit =
    simulateWhileStateIs((), takeWhile)

  def simulateWithPhysicalProcessModified(stateFilter:Seq[State] => Seq[State]):Unit =
    simulateWithPhysicalProcessModified()(stateFilter)

  def simulateWhileDataIs(takeWhile:Data => Boolean): Unit =
    simulateWhileDataIs((), takeWhile)

  def simulateWithDataSeqModified(dataFilter:Seq[Data] => Seq[Data]):Unit =
    simulateWithDataSeqModified(())(dataFilter)
}
