package org.waman.worldsheet.outputter

import org.waman.worldsheet.DataOutputter

trait DataOutputterProvider[P, D] extends (P => DataOutputter[D])
