object type_polymorphism_basics {

  /*
   * polimorfismo paramétrico
   * inferência de tipo (local)
   * quantificação existencial
   * visualizações
   */

  /*
   * Parametric polymorphism - polimorfismo paramétrico
   */

  // irar gerar um List[Any]
  val t = 2 :: 1 :: "bar" :: "foo" :: Nil

  // até onde entendi até agora, o [A] é tipo o Generics do Java ...
  def drop[A](l: List[A]) = l.tail

  def toList[A](a: A) = List(a)

  // isso nao compila, pq a Scala tem polimorfismo rank1, ou seja, todos os tipos
  // devem ser inferidos no ato da invocacao.
  //  def foo[A, B](f: A => List[A], b: B, c: Int) = (f(b), f(c))

  /*
   * TYPE INFERENCE
   */
  // é local, voce nao pode fazer isso:
  //  { x => x}

  // mas pode, por exemplo:
  def id[T](x: T) = x
  val x = id(123)
  val y = id("oi")
  val z = id(Array(1, 2, 3, 4))

  /*
   * Variancia
   * 
   * temos 3 tipos de variancia em scala
   * -covariant	  	C[T’] é uma subclasse de C[T] 
   * -contravariant	C[T] uma subclasse de C[T’]
   * -invariant	  	C[T] e C[T’] não estão associados
   */

  // crio uma classe de exemplo. O + indeica que ela é uma classe covariante
  class Covariant[+A]

  // funciona like a boss
  val cv: Covariant[AnyRef] = new Covariant[String]

  // erro de tipagem, String é um subtipo de AnyRef, mas o contrário não é válido!
  //  val vc: Covariant[String] = new Covariant[AnyRef]

  // agora, vou criar uma classe contravariante
  class Contravariante[-A]

  // funciona, pq aqui ocorro o contrário da covariante!
  val ccv: Contravariante[String] = new Contravariante[AnyRef]
  //  e isso vai falhar pelo mesmo motivo que a linha anterior funciona :)
  //  val fail: Contravariante[AnyRef] = new Contravariante[String]

  // pode parecer estranho, e qual o uso? qtal:
  trait Function1[-T1, +R] extends AnyRef

  // incrivelmente, esse troço que fiz ali funciona :D
  class Fn extends Function1[Int, Int] {
    def go: AnyRef = "GO GO POWER RANGEEERSSSS!!!"
  }
  val fn: Fn = new Fn()

  // criando diversas classes
  class A
  class B extends A
  class C extends B
  class D extends C

  // e umas funcoes 
  val f: (B => C) = ((b: B) => new C)
  // isso vai criar uma funcao do tipo: f: (B) => C = <function1>
  // bem como a proxima..
  //  val f: (B => C) = ((a: A) => new C)

  // isso só vai dar pau se fizer assim
  //   val f: (B => C) = ((c: C) => new C)
  // pq ele espera um (C) => B, não um (C) => C, como é o noso caso...

  /*
   * BOUNDS
   * 
   * permite polimorfismo restrito!
   */

  class F { def foo = "F" }
  class E extends F { override def foo = "E" } // sobrecarga sem fodendo annotations!

  // isso nao funciona, pq o compilador não tem duck typing, ele não sabe se a classe recebida tera o método foo!
  //  def callFoo[T](foos: Seq[T]) = foos map (_.foo) foreach println
  //  pode-se resolver isso fazendo assim:
  def callFoo[T <: F](foos: Seq[T]) = foos map (_.foo) foreach println
  // em T<:F estou dizendo que T vai ser um subtipo de F, é parecido com o generics do java, mas aqui se chamma bound

  /*
   * type bounds baixos
   */

  // isso funciona, mas eu quero ela como contravariante em T.
  //  class Node[T](x: T) { def sub(v: T): Node[T] = new Node(v) }

  //  nao funciona pq a covariante T aparece na posicao de contravariante o tipo T em v.
  //  class Node[+T](x: T) { def sub(v: T): Node[T] = new Node(v) }

  // usando nossa classe anterior:
  class Node[B](x: B) { def sub(v: B): Node[B] = new Node(v) }

  // vai funcionar, mas nossa classe NodeA nao vai ser um subtipo de Node[B], mesmo que B estenda A.
  class NodeA[A](x: A) { def sub(v: A): Node[A] = new Node(v) }
  // isso acontece porque o A não pode ser substituido por B no argumento do metodo "sub".
  // Aqui, podemos usar lower bounding !

  class NodeLower[+T](x: T) { def sub[U >: T](v: U): NodeLower[U] = new NodeLower(v) }
  //  agora, podemos fazer: 
  (new NodeLower(new B)).sub(new B) //retorna um Node[B]
  ((new NodeLower(new B)).sub(new B)).sub(new A) // retorna um Node[A]
  ((new NodeLower(new B)).sub(new B)).asInstanceOf[NodeLower[C]] // retorna um Node[C]
  (((new NodeLower(new B)).sub(new B)).sub(new A)).sub(new C) // retonar um Node[A]

  /*
   * QUANTIFICATION
   * 
   * porque as vezes voce não dá uma foda pra dar nome a uma variavel!
   */
  // a funcao:
  def count[A](l: List[A]) = l.size
  // poderia ser reescrita utilizando wildcards, assim:
  def count_(l: List[_]) = l.size
  // que nada mais é que um atalho para:
  def count__(l: List[T forSome { type T }]) = l.size

  //  mais um exemplo:
  def drop1(l: List[_]) = l.tail
  // porem, dessa forma perdemos a informação de Tipo.
  // a funcao anterior, vai ser criada assim: drop1: (List[_])List[Any]
  //   agora esta:
  def drop1_(l: List[T forSome { type T }]) = l.tail
  // seria criada assim: (List[T forSome { type T }])List[T forSome { type T }]
  // na versao anterior, não temos como saber nada sobre o T, porque o tipo não permite isso!

  // MAS, podemos aplicar bounds aos wildcards!!!:
  def hashcodes(l: Seq[_ <: AnyRef]) = l map (_.hashCode)
  // que seria criada assim: (Seq[_ <: AnyRef])Seq[Int] /// NAO ENTENDI O PQ DO Int :/, acho que é por causa do Map hmm

  // enfim, isso vai me proibir de executar hashcodes(Seq(1,2,3)), pq o tipo primitivo Int
  //   nao é implicitamente convertido pra AnyRef. já:
  hashcodes(Seq("um", "dois", "tres"))
  //  funciona normalmente!

  def main(args: Array[String]) {
    println(t)
    println(drop[Any](t))

    println(fn.go)

    callFoo(Seq(new E, new F))
  }
}