import scala.reflect.runtime.universe._

case class Person(name: String, age: Int)

object TypeTest extends App{

  val myType1: Type = typeTag[Person].tpe
  println(myType1)
  myType1.decls.take(10).foreach(println)
  println()

  val me = Person("waman", 100)
  val myType2: Type = typeTag[me.type].tpe
  println(myType2)
  myType2.decls.take(10).foreach(println)
}
