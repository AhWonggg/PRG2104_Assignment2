import com.github.tototoshi.csv._
import java.io.File

object CountryDataLoader {
  def loadData(filePath: String): Seq[CountryRecord] = {
    val reader = CSVReader.open(new File(filePath))
    val data = reader.allWithHeaders()
    reader.close()

    data.map(row =>
      CountryRecord(
        row("year").toInt,
        row("country_code"),
        row("country_name"),
        row("region"),
        row("income_group"),
        row("currency_unit"),
        row("gdp_usd").toDouble,
        row("population").toLong,
        row("gdp_per_capita").toDouble,
        row("inflation_rate").toDouble,
        row("unemployment_rate").toDouble,
        row("fdi_pct_gdp").toDouble,
        row.get("co2_emissions_kt").flatMap(value => Option(value).map(_.toDouble)),
        row("energy_use_per_capita").toDouble,
        row("renewable_energy_pct").toDouble,
        row("forest_area_pct").toDouble,
        row("electricity_access_pct").toDouble,
        row("life_expectancy").toDouble,
        row("child_mortality").toDouble,
        row("school_enrollment_secondary").toDouble,
        row("health_expenditure_pct_gdp").toDouble,
        row("hospital_beds_per_1000").toDouble,
        row("physicians_per_1000").toDouble,
        row("internet_usage_pct").toDouble,
        row("mobile_subscriptions_per_100").toDouble,
        row("calculated_gdp_per_capita").toDouble,
        row("real_economic_growth_indicator").toDouble,
        row("econ_opportunity_index").toDouble,
        row("co2_emissions_per_capita_tons").toDouble,
        row("co2_intensity_per_million_gdp").toDouble,
        row("green_transition_score").toDouble,
        row("ecological_preservation_index").toDouble,
        row("renewable_energy_efficiency").toDouble,
        row("human_development_composite").toDouble,
        row("healthcare_capacity_index").toDouble,
        row("digital_connectivity_index").toDouble,
        row("health_development_ratio").toDouble,
        row("education_health_ratio").toDouble,
        row("years_since_2000").toInt,
        row("years_since_century").toInt,
        row("is_pandemic_period").toBoolean,
        row.get("human_development_index").flatMap(value => Option(value).map(_.toDouble)),
        row.get("climate_vulnerability_index").flatMap(value => Option(value).map(_.toDouble)),
        row("digital_readiness_score").toDouble,
        row("governance_quality_index").toDouble,
        row("global_resilience_score").toDouble,
        row("global_development_resilience_index").toDouble
      )
    )
  }
}
