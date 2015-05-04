package org.waman.worldsheet

trait PhysicalSimulation{

  type State
  type Param
  type Data

  type SystemType = PhysicalSystem{
    type State = PhysicalSimulation.this.State
    type Param = PhysicalSimulation.this.Param
  }

  def physicalSystem: SystemType
  def observer: State => Data
  def outputterProviders: List[Param => DataOutputter[Data]] = Nil


  //***** PhysicalProcess/DataSeq Factory methods *****
  def newPhysicalProcess(param: Param):Seq[State] =
    this.physicalSystem.newPhysicalProcess(param)

  def newDataSeq(param: Param): Seq[Data] =
    newDataSeq(newPhysicalProcess(param))

  private def newDataSeq(pp:Seq[State]):Seq[Data] = pp.map(this.observer)


  //***** simulate methods *****
  def simulate(param:Param):Unit = simulate(param, newDataSeq(param))

  def simulateWhileState(param: Param, takeWhile:State => Boolean): Unit =
    simulateWithPhysicalProcess(param){ _.takeWhile(takeWhile) }

  def simulateWithPhysicalProcess(param:Param)(stateFilter:Seq[State] => Seq[State]):Unit =
    simulate(param, newDataSeq(stateFilter(newPhysicalProcess(param))))

  def simulateWhileData(param: Param, takeWhile:Data => Boolean): Unit =
    simulateWithDataSeq(param){ _.takeWhile(takeWhile) }

  def simulateWithDataSeq(param:Param)(dataFilter:Seq[Data] => Seq[Data]):Unit =
    simulate(param, dataFilter(newDataSeq(param)))

  protected def simulate(param:Param, dataSeq:Seq[Data]): Unit = {
    val outs = this.outputterProviders.map(_.apply(param))

    try{
      outs.foreach(_.prepare())

      dataSeq.foreach(data =>
        outs.foreach(out =>
          out.output(data)
        )
      )
    }finally{
      outs.foreach(_.dispose())
    }
  }
}