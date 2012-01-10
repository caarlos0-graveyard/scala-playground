object collections {
  def main(args: Array[String]) {
    val numbers = List(1, 2, 3, 4, 5, 6)
    val numbers2 = Set(1, 2, 3)

    println(numbers)
    println(numbers2)

    val hostPort = ("localhost", 8080)
    println(hostPort._1)
    println(hostPort._2)

    println(1 -> 2)

    val numbers_ = Map(1 -> "one", 2 -> "two")
    println(numbers_.get(2)) // vai retornar um "Option"

    println(numbers_.getOrElse(0, 1)) //se nao existir nada na key "0" do numbers_, vai retornar o valor 1 :) poupa um if / else

    val numbers_a = List(1, 2, 3, 4, 5, 6, 7, 9, 10)
    println(numbers_a.map((i: Int) => i * 2)) //multiplica todos os itens por 2
    println(numbers_a) //imprime o array original, porque ele é imutavel

    def timesTwo(i: Int): Int = i * 2
    println(numbers_a.map(timesTwo _))

    val doubled = numbers_a.foreach((i: Int) => i * 2) //nao vai funcionar, foreach nao retorna nada.

    println(numbers_a.filter((i: Int) => i % 2 == 0)) //imprime somente os que obedecem a expressao
    println(numbers_a) // imprime o array original. imutavel, manolo!

    println(List(1, 2, 3).zip(List("a", "b", "c")))

    val tenNumbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    println(tenNumbers.partition((i: Int) => i > 2)) //vai dividir em dois arrays, um até o item 2 e outro com o restante

    // retorna o primeiro item que encontrar que respeite a expressao!
    println(tenNumbers.find((i: Int) => i > 2))

    // irá dropar os primeiros n elementos do array!
    println(tenNumbers.drop(5))

    // dropa enquanto a expressao for válida. se eu por > 6, nao funciona, pq já começa invalidado! é um while!
    println(tenNumbers.dropWhile((i: Int) => i < 6))

    // vai somando os itens do array, a soma dos dois anteriores com o proximo, a expressao final seria 10+9+8+7+etc...
    println(tenNumbers.foldLeft(0)((m: Int, n: Int) => m + n))

    // para ver melhor o que acontece
    var total = tenNumbers.foldLeft(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }
    println(total)

    // mesma coisa que o foldLeft, porem, de tras para frente!
    total = tenNumbers.foldRight(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }
    println(total)

    // deixa todos os arrays de um array em um nível só.
    println(List(List(1, 2, 3), List(5, 6, 7, 10)).flatten)

    val nestedNumbers = List(List(1, 2), List(3, 4))
    println(nestedNumbers.flatMap(x => x.map(_ * 2)))

    // defino um funcao onde eu recebo um lis e uma funcao que espera um Int como parametro e retorna um Int.
    def ourMap(numbers: List[Int], fn: Int => Int): List[Int] = {
      // pego a list e faço um foldRight. O "List[Int]()" informa ao compilador
      // que vou querer um array vazio de Ints.
      // passo para o fold o x da vez (que é um dos itens do array)
      // e XS, que é o estado atual do meu array (que começou List[Int]()).
      numbers.foldRight(List[Int]()) { (x: Int, xs: List[Int]) =>
      	// vai executar minha funcao, adicionando o resultado dela no array (xs)
        fn(x) :: xs
      }
    }
    
//    executo minha funcao, passando o array e a timesTwo (com curry?)
    println(ourMap(tenNumbers, timesTwo(_)))
    println(timesTwo(2) :: List[Int]()) // vai retornar List(4)

    /**
     * MAPs
     */
    
    val extensions = Map("carlos" -> 10, "luan" -> 11, "joao" -> 24)
    
    // imprime um map com todos os itens que respeitam o filtro (atributo 2 < 20)
    println(extensions.filter((n: (String, Int)) => n._2 < 20))
    
    // isso teoricamente era para funcionar, nao sei pq nao funciona.
    //extensions.filter(case (name, extension) => extension < 200)
  }
}