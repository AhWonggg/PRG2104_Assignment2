import scalafx.application.JFXApp3
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.layout._
import scalafx.scene.paint._
import scalafx.scene.control._
import scalafx.scene.text._
import scalafx.geometry._
import scalafx.animation._
import scalafx.beans.property.DoubleProperty
import scalafx.Includes._

/**
 * ScalaFX版美丽现代GUI
 */
object DevelopmentGUIApp extends JFXApp3 {
  override def start(): Unit = {
    val analysis = loadAnalysisData()
    stage = new JFXApp3.PrimaryStage {
      title = "🌍 Global Development Analytics - Beautiful ScalaFX"
      width = 1100
      height = 750
      scene = new Scene {
        fill = LinearGradient(0, 0, 1, 1, true, CycleMethod.NoCycle,
          Stop(0, Color.web("#e0eafc")), Stop(1, Color.web("#cfdef3")))
        root = new BorderPane {
          padding = Insets(30)
          top = createHeader()
          center = new ScrollPane {
            content = createContent(analysis)
            fitToWidth = true
            style = "-fx-background: transparent;"
          }
          bottom = createFooter()
        }
      }
    }
  }

  def loadAnalysisData(): Analysis = {
    val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
    val data = Loader.loadData(inputCsv)
    new Analysis(data)
  }

  def createHeader(): VBox = {
    val title = new Label("🌍 Global Development Analytics") {
      font = Font.font("Segoe UI", FontWeight.Bold, 38)
      textFill = Color.web("#1e293b")
      alignmentInParent = Pos.Center
    }
    val subtitle = new Label("Beautiful Insights into World Development Indicators") {
      font = Font.font("Segoe UI", FontWeight.Normal, 18)
      textFill = Color.web("#64748b")
      alignmentInParent = Pos.Center
    }
    val deco = new Region {
      minHeight = 18
      maxHeight = 18
      style = "-fx-background-radius: 9; -fx-background-color: linear-gradient(to right, #4a90e2, #a855f7, #ec4899);"
    }
    new VBox(10, title, subtitle, deco) {
      alignment = Pos.Center
      padding = Insets(0, 0, 30, 0)
    }
  }

  def createContent(analysis: Analysis): VBox = {
    val statusCard = createStatusCard(analysis)
    val questions = List(
      ("🏥", "Life Expectancy Excellence", "Which country achieved peak longevity?", getLifeExpectancyAnswer(analysis), "#4a90e2", "#74b9ff"),
      ("📚", "Health & Education Mastery", "Which nation leads in comprehensive human development?", getHealthEducationAnswer(analysis), "#48bb78", "#81ecec"),
      ("🌳", "Forest Conservation Challenge", "Which country faced the greatest environmental loss?", getForestLossAnswer(analysis), "#a855f7", "#ec4899")
    )
    val cards = questions.zipWithIndex.map { case ((icon, title, q, ans, c1, c2), idx) =>
      createQuestionCard(icon, title, q, ans, c1, c2, idx + 1)
    }
    new VBox(30, (statusCard +: cards): _*)
  }

  def createStatusCard(analysis: Analysis): VBox = {
    val isDataLoaded = analysis.records.nonEmpty
    val icon = if (isDataLoaded) "✅" else "⚠️"
    val text = if (isDataLoaded)
      s"Successfully analyzed ${analysis.records.length} development records from 193 countries"
    else
      "Data loading failed - Please verify your data source configuration"
    val color = if (isDataLoaded) "#22c55e" else "#ef4444"
    new VBox {
      style = s"-fx-background-radius: 18; -fx-background-color: rgba(255,255,255,0.85); -fx-effect: dropshadow(gaussian, #a0aec0, 12, 0.2, 0, 2);"
      padding = Insets(25, 30, 25, 30)
      alignment = Pos.Center
      children = Seq(
        new Label(icon) {
          font = Font.font("Apple Color Emoji", FontWeight.Normal, 32)
        },
        new Label(text) {
          font = Font.font("Segoe UI", FontWeight.Normal, 16)
          textFill = Color.web(color)
        }
      )
    }
  }

  def createQuestionCard(icon: String, title: String, question: String, answer: String, color1: String, color2: String, number: Int): HBox = {
    val numberCircle = new Label(number.toString) {
      minWidth = 48
      minHeight = 48
      alignment = Pos.Center
      font = Font.font("Segoe UI", FontWeight.Bold, 20)
      textFill = Color.White
      style = s"-fx-background-radius: 24; -fx-background-color: linear-gradient(to bottom right, $color1, $color2);"
    }
    val iconLabel = new Label(icon) {
      font = Font.font("Apple Color Emoji", FontWeight.Normal, 36)
      minWidth = 48
      alignment = Pos.Center
    }
    val titleLabel = new Label(title) {
      font = Font.font("Segoe UI", FontWeight.Bold, 20)
      textFill = Color.web(color1)
    }
    val questionLabel = new Label(question) {
      font = Font.font("Segoe UI", FontWeight.Normal, 15)
      textFill = Color.web("#64748b")
    }
    val answerLabel = new Label(answer) {
      font = Font.font("Segoe UI", FontWeight.Bold, 16)
      textFill = Color.web(color1)
      style = s"-fx-background-radius: 10; -fx-background-color: linear-gradient(to right, $color1, $color2, #fff2); -fx-padding: 8 16 8 16;"
    }
    val vbox = new VBox(8, titleLabel, questionLabel, answerLabel) {
      alignment = Pos.TopLeft
      padding = Insets(0, 0, 0, 10)
    }
    new HBox(20, numberCircle, iconLabel, vbox) {
      style = "-fx-background-radius: 18; -fx-background-color: rgba(255,255,255,0.92); -fx-effect: dropshadow(gaussian, #a0aec0, 10, 0.18, 0, 2);"
      padding = Insets(30)
      alignment = Pos.CenterLeft
      maxWidth = 900
    }
  }

  def createFooter(): VBox = {
    new VBox {
      alignment = Pos.Center
      padding = Insets(20, 0, 0, 0)
      children = Seq(
        new Label("✨ Beautiful Analysis Dashboard - Powered by ScalaFX ✨") {
          font = Font.font("Segoe UI", FontPosture.Italic, 14)
          textFill = Color.web("#94a3b8")
        }
      )
    }
  }

  // Answer methods
  def getLifeExpectancyAnswer(analysis: Analysis): String = {
    analysis.highestLifeExpectancy match {
      case Some((country, year)) => s"$country achieved peak longevity in $year"
      case None => "Data analysis in progress..."
    }
  }
  def getHealthEducationAnswer(analysis: Analysis): String = {
    analysis.topHealthAndEducationCountry match {
      case Some(country) => s"$country excels in comprehensive human development"
      case None => "Data analysis in progress..."
    }
  }
  def getForestLossAnswer(analysis: Analysis): String = {
    analysis.highestForestLoss match {
      case Some((country, loss)) => f"$country faced $loss%.2f%% forest area reduction"
      case None => "Data analysis in progress..."
    }
  }
}
