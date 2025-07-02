object MainApp extends App {
  val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
  // Load data using Loader class
  val data = Loader.loadData(inputCsv)
  val analysis = new Analysis(data)

  println("Global Development Data Analysis Results:\n")

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
}
