import javafx.scene.control.Label
import javafx.scene.layout.VBox

class DevelopmentUI(analysis: Analysis) {

  def generateUI(): VBox = {
    val vbox = new VBox(10)

    val q1Label = new Label("1) Which country had the highest life expectancy, and in which year?")
    val q1Answer = analysis.highestLifeExpectancy match {
      case Some((country, year)) => new Label(s"Answer: $country in $year")
      case None => new Label("Answer: No data found.")
    }

    val q2Label = new Label("2) Which country performed best in health & education?")
    val q2Answer = analysis.topHealthAndEducationCountry match {
      case Some(country) => new Label(s"Answer: $country")
      case None => new Label("Answer: No valid data.")
    }

    val q3Label = new Label("3) Which country had the highest forest area loss from 2000 to 2020?")
    val q3Answer = analysis.highestForestLoss match {
      case Some((country, loss)) => new Label(f"Answer: $country lost $loss%.2f%% forest area")
      case None => new Label("Answer: No sufficient data.")
    }

    val q0Label = if (analysis.records.isEmpty)
      new Label("Warning: No data loaded. Please check your CSV file.")
    else
      new Label("Data loaded successfully.")

    vbox.getChildren.addAll(q0Label, q1Label, q1Answer, q2Label, q2Answer, q3Label, q3Answer)
    vbox
  }
}
