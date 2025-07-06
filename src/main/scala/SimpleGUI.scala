import javax.swing._
import java.awt._
import java.awt.event._
import javax.swing.WindowConstants

class SimpleGUI(analysis: Analysis) extends JFrame {
  
  def createAndShowGUI(): Unit = {
    setTitle("Global Development Data Analysis - Results")
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setSize(800, 600)
    setLocationRelativeTo(null)
    
    // Create main panel
    val mainPanel = new JPanel(new BorderLayout())
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20))
    
    // Title panel
    val titlePanel = new JPanel()
    val titleLabel = new JLabel("Global Development Data Analysis")
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24))
    titleLabel.setForeground(new Color(44, 62, 80))
    titlePanel.add(titleLabel)
    titlePanel.setBackground(new Color(236, 240, 241))
    titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0))
    
    // Results panel
    val resultsPanel = new JPanel()
    resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS))
    
    // Data status
    val dataStatus = if (analysis.records.isEmpty) {
      createStatusLabel("⚠️ Warning: No data loaded. Please check your CSV file.", new Color(231, 76, 60))
    } else {
      createStatusLabel(s"✅ Data loaded successfully! Analyzing ${analysis.records.length} records.", new Color(39, 174, 96))
    }
    resultsPanel.add(dataStatus)
    resultsPanel.add(Box.createVerticalStrut(20))
    
    // Question 1
    val q1Panel = createQuestionPanel(
      "1. Which country had the highest life expectancy, and in which year?",
      analysis.highestLifeExpectancy match {
        case Some((country, year)) => s"$country achieved the highest life expectancy in $year"
        case None => "No data available for life expectancy analysis"
      }
    )
    resultsPanel.add(q1Panel)
    resultsPanel.add(Box.createVerticalStrut(15))
    
    // Question 2
    val q2Panel = createQuestionPanel(
      "2. Which country performed best in health & education?",
      analysis.topHealthAndEducationCountry match {
        case Some(country) => s"$country performed best in health & education across all indicators"
        case None => "No valid data available for health & education analysis"
      }
    )
    resultsPanel.add(q2Panel)
    resultsPanel.add(Box.createVerticalStrut(15))
    
    // Question 3
    val q3Panel = createQuestionPanel(
      "3. Which country had the highest forest area loss from 2000 to 2020?",
      analysis.highestForestLoss match {
        case Some((country, loss)) => f"$country had the highest forest loss with $loss%.2f%% reduction"
        case None => "No sufficient data available for forest loss analysis"
      }
    )
    resultsPanel.add(q3Panel)
    
    // Footer
    val footerPanel = new JPanel()
    val footerLabel = new JLabel("Analysis completed successfully!")
    footerLabel.setFont(new Font("Arial", Font.ITALIC, 12))
    footerLabel.setForeground(new Color(127, 140, 141))
    footerPanel.add(footerLabel)
    
    // Assemble components
    mainPanel.add(titlePanel, BorderLayout.NORTH)
    
    val scrollPane = new JScrollPane(resultsPanel)
    scrollPane.setBorder(null)
    scrollPane.getVerticalScrollBar.setUnitIncrement(16)
    mainPanel.add(scrollPane, BorderLayout.CENTER)
    
    mainPanel.add(footerPanel, BorderLayout.SOUTH)
    
    add(mainPanel)
    setVisible(true)
  }
  
  private def createStatusLabel(text: String, color: Color): JLabel = {
    val label = new JLabel(text)
    label.setFont(new Font("Arial", Font.PLAIN, 14))
    label.setForeground(color)
    label.setAlignmentX(Component.LEFT_ALIGNMENT)
    label
  }
  
  private def createQuestionPanel(question: String, answer: String): JPanel = {
    val panel = new JPanel()
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS))
    panel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
      BorderFactory.createEmptyBorder(15, 15, 15, 15)
    ))
    panel.setBackground(new Color(248, 249, 250))
    panel.setAlignmentX(Component.LEFT_ALIGNMENT)
    
    val questionLabel = new JLabel(question)
    questionLabel.setFont(new Font("Arial", Font.BOLD, 16))
    questionLabel.setForeground(new Color(52, 73, 94))
    questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT)
    
    val answerLabel = new JLabel(s"<html><div style='margin-left: 20px; color: #2980b9;'>$answer</div></html>")
    answerLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    answerLabel.setAlignmentX(Component.LEFT_ALIGNMENT)
    
    panel.add(questionLabel)
    panel.add(Box.createVerticalStrut(10))
    panel.add(answerLabel)
    
    panel
  }
}

object SimpleGUIApp {
  def main(args: Array[String]): Unit = {
    // Set look and feel
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    } catch {
      case _: Exception => // Use default look and feel
    }
    
    SwingUtilities.invokeLater(new Runnable {
      override def run(): Unit = {
        // Load data
        val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
        val data = Loader.loadData(inputCsv)
        val analysis = new Analysis(data)
        
        // Create and show GUI
        val gui = new SimpleGUI(analysis)
        gui.createAndShowGUI()
      }
    })
  }
}
