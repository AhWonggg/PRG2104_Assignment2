error id: file:///C:/Users/wong/IdeaProjects/test/OOP-Kacang-Putih/assignment-2-group-kacang-putih/src/main/scala/MainApp.scala:`<none>`.
file:///C:/Users/wong/IdeaProjects/test/OOP-Kacang-Putih/assignment-2-group-kacang-putih/src/main/scala/MainApp.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 46
uri: file:///C:/Users/wong/IdeaProjects/test/OOP-Kacang-Putih/assignment-2-group-kacang-putih/src/main/scala/MainApp.scala
text:
```scala

object MainApp extends App {
  // Define fi@@le paths directly within the code
  val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
  val outputTxt = "src/main/resources/output.txt"

  // Create an instance of HeaderReader and call the method to extract the header
  val headerReader = new HeaderReader(inputCsv, outputTxt)
  headerReader.extractHeader()
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.