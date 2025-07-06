import scalafx.scene.control.{Label, Button, TextArea}
import scalafx.scene.layout.{VBox, HBox, BorderPane}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.text.{Font, FontWeight}

class DevelopmentUI(analysis: Analysis) {

  def generateUI(): BorderPane = {
    val borderPane = new BorderPane()
    
    // Title section
    val titleLabel = new Label("Global Development Data Analysis") {
      font = Font.font("Arial", FontWeight.Bold, 24)
      style = "-fx-text-fill: #2c3e50;"
    }
    
    val titleBox = new VBox(titleLabel) {
      alignment = Pos.Center
      padding = Insets(20)
      style = "-fx-background-color: #ecf0f1;"
    }
    
    // Results section
    val resultsBox = new VBox() {
      spacing = 15
      padding = Insets(20)
      alignment = Pos.TopLeft
    }
    
    // Data status
    val dataStatusLabel = if (analysis.records.isEmpty) {
      new Label("⚠️ Warning: No data loaded. Please check your CSV file.") {
        style = "-fx-text-fill: #e74c3c; -fx-font-size: 14px;"
      }
    } else {
      new Label(s"✅ Data loaded successfully! Analyzing ${analysis.records.length} records.") {
        style = "-fx-text-fill: #27ae60; -fx-font-size: 14px;"
      }
    }
    
    // Question 1
    val q1Box = createQuestionBox(
      "1. Which country had the highest life expectancy, and in which year?",
      analysis.highestLifeExpectancy match {
        case Some((country, year)) => s"$country achieved the highest life expectancy in $year"
        case None => "No data available for life expectancy analysis"
      }
    )
    
    // Question 2
    val q2Box = createQuestionBox(
      "2. Which country performed best in health & education?",
      analysis.topHealthAndEducationCountry match {
        case Some(country) => s"$country performed best in health & education across all indicators"
        case None => "No valid data available for health & education analysis"
      }
    )
    
    // Question 3
    val q3Box = createQuestionBox(
      "3. Which country had the highest forest area loss from 2000 to 2020?",
      analysis.highestForestLoss match {
        case Some((country, loss)) => f"$country had the highest forest loss with $loss%.2f%% reduction"
        case None => "No sufficient data available for forest loss analysis"
      }
    )
    
    resultsBox.children.addAll(dataStatusLabel, q1Box, q2Box, q3Box)
    
    // Footer
    val footerLabel = new Label("Analysis completed successfully!") {
      style = "-fx-text-fill: #7f8c8d; -fx-font-size: 12px;"
    }
    val footerBox = new VBox(footerLabel) {
      alignment = Pos.Center
      padding = Insets(10)
    }
    
    // Assemble the layout
    borderPane.top = titleBox
    borderPane.center = resultsBox
    borderPane.bottom = footerBox
    borderPane.style = "-fx-background-color: white;"
    
    borderPane
  }
  
  private def createQuestionBox(question: String, answer: String): VBox = {
    val questionLabel = new Label(question) {
      font = Font.font("Arial", FontWeight.Bold, 16)
      style = "-fx-text-fill: #34495e;"
      wrapText = true
    }
    
    val answerLabel = new Label(answer) {
      font = Font.font("Arial", 14)
      style = "-fx-text-fill: #2980b9; -fx-padding: 10 0 0 20;"
      wrapText = true
    }
    
    val box = new VBox(5, questionLabel, answerLabel) {
      style = "-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;"
      padding = Insets(15)
    }
    
    box
  }
}
