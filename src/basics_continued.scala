object basics_continued {

  def main(args: Array[String]): Unit = {

    //package basicscontinued //pode se usar packages tbm :)

    class Foo {
      def apply() = "oi"
    }

    // nao instanciavel?
    object FooMaker {
      def apply() = new Foo() // como se fosse a operacao default da class/object
    }
    val foom = new Foo
    println(foom())

    /*
parecem classes staticas do java
Nao-instanciaveis (pelo o que percebi)
*/
    object Timer {
      var count = 0

      def currentCount(): Long = {
        count += 1
        count
      }
    }

    println(Timer.currentCount)
    println(Timer.currentCount)
    println(Timer.currentCount)

    class Bar(foo: String)

    object Bar {
      def apply(foo: String) = new Bar(foo)
    }

    val bbb = Bar

    // funcoes sao objetos
    object addOne extends Function[Int, Int] {
      def apply(m: Int): Int = m + 1
    }

    println(addOne(2))

    // classes tbm podem estender function LOL
    class AddOne extends Function[Int, Int] {
      def apply(m: Int): Int = m + 1
    }

    // tenho que instanciar antes de usar :P
    val plusOne = new AddOne
    println(plusOne(10))

    val times = 1

    // posso especificar o tipo 
    val times_s: String = times match {
      case 1 => "um"
      case 2 => "dois"
      case _ => "nao sei LOL"
    }

    println(times_s)

    // copiado do basics.scala
    class Calculator(brand: String, model: String) {
      def add(m: Int, n: Int): Int = m + n
    }
    /*
def calcType(calc: Calculator): String = calc match {
	case calc.brand == "hp" && calc.model == "20B" => "financeira"
  	case calc.brand == "hp" && calc.model == "48G" => "cientifica"
  	case calc.brand == "hp" && calc.model == "30B" => "bussiness "
  	case _ => "nao sei"
}

println(calcType(new Calculator("hp", "20B")))
*/

    // exceptions e case classes nao feitas aqui ainda, pq os exemplos estavam imcompletos, e ainda não sei prosseguir 
    // :(

  }

}