import scala.io.Source

object Loader {
  private var cachedData: Option[Seq[CountryData]] = None

  def loadData(filePath: String): Seq[CountryData] = {
    if (cachedData.isEmpty) {
      val source = Source.fromFile(filePath)
      val lines = source.getLines().drop(1).toSeq

      val data = lines.flatMap { line =>
        val cols = parseCsvLine(line)

        if (cols.length >= 47) {
          try {
            // Helper function to safely parse double
            def safeParseDouble(str: String): Double = {
              if (str.isEmpty || str.equalsIgnoreCase("NA")) 0.0 else str.toDouble
            }
            
            // Helper function to safely parse boolean from 0/1
            def safeParseBoolean(str: String): Boolean = {
              if (str.isEmpty || str.equalsIgnoreCase("NA")) false 
              else str == "1" || str.equalsIgnoreCase("true")
            }

            Some(CountryRecord(
              year = cols(0).toInt,
              country_code = cols(1),
              country_name = cols(2),
              region = cols(3),
              income_group = cols(4),
              currency_unit = cols(5),
              gdp_usd = safeParseDouble(cols(6)),
              population = safeParseDouble(cols(7)),
              gdp_per_capita = safeParseDouble(cols(8)),
              inflation_rate = safeParseDouble(cols(9)),
              unemployment_rate = safeParseDouble(cols(10)),
              fdi_pct_gdp = safeParseDouble(cols(11)),
              co2_emissions_kt = parseOptDouble(cols(12)),
              energy_use_per_capita = safeParseDouble(cols(13)),
              renewable_energy_pct = safeParseDouble(cols(14)),
              forest_area_pct = safeParseDouble(cols(15)),
              electricity_access_pct = safeParseDouble(cols(16)),
              life_expectancy = safeParseDouble(cols(17)),
              child_mortality = safeParseDouble(cols(18)),
              school_enrollment_secondary = safeParseDouble(cols(19)),
              health_expenditure_pct_gdp = safeParseDouble(cols(20)),
              hospital_beds_per_1000 = safeParseDouble(cols(21)),
              physicians_per_1000 = safeParseDouble(cols(22)),
              internet_usage_pct = safeParseDouble(cols(23)),
              mobile_subscriptions_per_100 = safeParseDouble(cols(24)),
              calculated_gdp_per_capita = safeParseDouble(cols(25)),
              real_economic_growth_indicator = safeParseDouble(cols(26)),
              econ_opportunity_index = safeParseDouble(cols(27)),
              co2_emissions_per_capita_tons = safeParseDouble(cols(28)),
              co2_intensity_per_million_gdp = safeParseDouble(cols(29)),
              green_transition_score = safeParseDouble(cols(30)),
              ecological_preservation_index = safeParseDouble(cols(31)),
              renewable_energy_efficiency = safeParseDouble(cols(32)),
              human_development_composite = safeParseDouble(cols(33)),
              healthcare_capacity_index = safeParseDouble(cols(34)),
              digital_connectivity_index = safeParseDouble(cols(35)),
              health_development_ratio = safeParseDouble(cols(36)),
              education_health_ratio = safeParseDouble(cols(37)),
              years_since_2000 = cols(38).toInt,
              years_since_century = cols(39).toInt,
              is_pandemic_period = safeParseBoolean(cols(40)),
              human_development_index = parseOptDouble(cols(41)),
              climate_vulnerability_index = parseOptDouble(cols(42)),
              digital_readiness_score = safeParseDouble(cols(43)),
              governance_quality_index = safeParseDouble(cols(44)),
              global_resilience_score = safeParseDouble(cols(45)),
              global_development_resilience_index = safeParseDouble(cols(46))
            ))
          } catch {
            case e: Exception =>
              println(s"Skipping malformed row due to error: ${e.getMessage}")
              None
          }
        } else {
          println(s"Skipping invalid row with ${cols.length} columns (expected 47): ${line.take(100)}...")
          None
        }
      }

      source.close()
      cachedData = Some(data)
    }

    cachedData.get
  }

  private def parseOptDouble(str: String): Option[Double] = {
    if (str.isEmpty || str.equalsIgnoreCase("NA")) None else Some(str.toDouble)
  }

  // Parse CSV line correctly handling quoted fields with commas
  private def parseCsvLine(line: String): Array[String] = {
    val result = scala.collection.mutable.ArrayBuffer[String]()
    var current = new StringBuilder()
    var inQuotes = false
    var i = 0
    
    while (i < line.length) {
      val char = line.charAt(i)
      char match {
        case '"' => 
          inQuotes = !inQuotes
        case ',' if !inQuotes =>
          result += current.toString.trim
          current.clear()
        case _ =>
          current += char
      }
      i += 1
    }
    result += current.toString.trim
    result.toArray
  }
}
