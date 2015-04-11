package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait DataSeqModified extends PhysicalSimulation{

  protected def modifyDataSeq(s:Seq[Data]):Seq[Data]

  override def newDataSeq(params:Params):Seq[Data] = modifyDataSeq(super.newDataSeq(params))
}
