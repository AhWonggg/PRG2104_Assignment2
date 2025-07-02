import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}
import java.io.File

class HeaderReader(inputCsv: String, outputTxt: String) {

  // Method to read the header from the CSV file
  def extractHeader(): Unit = {
    val input = new File(inputCsv)
    if (!input.exists()) {
      println(s"Error: The file $inputCsv does not exist.")
      return
    }

    try {
      val reader = new BufferedReader(new FileReader(input))
      val writer = new BufferedWriter(new FileWriter(outputTxt))

      val line = reader.readLine()
      if (line != null) {
        line.split(",").foreach { header =>
          writer.write(header)
          writer.newLine()
        }
      }

      reader.close()
      writer.close()
      println(s"Headers have been successfully extracted to $outputTxt")

    } catch {
      case e: Exception => println(s"Error extracting header: ${e.getMessage}")
    }
  }

}


