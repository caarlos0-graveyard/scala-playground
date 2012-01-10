object pattern_matching_and_functional_composition {

  // duas funcoes que vao ser utilizadas ao long do codigo
  def addBacon(x: String): String = x + "_bacon"
  def addChedar(x: String): String = x + "_chedar"

  // faz um compose dos dois metodos convertendo-as para funcoes 
  val addChedarEBacon = addBacon _ compose addChedar _
  val addBaconEChedar = addBacon _ andThen addChedar _

  /*
   * funcoes parciais
   */
  val one: PartialFunction[Int, String] = { case 1 => "one" }
  val two: PartialFunction[Int, String] = { case 2 => "two" }
  val three: PartialFunction[Int, String] = { case 3 => "three" }
  val wildcard: PartialFunction[Int, String] = { case _ => "something else" }
  val ottw = one orElse two orElse three orElse wildcard // junto todas as partials em outra partial

  /*
   * the mistery of case
   */
  case class PhoneExt(name: String, ext: Int)
  val extensions = List(PhoneExt("steve", 100), PhoneExt("robey", 200))

  def main(args: Array[String]): Unit = {
    println(addBacon("chedar"))
    println(addChedar("bacon"))
    println(addChedarEBacon("queijo")) // imprime queijo_chedar_bacon, pq o compose chama primeiro a 2 funcao
    println(addBaconEChedar("queijo")) // imprime queijo_bacon_chedar, pq o addThen chama primeiro a 1 funcao

    println(one.isDefinedAt(1)) //true
    println(one.isDefinedAt(2)) //false

    println(ottw(10)) // something else
    println(ottw(3)) // three

    /*
     * Isso funciona pq filter espera uma function.
     * Nesse caso, uma predicate funcion de (PhoneExt) => Boolean.
     * Uma funcao parcial é um subtipo de function, entao o filter pode pegar uma partial function tambem!
     */
    println(extensions.filter { case PhoneExt(name, extension) => extension < 200 })
  }

}