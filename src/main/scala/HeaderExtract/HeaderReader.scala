import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}
import java.io.File

object HeaderReader {
  
  // Function to extract headers from CSV file
  def extractHeader(inputCsv: String, outputTxt: String): Unit = {
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
          writer.write(header.trim)
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

  // Function to get headers as a list
  def getHeaders(inputCsv: String): List[String] = {
    val input = new File(inputCsv)
    if (!input.exists()) {
      return List.empty
    }

    try {
      val reader = new BufferedReader(new FileReader(input))
      val line = reader.readLine()
      reader.close()
      
      if (line != null) {
        line.split(",").map(_.trim).toList
      } else {
        List.empty
      }
    } catch {
      case e: Exception => 
        println(s"Error reading headers: ${e.getMessage}")
        List.empty
    }
  }
}


