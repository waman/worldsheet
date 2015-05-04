package org.waman.worldsheet

import java.lang.reflect.Method

import spire.math.Numeric
import spire.syntax.numeric._

trait StateElementEvolver[State, Param, T] {
  def apply(current: State, next: State, param: Param): T
}

object StateElementEvolver{

  private class CumulativeEvolver[State, Param, T](
    val name: String,
    val calculator: (State, State, Param) => T,
    val reducer: (T, T) => T
  )extends StateElementEvolver[State, Param, T]{

    private var accessor: Method = null

    override def apply(current: State, next: State, param: Param):T = {
      if(this.accessor == null)
        this.accessor = current.getClass.getMethod(this.name)

      val arg1 = this.accessor.invoke(current).asInstanceOf[T]
      val arg2 = calculator(current, next, param)
      reducer(arg1, arg2)
    }
  }

  def newCumulativeEvolver[State, Param, T](
    name: String,
    calculator: (State, State, Param) => T,
    reducer: (T, T) => T
  ): StateElementEvolver[State, Param, T] =
    new CumulativeEvolver(name, calculator, reducer)

  def newIncrementalEvolver[State, Param, T: Numeric]
      (name: String)(calculator: (State, State, Param) => T):
      StateElementEvolver[State, Param, T] =
    newCumulativeEvolver(name, calculator, (n0:T, n1:T) => n0 + n1)

  def newIncrementalEvolver[State, Param, T: Numeric]
      (name: String, diff: T):StateElementEvolver[State, Param, T] =
    newIncrementalEvolver[State, Param, T](name)((c, n, p) => diff)
}