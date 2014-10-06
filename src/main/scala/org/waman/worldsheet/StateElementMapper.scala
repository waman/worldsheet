package org.waman.worldsheet

import spire.math.Numeric
import spire.syntax.numeric._

trait StateElementMapper[State, Params, T] {
  def apply(current: State, next: State, params: Params): T
}

class StateElementMapperFactory[State, Params](
  val stateClass: Class[State]
){
  private class CumulativeMapper[T](
    val name: String,
    val calculator: (State, State, Params) => T,
    val reducer: (T, T) => T
  )extends StateElementMapper[State, Params, T]{

    lazy val accessor = stateClass.getDeclaredMethod(name)

    override def apply(current: State, next: State, params: Params):T = {
      val arg1 = this.accessor.invoke(current).asInstanceOf[T]
      val arg2 = calculator(current, next, params)
      reducer(arg1, arg2)
    }
  }

  def createCumulativeMapper[T](
    name: String,
    calculator: (State, State, Params) => T,
    reducer: (T, T) => T
  ): StateElementMapper[State, Params, T] =
    new CumulativeMapper(name, calculator, reducer)

  def createIncrementalMapper[T: Numeric](
    name: String
  )(
    calculator: (State, State, Params) => T
  ): StateElementMapper[State, Params, T] =
    createCumulativeMapper(name, calculator, (n0:T, n1:T) => n0 + n1)

  def createIncrementalMapper[T: Numeric](
    name: String,
    diff: T
  ): StateElementMapper[State, Params, T] =
    createIncrementalMapper[T](name)((c, n, p) => diff)

  def createDecrementalMapper[T: Numeric](
    name: String
  )(
    calculator: (State, State, Params) => T
  ): StateElementMapper[State, Params, T] =
    createCumulativeMapper(name, calculator, (n0:T, n1:T) => n0 - n1)

  def createDecrementalMapper[T: Numeric](
    name: String,
    diff: T
  ): StateElementMapper[State, Params, T] =
    createDecrementalMapper[T](name)((c, n, p) => diff)
}