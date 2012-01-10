val numbers = List(1,2,3,4,5,6)
val numbers2 = Set(1,2,3)

println(numbers)
println(numbers2)


val hostPort = ("localhost", 8080)
println(hostPort._1)
println(hostPort._2)

println(1 -> 2)

val numbers_ = Map(1 -> "one", 2 -> "two")
println(numbers_.get(2)) // vai retornar um "Option"

println(numbers_.getOrElse(0, 1)) //se nao existir nada na key "0" do numbers_, vai retornar o valor 1 :) poupa um if / else

val numbers_a = List(1, 2,3,4,5,6,7,9,10)
println(numbers_a.map((i: Int) => i * 2 )) //multiplica todos os itens por 2
println(numbers_a) //imprime o array original, porque ele é imutavel

def timesTwo(i: Int): Int = i * 2
println(numbers_a.map(timesTwo _))

