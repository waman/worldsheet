package org.waman.worldsheet.simulation

import org.waman.worldsheet.{DataOutputter, PhysicalSimulation}

trait NoParam extends PhysicalSimulation{

  override type Param = Unit

  protected def outputters: List[DataOutputter[Data]] = Nil

  override def outputterProviders: List[Param => DataOutputter[Data]] =
    this.outputters.map(out => (param:Param) => out)

  def newPhysicalProcess(): Seq[State] = newPhysicalProcess(())
  def newDataSeq(): Seq[Data] = newDataSeq(())

  //***** simulate methods *****
  def simulate():Unit = simulate(())

  def simulateWhileState(takeWhile:State => Boolean): Unit =
    simulateWhileState((), takeWhile)

  def simulateWithPhysicalProcess(stateFilter:Seq[State] => Seq[State]):Unit =
    simulateWithPhysicalProcess()(stateFilter)

  def simulateWhileData(takeWhile:Data => Boolean): Unit =
    simulateWhileData((), takeWhile)

  def simulateWithDataSeq(dataFilter:Seq[Data] => Seq[Data]):Unit =
    simulateWithDataSeq(())(dataFilter)
}
