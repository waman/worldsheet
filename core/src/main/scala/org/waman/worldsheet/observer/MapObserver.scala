package org.waman.worldsheet.observer

import org.waman.worldsheet.Observer

import scala.collection.immutable.ListMap

class MapObserver[S](val obs:List[S => Map[String, Any]]) extends Observer[S, Map[String, Any]] {

  private type M = Map[String, Any]

  override def apply(state: S):M =
    obs.map(o => o(state)).foldLeft[M](new ListMap[String,Any]){
      (result, m) => result ++ m
    }
}