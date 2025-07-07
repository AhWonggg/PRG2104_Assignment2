import scalafx.Includes.*
import scalafx.animation.*
import scalafx.application.JFXApp3
import scalafx.geometry.*
import scalafx.scene.Scene
import scalafx.scene.control.*
import scalafx.scene.effect.*
import scalafx.scene.layout.*
import scalafx.scene.paint.*
import scalafx.scene.text.*
import scalafx.util.Duration

/**
 * Enhanced Professional ScalaFX GUI for Global Development Analytics
 * Features: Modern design, animations, professional styling, and improved UX
 */
object DevelopmentGUI extends JFXApp3 {

  // Color palette for professional design
  private val primaryBlue = "#2563eb"
  private val secondaryBlue = "#3b82f6"
  private val accentGreen = "#10b981"
  private val accentOrange = "#f59e0b"
  private val accentRed = "#ef4444"
  private val neutralGray = "#6b7280"
  private val lightGray = "#f8fafc"
  private val darkGray = "#1f2937"
  private val cardBackground = "rgba(255,255,255,0.95)"

  override def start(): Unit = {
    val analysis: Analysis = loadAnalysisData()

    stage = new JFXApp3.PrimaryStage {
      title = "Global Development Analytics - Professional Dashboard"
      width = 1200
      height = 800
      minWidth = 1000
      minHeight = 700

      scene = new Scene {
        fill = createBackgroundGradient()
        root = createMainLayout(analysis)

        // Add subtle fade-in animation
        opacity = 0.0
        val fadeIn: FadeTransition = new FadeTransition(Duration(800), root.value) {
          fromValue = 0.0
          toValue = 1.0
        }
        fadeIn.play()
      }
    }
  }

  private def createBackgroundGradient(): LinearGradient = {
    LinearGradient(0, 0, 1, 1, true, CycleMethod.NoCycle,
      Stop(0, Color.web("#f1f5f9")),
      Stop(0.5, Color.web("#e2e8f0")),
      Stop(1, Color.web("#cbd5e1"))
    )
  }

  private def loadAnalysisData(): Analysis = {
    val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
    val data = Loader.loadData(inputCsv)
    new Analysis(data)
  }

  private def createMainLayout(analysis: Analysis): BorderPane = {
    new BorderPane {
      padding = Insets(25)
      top = createHeaderSection(analysis)
      center = createMainContent(analysis)
      bottom = createFooterSection()
    }
  }

  private def createHeaderSection(analysis: Analysis): VBox = {
    val titleContainer = new HBox {
      alignment = Pos.Center
      spacing = 20

      children = Seq(
        // App icon/logo placeholder
        new Label("🌍") {
          font = Font.font("Apple Color Emoji", 48)
          effect = new DropShadow(5, Color.web("#00000020"))
        },

        new VBox {
          alignment = Pos.CenterLeft
          spacing = 5
          children = Seq(
            new Label("Global Development Analytics") {
              font = Font.font("Segoe UI", FontWeight.Bold, 42)
              textFill = Color.web(darkGray)
              effect = new DropShadow(2, Color.web("#00000015"))
            },
            new Label("Professional Insights Dashboard") {
              font = Font.font("Segoe UI", FontWeight.Normal, 18)
              textFill = Color.web(neutralGray)
            }
          )
        }
      )
    }

    val statsBar = createStatsBar(analysis)

    new VBox(30, titleContainer, statsBar) {
      alignment = Pos.Center
      padding = Insets(0, 0, 25, 0)
    }
  }

  private def createStatsBar(analysis: Analysis): HBox = {
    val recordCount: Int = analysis.records.length
    val countryCount: Int = analysis.records.map(_.country_name).distinct.length
    val yearRange: String = if (analysis.records.nonEmpty) {
      val years = analysis.records.map(_.year)
      s"${years.min} - ${years.max}"
    } else "No data"

    val stats: Seq[(String, String, String, String)] = Seq(
      ("📊", "Total Records", recordCount.toString, primaryBlue),
      ("🌎", "Countries", countryCount.toString, accentGreen),
      ("📅", "Year Range", yearRange, accentOrange)
    )

    new HBox(20) {
      alignment = Pos.Center
      children = stats.map { case (icon, label, value, color) =>
        createStatCard(icon, label, value, color)
      }
    }
  }

  private def createStatCard(icon: String, label: String, value: String, color: String): VBox = {
    new VBox {
      alignment = Pos.Center
      spacing = 8
      padding = Insets(20, 30, 20, 30)
      minWidth = 150

      style = s"""
        -fx-background-color: $cardBackground;
        -fx-background-radius: 12;
        -fx-effect: dropshadow(gaussian, #00000020, 8, 0.3, 0, 2);
        -fx-border-color: ${color}40;
        -fx-border-width: 1;
        -fx-border-radius: 12;
      """

      children = Seq(
        new Label(icon) {
          font = Font.font("Apple Color Emoji", 28)
        },
        new Label(value) {
          font = Font.font("Segoe UI", FontWeight.Bold, 24)
          textFill = Color.web(color)
        },
        new Label(label) {
          font = Font.font("Segoe UI", FontWeight.Normal, 12)
          textFill = Color.web(neutralGray)
        }
      )

      onMouseEntered = (_: scalafx.scene.input.MouseEvent) => {
        style = s"""
          -fx-background-color: $cardBackground;
          -fx-background-radius: 12;
          -fx-effect: dropshadow(gaussian, #00000030, 12, 0.4, 0, 4);
          -fx-border-color: $color;
          -fx-border-width: 2;
          -fx-border-radius: 12;
          -fx-scale-x: 1.05;
          -fx-scale-y: 1.05;
        """
      }

      onMouseExited = (_: scalafx.scene.input.MouseEvent) => {
        style = s"""
          -fx-background-color: $cardBackground;
          -fx-background-radius: 12;
          -fx-effect: dropshadow(gaussian, #00000020, 8, 0.3, 0, 2);
          -fx-border-color: ${color}40;
          -fx-border-width: 1;
          -fx-border-radius: 12;
          -fx-scale-x: 1.0;
          -fx-scale-y: 1.0;
        """
      }
    }
  }

  private def createMainContent(analysis: Analysis): ScrollPane = {
    val contentVBox = new VBox(30) {
      padding = Insets(20)
      alignment = Pos.TopCenter
      maxWidth = 1000

      children = Seq(
        createDataStatusCard(analysis),
        createAnalysisSection(analysis)
      )
    }

    new ScrollPane {
      content = contentVBox
      fitToWidth = true
      hbarPolicy = ScrollPane.ScrollBarPolicy.Never
      vbarPolicy = ScrollPane.ScrollBarPolicy.AsNeeded
      style = "-fx-background: transparent; -fx-background-color: transparent;"

      // Center the content
      val wrapper: HBox = new HBox(contentVBox) {
        alignment = Pos.TopCenter
        fillHeight = false
      }
      content = wrapper
    }
  }

  private def createDataStatusCard(analysis: Analysis): VBox = {
    val isDataLoaded: Boolean = analysis.records.nonEmpty
    val statusColor: String = if (isDataLoaded) accentGreen else accentRed
    val statusIcon: String = if (isDataLoaded) "✅" else "⚠️"
    val statusText: String = if (isDataLoaded)
      "Data Successfully Loaded"
    else
      "Data Loading Failed"

    val detailText: String = if (isDataLoaded)
      f"Successfully processed ${analysis.records.length}%,d development indicators from ${analysis.records.map(_.country_name).distinct.length} countries worldwide"
    else
      "Please verify your data source configuration and ensure the CSV file is accessible"

    new VBox {
      alignment = Pos.Center
      spacing = 15
      padding = Insets(30)
      maxWidth = 800

      style = s"""
        -fx-background-color: $cardBackground;
        -fx-background-radius: 16;
        -fx-effect: dropshadow(gaussian, #00000025, 10, 0.4, 0, 3);
        -fx-border-color: ${statusColor}30;
        -fx-border-width: 2;
        -fx-border-radius: 16;
      """

      children = Seq(
        new HBox {
          alignment = Pos.Center
          spacing = 15
          children = Seq(
            new Label(statusIcon) {
              font = Font.font("Apple Color Emoji", 32)
            },
            new Label(statusText) {
              font = Font.font("Segoe UI", FontWeight.Bold, 20)
              textFill = Color.web(statusColor)
            }
          )
        },
        new Label(detailText) {
          font = Font.font("Segoe UI", FontWeight.Normal, 14)
          textFill = Color.web(neutralGray)
          textAlignment = TextAlignment.Center
          wrapText = true
          maxWidth = 700
        }
      )
    }
  }

  private def createAnalysisSection(analysis: Analysis): VBox = {
    val questions: List[(String, String, String, String, String, String)] = List(
      ("💚", "Life Expectancy Excellence", "Which country achieved the highest life expectancy?",
        getLifeExpectancyAnswer(analysis), accentGreen, "Health & Longevity"),
      ("🎓", "Health & Education Mastery", "Which nation leads in comprehensive human development?",
        getHealthEducationAnswer(analysis), primaryBlue, "Development Leadership"),
      ("🌲", "Environmental Impact", "Which country experienced the greatest forest area loss?",
        getForestLossAnswer(analysis), accentRed, "Conservation Challenge")
    )

    val sectionHeader: Label = new Label("Analysis Results") {
      font = Font.font("Segoe UI", FontWeight.Bold, 28)
      textFill = Color.web(darkGray)
      padding = Insets(0, 0, 20, 0)
    }

    val questionCards: List[VBox] = questions.zipWithIndex.map { case ((icon, title, question, answer, color, category), index) =>
      createProfessionalQuestionCard(icon, title, question, answer, color, category, index + 1)
    }

    new VBox(25, sectionHeader +: questionCards: _*) {
      alignment = Pos.Center
      maxWidth = 900
    }
  }

  private def createProfessionalQuestionCard(
                                              icon: String,
                                              title: String,
                                              question: String,
                                              answer: String,
                                              color: String,
                                              category: String,
                                              number: Int
                                            ): VBox = {

    val headerSection: HBox = new HBox {
      alignment = Pos.CenterLeft
      spacing = 20
      padding = Insets(0, 0, 15, 0)

      children = Seq(
        // Number badge
        new Label(number.toString) {
          minWidth = 50
          minHeight = 50
          alignment = Pos.Center
          font = Font.font("Segoe UI", FontWeight.Bold, 20)
          textFill = Color.White
          style = s"""
            -fx-background-radius: 25;
            -fx-background-color: linear-gradient(to bottom right, $color, ${adjustBrightness(color, -20)});
            -fx-effect: dropshadow(gaussian, #00000030, 6, 0.4, 0, 2);
          """
        },

        new VBox {
          spacing = 5
          children = Seq(
            new HBox {
              spacing = 10
              alignment = Pos.CenterLeft
              children = Seq(
                new Label(icon) {
                  font = Font.font("Apple Color Emoji", 28)
                },
                new Label(title) {
                  font = Font.font("Segoe UI", FontWeight.Bold, 20)
                  textFill = Color.web(darkGray)
                }
              )
            },
            new Label(category) {
              font = Font.font("Segoe UI", FontWeight.Normal, 12)
              textFill = Color.web(color)
              style = s"""
                -fx-background-color: ${color}20;
                -fx-background-radius: 10;
                -fx-padding: 4 12 4 12;
              """
            }
          )
        }
      )
    }

    val questionSection: Label = new Label(question) {
      font = Font.font("Segoe UI", FontWeight.Normal, 16)
      textFill = Color.web(neutralGray)
      wrapText = true
      padding = Insets(0, 0, 15, 0)
    }

    val answerSection: Label = new Label(answer) {
      font = Font.font("Segoe UI", FontWeight.SemiBold, 16)
      textFill = Color.web(color)
      wrapText = true
      style = s"""
        -fx-background-color: ${color}15;
        -fx-background-radius: 12;
        -fx-padding: 16 20 16 20;
        -fx-border-color: ${color}40;
        -fx-border-width: 1;
        -fx-border-radius: 12;
      """
    }

    new VBox {
      spacing = 0
      padding = Insets(30)

      style = s"""
        -fx-background-color: $cardBackground;
        -fx-background-radius: 16;
        -fx-effect: dropshadow(gaussian, #00000020, 10, 0.3, 0, 3);
        -fx-border-color: #e2e8f020;
        -fx-border-width: 1;
        -fx-border-radius: 16;
      """

      children = Seq(headerSection, questionSection, answerSection)

      // Add sophisticated hover effect
      onMouseEntered = (_: scalafx.scene.input.MouseEvent) => {
        style = s"""
          -fx-background-color: $cardBackground;
          -fx-background-radius: 16;
          -fx-effect: dropshadow(gaussian, #00000030, 15, 0.4, 0, 6);
          -fx-border-color: ${color}60;
          -fx-border-width: 2;
          -fx-border-radius: 16;
          -fx-scale-x: 1.02;
          -fx-scale-y: 1.02;
        """
      }

      onMouseExited = (_: scalafx.scene.input.MouseEvent) => {
        style = s"""
          -fx-background-color: $cardBackground;
          -fx-background-radius: 16;
          -fx-effect: dropshadow(gaussian, #00000020, 10, 0.3, 0, 3);
          -fx-border-color: #e2e8f020;
          -fx-border-width: 1;
          -fx-border-radius: 16;
          -fx-scale-x: 1.0;
          -fx-scale-y: 1.0;
        """
      }
    }
  }

  private def createFooterSection(): VBox = {
    new VBox {
      alignment = Pos.Center
      padding = Insets(25, 0, 10, 0)
      spacing = 10

      children = Seq(
        new Separator {
          maxWidth = 800
          style = s"-fx-background-color: $neutralGray;"
        },
        new Label("Professional Analytics Dashboard • Powered by ScalaFX") {
          font = Font.font("Segoe UI", FontPosture.Italic, 12)
          textFill = Color.web(neutralGray)
        },
        new Label("© 2024 Global Development Analytics Suite") {
          font = Font.font("Segoe UI", FontWeight.Normal, 10)
          textFill = Color.web("#94a3b8")
        }
      )
    }
  }

  private def adjustBrightness(hexColor: String, percent: Double): String = {
    val color = Color.web(hexColor)
    val factor = 1 + (percent / 100.0)
    val newRed = math.min(1.0, math.max(0.0, color.red * factor))
    val newGreen = math.min(1.0, math.max(0.0, color.green * factor))
    val newBlue = math.min(1.0, math.max(0.0, color.blue * factor))

    f"#${(newRed * 255).toInt}%02x${(newGreen * 255).toInt}%02x${(newBlue * 255).toInt}%02x"
  }

  // Answer methods with enhanced formatting
  private def getLifeExpectancyAnswer(analysis: Analysis): String = {
    analysis.highestLifeExpectancy match {
      case Some((country, year)) =>
        s"🏆 $country achieved peak life expectancy in $year, demonstrating exceptional healthcare quality and living standards."
      case None =>
        "⚠️ Insufficient data available for comprehensive life expectancy analysis."
    }
  }

  private def getHealthEducationAnswer(analysis: Analysis): String = {
    analysis.topHealthAndEducationCountry match {
      case Some(country) =>
        s"🌟 $country excels across all health and education indicators, representing the gold standard in comprehensive human development."
      case None =>
        "⚠️ Unable to determine leader due to incomplete health and education data coverage."
    }
  }

  private def getForestLossAnswer(analysis: Analysis): String = {
    analysis.highestForestLoss match {
      case Some((country, loss)) =>
        f"🔥 $country experienced the most significant environmental impact with $loss%.2f%% forest area reduction from 2000-2020."
      case None =>
        "⚠️ Forest area change data insufficient for comparative environmental impact analysis."
    }
  }
}