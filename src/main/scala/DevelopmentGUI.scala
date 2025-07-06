import javax.swing._
import java.awt._
import java.awt.event._
import javax.swing.WindowConstants

/**
 * 一个现代化的图形界面，用于展示全球发展数据分析的三个问题答案
 */
class DevelopmentGUI extends JFrame {
  
  def createAndShowGUI(): Unit = {
    initializeFrame()
    
    // 加载数据和进行分析
    val data = loadAnalysisData()
    
    // 创建主面板
    val mainPanel = createMainPanel(data)
    
    add(mainPanel)
    pack()
    setLocationRelativeTo(null)
    setVisible(true)
  }
  
  private def initializeFrame(): Unit = {
    setTitle("Global Development Data Analysis Results")
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    setResizable(true)
    
    // 设置现代化外观
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    } catch {
      case _: Exception => // 使用默认外观
    }
  }
  
  private def loadAnalysisData(): Analysis = {
    val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
    val data = Loader.loadData(inputCsv)
    new Analysis(data)
  }
  
  private def createMainPanel(analysis: Analysis): JPanel = {
    val mainPanel = new JPanel(new BorderLayout())
    mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25))
    mainPanel.setBackground(new Color(248, 249, 250))
    
    // 标题
    val titlePanel = createTitlePanel()
    mainPanel.add(titlePanel, BorderLayout.NORTH)
    
    // 数据状态指示器
    val statusPanel = createStatusPanel(analysis)
    
    // 结果内容
    val contentPanel = createContentPanel(analysis)
    
    // 创建中心面板包含状态和内容
    val centerPanel = new JPanel(new BorderLayout())
    centerPanel.add(statusPanel, BorderLayout.NORTH)
    centerPanel.add(contentPanel, BorderLayout.CENTER)
    centerPanel.setOpaque(false)
    
    mainPanel.add(centerPanel, BorderLayout.CENTER)
    
    // 底部信息
    val footerPanel = createFooterPanel()
    mainPanel.add(footerPanel, BorderLayout.SOUTH)
    
    mainPanel
  }
  
  private def createTitlePanel(): JPanel = {
    val panel = new JPanel(new BorderLayout())
    panel.setOpaque(false)
    panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0))
    
    val titleLabel = new JLabel("Global Development Data Analysis", SwingConstants.CENTER)
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28))
    titleLabel.setForeground(new Color(44, 62, 80))
    
    val subtitleLabel = new JLabel("Statistical Analysis Results", SwingConstants.CENTER)
    subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14))
    subtitleLabel.setForeground(new Color(127, 140, 141))
    
    panel.add(titleLabel, BorderLayout.CENTER)
    panel.add(subtitleLabel, BorderLayout.SOUTH)
    
    panel
  }
  
  private def createStatusPanel(analysis: Analysis): JPanel = {
    val panel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    panel.setOpaque(false)
    panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0))
    
    val statusLabel = if (analysis.records.isEmpty) {
      createStatusLabel("⚠️ Warning: Data loading failed", new Color(231, 76, 60))
    } else {
      createStatusLabel(s"✅ Successfully loaded ${analysis.records.length} data records", new Color(39, 174, 96))
    }
    
    panel.add(statusLabel)
    panel
  }
  
  private def createStatusLabel(text: String, color: Color): JLabel = {
    val label = new JLabel(text)
    label.setFont(new Font("Arial", Font.PLAIN, 14))
    label.setForeground(color)
    label
  }
  
  private def createContentPanel(analysis: Analysis): JPanel = {
    val panel = new JPanel()
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS))
    panel.setOpaque(false)
    
    // Three analysis questions
    val questions = scala.collection.immutable.List(
      ("Question 1: Which country had the highest life expectancy, and in which year?", getLifeExpectancyAnswer(analysis)),
      ("Question 2: Which country performed best in health & education?", getHealthEducationAnswer(analysis)),
      ("Question 3: Which country had the highest forest area loss from 2000 to 2020?", getForestLossAnswer(analysis))
    )
    
    questions.zipWithIndex.foreach { case ((question, answer), index) =>
      if (index > 0) {
        panel.add(Box.createVerticalStrut(20))
      }
      panel.add(createQuestionCard(question, answer, index + 1))
    }
    
    panel
  }
  
  private def getLifeExpectancyAnswer(analysis: Analysis): String = {
    analysis.highestLifeExpectancy match {
      case Some((country, year)) => s"$country achieved the highest life expectancy in $year"
      case None => "Insufficient data available"
    }
  }
  
  private def getHealthEducationAnswer(analysis: Analysis): String = {
    analysis.topHealthAndEducationCountry match {
      case Some(country) => s"$country performed best in comprehensive health & education indicators"
      case None => "Insufficient data available"
    }
  }
  
  private def getForestLossAnswer(analysis: Analysis): String = {
    analysis.highestForestLoss match {
      case Some((country, loss)) => f"$country had the highest forest loss with $loss%.2f%% reduction"
      case None => "Insufficient data available"
    }
  }
  
  private def createQuestionCard(question: String, answer: String, number: Int): JPanel = {
    val card = new JPanel(new BorderLayout())
    card.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
      BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ))
    card.setBackground(Color.WHITE)
    card.setMaximumSize(new Dimension(700, 120))
    card.setPreferredSize(new Dimension(700, 120))
    
    // Question title
    val questionLabel = new JLabel(question)
    questionLabel.setFont(new Font("Arial", Font.BOLD, 16))
    questionLabel.setForeground(new Color(52, 73, 94))
    
    // Answer content
    val answerLabel = new JLabel(s"Answer: $answer")
    answerLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    answerLabel.setForeground(new Color(41, 128, 185))
    answerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0))
    
    // 数字标记
    val numberLabel = new JLabel(number.toString, SwingConstants.CENTER)
    numberLabel.setFont(new Font("Arial", Font.BOLD, 18))
    numberLabel.setForeground(Color.WHITE)
    numberLabel.setOpaque(true)
    numberLabel.setBackground(new Color(52, 152, 219))
    numberLabel.setPreferredSize(new Dimension(35, 35))
    numberLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5))
    
    // 文本面板
    val textPanel = new JPanel()
    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS))
    textPanel.setOpaque(false)
    textPanel.add(questionLabel)
    textPanel.add(answerLabel)
    
    card.add(numberLabel, BorderLayout.WEST)
    card.add(Box.createHorizontalStrut(15), BorderLayout.CENTER)
    card.add(textPanel, BorderLayout.CENTER)
    
    card
  }
  
  private def createFooterPanel(): JPanel = {
    val panel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    panel.setOpaque(false)
    panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0))
    
    val footerLabel = new JLabel("Analysis Completed Successfully")
    footerLabel.setFont(new Font("Arial", Font.ITALIC, 12))
    footerLabel.setForeground(new Color(127, 140, 141))
    
    panel.add(footerLabel)
    panel
  }
}

/**
 * GUI应用程序入口点
 */
object DevelopmentGUIApp {
  def main(args: Array[String]): Unit = {
    // 设置系统外观
    SwingUtilities.invokeLater(new Runnable {
      override def run(): Unit = {
        val gui = new DevelopmentGUI()
        gui.createAndShowGUI()
      }
    })
  }
}
