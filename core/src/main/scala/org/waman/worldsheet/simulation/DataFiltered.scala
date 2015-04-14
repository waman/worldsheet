package org.waman.worldsheet.simulation

import org.waman.worldsheet.PhysicalSimulation

trait DataFiltered extends PhysicalSimulation{

   val dataFilter: Data => Boolean

   abstract override
   def newDataSeq(param: Param):Seq[Data] =
     super.newDataSeq(param).filter(this.dataFilter)
 }
