object MainApp extends App {
  val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
  val outputTxt = "src/main/resources/output.txt"

  println("==========================================================")
  println("   GLOBAL DEVELOPMENT ANALYTICS - ENTERPRISE PLATFORM")
  println("==========================================================")
  println()

  // Extract headers first
  println("📄 Extracting data headers...")
  HeaderReader.extractHeader(inputCsv, outputTxt)
  println("✓ Headers extracted successfully")
  println()

  // Load data and perform analysis
  println("📊 Loading development data...")
  val data = Loader.loadData(inputCsv)
  val analysis = new Analysis(data)
  println(f"✓ Loaded ${data.length}%,d records successfully")
  println()

  println("🔍 EXECUTIVE ANALYSIS SUMMARY")
  println("==========================================================")
  println()

  // Question 1: Life Expectancy
  analysis.highestLifeExpectancy match {
    case Some((country, year)) =>
      println(s"🏥 HEALTH EXCELLENCE:")
      println(s"   → $country achieved peak life expectancy in $year")
      println(s"   → Global benchmark for healthcare quality")
    case None =>
      println("🏥 HEALTH EXCELLENCE:")
      println("   → Insufficient data for life expectancy analysis")
  }
  println()

  // Question 2: Health & Education
  analysis.topHealthAndEducationCountry match {
    case Some(country) =>
      println(s"🎓 HUMAN DEVELOPMENT LEADERSHIP:")
      println(s"   → $country excels in comprehensive development")
      println(s"   → Gold standard for health & education integration")
    case None =>
      println("🎓 HUMAN DEVELOPMENT LEADERSHIP:")
      println("   → Incomplete data for comprehensive analysis")
  }
  println()

  // Question 3: Forest Loss
  analysis.highestForestLoss match {
    case Some((country, loss)) =>
      println(s"🌳 ENVIRONMENTAL IMPACT:")
      println(f"   → $country: $loss%.2f%% forest area reduction (2000-2020)")
      println(s"   → Critical conservation challenges identified")
    case None =>
      println("🌳 ENVIRONMENTAL IMPACT:")
      println("   → Insufficient forest data for impact analysis")
  }
  println()

  println("==========================================================")
  println("✓ Console analysis completed successfully!")
  println("🚀 Launching Advanced Visual Intelligence Platform...")
  println("==========================================================")
  println()

  Thread.sleep(1500)

  DevelopmentGUI.launch()

  println("📱 GUI Interface launched successfully!")
  println()
}