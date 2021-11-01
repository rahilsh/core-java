def expensiveComputation(input: Int) = {
  println("called expensive computation")
  input
}

val somevalue = 4

println("Short circuit")
if(somevalue > 5 && expensiveComputation(5) > 0)
  println("")
else
  println("")
  
println("Not so efficient")
val temp = expensiveComputation(5)
if(somevalue > 5 && temp > 0)
  println("")
else
  println("")

println("But we can be quite easily...")
lazy val temp2 = expensiveComputation(5)
if(somevalue > 5 && temp2 > 0)
  println("")
else
  println("")
