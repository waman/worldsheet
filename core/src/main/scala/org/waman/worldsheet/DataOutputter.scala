package org.waman.worldsheet

trait DataOutputter[D]{

  def setUp()
  def output(data:D)
  def tearDown()
}
