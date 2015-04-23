package org.waman.worldsheet.simulation

import org.waman.worldsheet.{DataOutputter, PhysicalSimulation}

trait OneOutput extends PhysicalSimulation{

  val outputter:DataOutputter[Data]

  override def outputterProviders: List[(Param) => DataOutputter[Data]] =
    List((Param) => this.outputter)

  override protected def simulate(param: Param, dataSeq: Seq[Data]): Unit = {
    try{
      this.outputter.prepare()
      dataSeq.foreach(this.outputter.output)

    }finally{
      this.outputter.dispose()
    }
  }
}
