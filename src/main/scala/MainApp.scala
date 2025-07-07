object MainApp extends App {
  val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
  val outputTxt = "src/main/resources/output.txt"
  
  // Extract headers first
  HeaderReader.extractHeader(inputCsv, outputTxt)
  
  // Load data and perform analysis
  val data = Loader.loadData(inputCsv)
  val analysis = new Analysis(data)

  println("Global Development Data Analysis Results:")
  println("==========================================\n")

  analysis.highestLifeExpectancy match {
    case Some((country, year)) => println(s"1) Country with highest life expectancy: $country in $year")
    case None => println("1) No data for life expectancy.")
  }

  analysis.topHealthAndEducationCountry match {
    case Some(country) => println(s"2) Country with best health & education: $country")
    case None => println("2) No valid data for health & education.")
  }

  analysis.highestForestLoss match {
    case Some((country, loss)) => println(f"3) Country with highest forest loss: $country lost $loss%.2f%%")
    case None => println("3) No sufficient data for forest loss.")
  }
  
  println("\n==========================================")
  println("Analysis completed successfully!")
  
  // Launch beautiful GUI after console output
  println("\nLaunching Beautiful Modern GUI interface...")

  DevelopmentGUI.main(Array.empty)
}
