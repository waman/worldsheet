import scala.reflect.runtime.universe._

object MirrorTest extends App{

  val mirror: Mirror = runtimeMirror(getClass.getClassLoader)

  val myType: Type = typeOf[Person]

  // Class
  val myClass: ClassSymbol = myType.typeSymbol.asClass
  val classMirror: ClassMirror = mirror.reflectClass(myClass)
  println(classMirror)

  // Constructor
  val myCons: MethodSymbol = myType.decl(termNames.CONSTRUCTOR).asMethod
  val consMirror = classMirror.reflectConstructor(myCons)
  val me = consMirror("waman", 100)
  println(me)

  // Term(Field)
  val myTerm: TermSymbol = myType.decl(TermName("age")).asTerm
  val termMirror: FieldMirror = mirror.reflect(me).reflectField(myTerm)
  println(termMirror.get)

  // Method
  val myMethod: MethodSymbol = myType.decl(TermName("productIterator")).asMethod
  val methodMirror: MethodMirror = mirror.reflect(me).reflectMethod(myMethod)
  methodMirror().asInstanceOf[Iterator[Any]].foreach(println)
}
