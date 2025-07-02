
object MainApp extends App {
  // Define file paths directly within the code
  val inputCsv = "src//main//resources//Global_Development_Indicators_2000_2020.csv"
  val outputTxt = "src//main//resources//output.txt"

  // Create an instance of HeaderReader and call the method to extract the header
  val headerReader = new HeaderReader(inputCsv, outputTxt)
  headerReader.extractHeader()
}
