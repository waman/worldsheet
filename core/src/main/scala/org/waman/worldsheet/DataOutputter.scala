package org.waman.worldsheet

trait DataOutputter[D]{

  def prepare()
  def output(data:D)
  def dispose()
}
