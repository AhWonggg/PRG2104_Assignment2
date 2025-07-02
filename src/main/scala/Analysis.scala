


/*class Analysis(val records: Seq[CountryData]) {

  //Question 1
  def highestLifeExpectancy: Option[(String, Int)] = {
    records.maxByOption(_.life_expectancy).map(r => (r.country_name, r.year))
  }

  //Question 2
  def topHealthAndEducationCountry: Option[String] = {
    // Group records by country
    val grouped = records.groupBy(_.country_name)

    // Calculate averages for each relevant indicator
    val averages: Map[String, (Double, Double, Double, Double, Double)] = grouped.flatMap {
      case (country, recs) =>
        val life = recs.map(_.life_expectancy)
        val mortality = recs.map(_.child_mortality)
        val school = recs.map(_.school_enrollment_secondary)
        val capacity = recs.map(_.healthcare_capacity_index)
        val dev = recs.map(_.health_development_ratio)

        // Only include countries with complete data (no empty lists)
        if (life.nonEmpty && mortality.nonEmpty && school.nonEmpty && capacity.nonEmpty && dev.nonEmpty) {
          Some(country -> (
            life.sum / life.size,
            mortality.sum / mortality.size,
            school.sum / school.size,
            capacity.sum / capacity.size,
            dev.sum / dev.size
          ))
        } else None
    }.toMap

    // Extract indicator values and normalize
    val avgLife = averages.collect { case (c, (l, _, _, _, _)) if l > 0 => (c, l) }.toSeq
    val avgMortality = averages.collect { case (c, (_, m, _, _, _)) if m > 0 => (c, m) }.toSeq
    val avgSchool = averages.collect { case (c, (_, _, s, _, _)) if s > 0 => (c, s) }.toSeq
    val avgCapacity = averages.collect { case (c, (_, _, _, cap, _)) if cap > 0 => (c, cap) }.toSeq
    val avgDev = averages.collect { case (c, (_, _, _, _, d)) if d > 0 => (c, d) }.toSeq

    val normLife = normalize(avgLife, higherIsBetter = true)
    val normMortality = normalize(avgMortality, higherIsBetter = false)
    val normSchool = normalize(avgSchool, higherIsBetter = true)
    val normCapacity = normalize(avgCapacity, higherIsBetter = true)
    val normDev = normalize(avgDev, higherIsBetter = true)

    // Compute overall score for each country
    val scores = averages.keys.map { country =>
      val score = (
        normLife.getOrElse(country, 0.0) +
          normMortality.getOrElse(country, 0.0) +
          normSchool.getOrElse(country, 0.0) +
          normCapacity.getOrElse(country, 0.0) +
          normDev.getOrElse(country, 0.0)
        ) / 5.0
      country -> score
    }

    // Return country with highest score
    scores.maxByOption(_._2).map(_._1)
  }

  def highestForestLoss: Option[(String, Double)] = {
    val grouped = records.groupBy(_.country_name)

    val losses = grouped.flatMap { case (country, recs) =>
      val year2000 = recs.find(_.year == 2000).map(_.forest_area_pct)
      val year2020 = recs.find(_.year == 2020).map(_.forest_area_pct)
      for (a2000 <- year2000; a2020 <- year2020) yield country -> (a2000 - a2020)
    }

    losses.toSeq.sortBy(-_._2).headOption
  }

  private def normalize(data: Seq[(String, Double)], higherIsBetter: Boolean): Map[String, Double] = {
    if (data.isEmpty) return Map.empty
    val values = data.map(_._2)
    val min = values.min
    val max = values.max
    val range = max - min

    data.map { case (c, v) =>
      val score =
        if (range == 0) 100.0
        else if (higherIsBetter) (v - min) / range * 100
        else (max - v) / range * 100
      c -> score
    }.toMap
  }
}
*/
class Analysis(val records: Seq[CountryData]) {

  def run(): Unit = {
    println("===================================")
    println("Question 1: Highest Life Expectancy")
    println("===================================")
    println(highestLifeExpectancy match {
      case Some((country, year)) => s"Answer: $country in $year"
      case None => "Answer: No data available."
    })

    println("\n===================================")
    println("Question 2: Best Health & Education")
    println("===================================")
    println(topHealthAndEducationCountry match {
      case Some(country) => s"Answer: $country"
      case None => "Answer: No valid country found."
    })

    println("\n===================================")
    println("Question 3: Forest Loss (2000–2020)")
    println("===================================")
    println(highestForestLoss match {
      case Some((country, loss)) => f"Answer: $country lost $loss%.2f%% of forest area between 2000 and 2020"
      case None => "Answer: No valid forest area data for 2000 and 2020."
    })
  }

  def highestLifeExpectancy: Option[(String, Int)] = {
    records.maxByOption(_.life_expectancy).map(r => (r.country_name, r.year))
  }

  def topHealthAndEducationCountry: Option[String] = {
    val grouped = records.groupBy(_.country_name)

    val averages: Map[String, (Double, Double, Double, Double, Double)] = grouped.flatMap {
      case (country, recs) =>
        val life = recs.map(_.life_expectancy)
        val mortality = recs.map(_.child_mortality)
        val school = recs.map(_.school_enrollment_secondary)
        val capacity = recs.map(_.healthcare_capacity_index)
        val dev = recs.map(_.health_development_ratio)

        if (life.nonEmpty && mortality.nonEmpty && school.nonEmpty && capacity.nonEmpty && dev.nonEmpty) {
          Some(country -> (
            life.sum / life.size,
            mortality.sum / mortality.size,
            school.sum / school.size,
            capacity.sum / capacity.size,
            dev.sum / dev.size
          ))
        } else None
    }.toMap

    val avgLife = averages.collect { case (c, (l, _, _, _, _)) if l > 0 => (c, l) }.toSeq
    val avgMortality = averages.collect { case (c, (_, m, _, _, _)) if m > 0 => (c, m) }.toSeq
    val avgSchool = averages.collect { case (c, (_, _, s, _, _)) if s > 0 => (c, s) }.toSeq
    val avgCapacity = averages.collect { case (c, (_, _, _, cap, _)) if cap > 0 => (c, cap) }.toSeq
    val avgDev = averages.collect { case (c, (_, _, _, _, d)) if d > 0 => (c, d) }.toSeq

    val normLife = normalize(avgLife, higherIsBetter = true)
    val normMortality = normalize(avgMortality, higherIsBetter = false)
    val normSchool = normalize(avgSchool, higherIsBetter = true)
    val normCapacity = normalize(avgCapacity, higherIsBetter = true)
    val normDev = normalize(avgDev, higherIsBetter = true)

    val scores = averages.keys.map { country =>
      val score = (
        normLife.getOrElse(country, 0.0) +
          normMortality.getOrElse(country, 0.0) +
          normSchool.getOrElse(country, 0.0) +
          normCapacity.getOrElse(country, 0.0) +
          normDev.getOrElse(country, 0.0)
        ) / 5.0
      country -> score
    }

    scores.maxByOption(_._2).map(_._1)
  }

  def highestForestLoss: Option[(String, Double)] = {
    val grouped = records.groupBy(_.country_name)

    val losses = grouped.flatMap { case (country, recs) =>
      val year2000 = recs.find(_.year == 2000).map(_.forest_area_pct)
      val year2020 = recs.find(_.year == 2020).map(_.forest_area_pct)
      for (a2000 <- year2000; a2020 <- year2020) yield country -> (a2000 - a2020)
    }

    losses.toSeq.sortBy(-_._2).headOption
  }

  private def normalize(data: Seq[(String, Double)], higherIsBetter: Boolean): Map[String, Double] = {
    if (data.isEmpty) return Map.empty
    val values = data.map(_._2)
    val min = values.min
    val max = values.max
    val range = max - min

    data.map { case (c, v) =>
      val score =
        if (range == 0) 100.0
        else if (higherIsBetter) (v - min) / range * 100
        else (max - v) / range * 100
      c -> score
    }.toMap
  }
}

