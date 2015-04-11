package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait DataFiltered extends PhysicalSimulation{

   val dataFilter: Data => Boolean

   abstract override
   def newDataSeq(params:Params):Seq[Data] =
     super.newDataSeq(params).filter(this.dataFilter)
 }
