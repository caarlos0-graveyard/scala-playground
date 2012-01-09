println(1+2)

var nome = "carlos"
println(nome)

// cria uma funcao
def addOne(m : Int): Int = m + 1

println(addOne(2))

def three() = 1+2

println(three())

//### Funcoes anonimas
val addOne2 = (x: Int) => x+1
println(addOne2(1))

def timesTwo(i : Int): Int = {
    println("hello world")
    i*2
}

timesTwo(2)

{ 
    i: Int => 
    println("hello world")
    i*2    
}

def adder(m: Int, n: Int) = m+n
println(adder(20,12))

val add2 = adder(2, _:Int)
println(add2(10))


// curried functions
// permite que você passe os parametros em tempos diferentes...

def multiply(m: Int)(n: Int): Int = m*n
println(multiply(2)(3))

val timesTwo2 = multiply(2)(_)
println(timesTwo2(10))


// nao entendi essa merda ainda.
//multiplyThenFilter { m: Int =>
 //   m * 2
//} { n: Int =>
 //   n < 5
//}

//println(multiplyThenFilter(10)(2))

// faz curry em em uma funcao :D
(adder(_,_)).curried

println(adder(3, 10))

def capitalizeAll(args: String*)= {
    args.map { arg =>
        println(arg.capitalize)
    }
}

capitalizeAll("carlos", "raduq", "luan")



// CLASSES

class Calculator(brand: String) {
    val color: String = if (brand == "TI"){
        "blue"
    } else if(brand == "HP"){
        "black"
    } else {
        "white"
    }

    def add(m: Int, n: Int): Int = m + n
}

val calc = new Calculator("TI")
var ret = calc.add(1, 4)
println(ret)
println(calc.color)
//println(calc.brand)


class ScientificCalculator(brand: String) extends Calculator(brand)
{
    def log(m: Double, base: Double) = math.log(m) / math.log(base)
}

val scalc = new ScientificCalculator("HP")
println(scalc.log(2, 10))


trait Car {
    val brand: String
}

class BMW extends Car {
    val brand = "BWM"
}

val car: Car = new BMW
println(car.brand)

trait Cache[K, V]
{
    def getKey(key: K): V
    def put(key: K, value: V)
    def delete(key: K)
    def remove[K](key: K)
}

class MyCache extends Cache[Int, String]
{


    def getKey(key: Int): String = "oi"
    def put(key: Int, value: String) = println("oi")
    def delete(key: Int) = println("oi")
    def remove[Int](key: Int) = println("oi")   
}

println(new MyCache)