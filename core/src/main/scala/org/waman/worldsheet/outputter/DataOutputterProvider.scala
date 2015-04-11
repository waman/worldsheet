package org.waman.worldsheet.outputter

import org.waman.worldsheet.DataOutputter

class DataOutputterProvider[P, D] extends (P => DataOutputter[D]){

  override def apply(v1: P): DataOutputter[D] = ???
}
