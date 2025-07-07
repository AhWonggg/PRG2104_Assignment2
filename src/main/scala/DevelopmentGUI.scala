import java.awt.*
import javax.swing.*

/**
 * Modern Dashboard GUI matching the professional design layout
 */
object DevelopmentGUI {

  // Modern color palette matching the design
  private val backgroundColor = new Color(243, 244, 246)
  private val cardBackground = Color.WHITE
  private val primaryBlue = new Color(37, 99, 235)
  private val primaryGreen = new Color(34, 197, 94)
  private val primaryOrange = new Color(251, 146, 60)
  private val primaryRed = new Color(239, 68, 68)
  private val textPrimary = new Color(15, 23, 42)
  private val textSecondary = new Color(100, 116, 139)
  private val borderColor = new Color(229, 231, 235)
  private val successGreen = new Color(34, 197, 94)

  def launch(): Unit = {
    SwingUtilities.invokeLater(() => createModernDashboard())
  }

  private def createModernDashboard(): Unit = {
    val analysis = loadData()

    val frame = new JFrame("Global Development Analytics")
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setSize(1400, 900)
    frame.setLocationRelativeTo(null)
    frame.setBackground(backgroundColor)

    val mainPanel = new JPanel(new BorderLayout())
    mainPanel.setBackground(backgroundColor)

    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(backgroundColor)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40))

    // Header section
    contentPanel.add(createHeaderSection())
    contentPanel.add(Box.createVerticalStrut(30))

    // Metrics cards row
    contentPanel.add(createMetricsSection(analysis))
    contentPanel.add(Box.createVerticalStrut(40))

    // System status section with icon
    contentPanel.add(createSystemStatusSection(analysis))
    contentPanel.add(Box.createVerticalStrut(50))

    // Analysis reports section
    contentPanel.add(createAnalysisReportsSection(analysis))

    val scrollPane = new JScrollPane(contentPanel)
    scrollPane.setBorder(null)
    scrollPane.getViewport.setBackground(backgroundColor)
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED)
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
    scrollPane.getVerticalScrollBar.setUnitIncrement(16)

    frame.add(scrollPane)
    frame.setVisible(true)
    frame.toFront()
  }

  private def createHeaderSection(): JPanel = {
    val headerPanel = new JPanel(new BorderLayout())
    headerPanel.setBackground(backgroundColor)
    headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80))

    // Left side - Icon and title
    val leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    leftPanel.setBackground(backgroundColor)

    // Icon with blue background circle
    val iconPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(primaryBlue)
        g2.fillOval(5, 5, 50, 50)
        g2.setColor(Color.WHITE)
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24))
        val fm = g2.getFontMetrics
        val text = "🌐"
        val x = (60 - fm.stringWidth(text)) / 2
        val y = ((60 - fm.getHeight) / 2) + fm.getAscent
        g2.drawString(text, x, y)
      }
    }
    iconPanel.setPreferredSize(new Dimension(60, 60))
    iconPanel.setBackground(backgroundColor)

    val titlePanel = new JPanel()
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS))
    titlePanel.setBackground(backgroundColor)
    titlePanel.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 0))

    val titleLabel = new JLabel("Global Development Analytics")
    titleLabel.setFont(new Font("Arial", Font.BOLD, 32))
    titleLabel.setForeground(textPrimary)

    val subtitleLabel = new JLabel("Professional Intelligence Dashboard")
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16))
    subtitleLabel.setForeground(textSecondary)

    titlePanel.add(titleLabel)
    titlePanel.add(subtitleLabel)

    leftPanel.add(iconPanel)
    leftPanel.add(titlePanel)

    // Right side - Status indicator
    val statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0))
    statusPanel.setBackground(Color.WHITE)
    statusPanel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(34, 197, 94, 60), 1),
      BorderFactory.createEmptyBorder(15, 25, 15, 25)
    ))

    val checkIcon = new JLabel("✓")
    checkIcon.setFont(new Font("Arial", Font.BOLD, 18))
    checkIcon.setForeground(successGreen)

    val statusLabel = new JLabel("System Operational")
    statusLabel.setFont(new Font("Arial", Font.BOLD, 16))
    statusLabel.setForeground(successGreen)

    statusPanel.add(checkIcon)
    statusPanel.add(statusLabel)

    val rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    rightPanel.setBackground(backgroundColor)
    rightPanel.add(statusPanel)

    headerPanel.add(leftPanel, BorderLayout.WEST)
    headerPanel.add(rightPanel, BorderLayout.EAST)

    headerPanel
  }

  private def createMetricsSection(analysis: Analysis): JPanel = {
    val metricsPanel = new JPanel(new GridLayout(1, 3, 30, 0))
    metricsPanel.setBackground(backgroundColor)
    metricsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200))

    val recordCount = analysis.records.length
    val countryCount = analysis.records.map(_.country_name).distinct.length
    val yearRange = if (analysis.records.nonEmpty) {
      val years = analysis.records.map(_.year)
      s"${years.min}—${years.max}"
    } else "No data"

    metricsPanel.add(createMetricCard("📊", "Data Records", f"$recordCount%,d", primaryBlue))
    metricsPanel.add(createMetricCard("🌍", "Global Reach", countryCount.toString, primaryGreen))
    metricsPanel.add(createMetricCard("📅", "Timeline", yearRange, primaryOrange))

    metricsPanel
  }

  private def createMetricCard(emoji: String, title: String, value: String, accentColor: Color): JPanel = {
    val card = new JPanel(new BorderLayout())
    card.setBackground(Color.WHITE)
    card.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(borderColor, 1),
      BorderFactory.createEmptyBorder(30, 25, 30, 25)
    ))

    // Icon section with colored background
    val iconSection = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 30))
        g2.fillRoundRect(15, 5, 50, 40, 8, 8)
        g2.setColor(accentColor)
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20))
        val fm = g2.getFontMetrics
        val x = (80 - fm.stringWidth(emoji)) / 2
        val y = ((50 - fm.getHeight) / 2) + fm.getAscent
        g2.drawString(emoji, x, y)
      }
    }
    iconSection.setPreferredSize(new Dimension(80, 50))
    iconSection.setBackground(Color.WHITE)

    // Content section
    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(Color.WHITE)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0))

    val titleLabel = new JLabel(title, SwingConstants.CENTER)
    titleLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    titleLabel.setForeground(textSecondary)
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    val valueLabel = new JLabel(value, SwingConstants.CENTER)
    valueLabel.setFont(new Font("Arial", Font.BOLD, 32))
    valueLabel.setForeground(textPrimary)
    valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    // Progress bar
    val progressPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(accentColor)
        g2.fillRoundRect(0, 15, getWidth, 4, 2, 2)
      }
    }
    progressPanel.setPreferredSize(new Dimension(200, 30))
    progressPanel.setBackground(Color.WHITE)
    progressPanel.setAlignmentX(Component.CENTER_ALIGNMENT)

    contentPanel.add(titleLabel)
    contentPanel.add(Box.createVerticalStrut(10))
    contentPanel.add(valueLabel)
    contentPanel.add(Box.createVerticalStrut(15))
    contentPanel.add(progressPanel)

    card.add(iconSection, BorderLayout.NORTH)
    card.add(contentPanel, BorderLayout.CENTER)

    card
  }

  private def createSystemStatusSection(analysis: Analysis): JPanel = {
    val statusCard = new JPanel(new BorderLayout())
    statusCard.setBackground(Color.WHITE)
    statusCard.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(borderColor, 1),
      BorderFactory.createEmptyBorder(30, 40, 30, 40)
    ))
    statusCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120))

    // Left side with icon
    val leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    leftPanel.setBackground(Color.WHITE)

    // Green circle with checkmark
    val iconPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(new Color(34, 197, 94, 30))
        g2.fillOval(10, 10, 60, 60)
        g2.setColor(successGreen)
        g2.setFont(new Font("Arial", Font.BOLD, 24))
        val fm = g2.getFontMetrics
        val text = "✓"
        val x = (80 - fm.stringWidth(text)) / 2
        val y = ((80 - fm.getHeight) / 2) + fm.getAscent
        g2.drawString(text, x, y)
      }
    }
    iconPanel.setPreferredSize(new Dimension(80, 80))
    iconPanel.setBackground(Color.WHITE)

    val textPanel = new JPanel()
    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS))
    textPanel.setBackground(Color.WHITE)
    textPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 0))

    val statusTitle = new JLabel("System Operational")
    statusTitle.setFont(new Font("Arial", Font.BOLD, 24))
    statusTitle.setForeground(textPrimary)

    val recordCount = analysis.records.length
    val countryCount = analysis.records.map(_.country_name).distinct.length
    val statusDescription = new JLabel(f"Processing $recordCount%,d indicators from $countryCount countries worldwide")
    statusDescription.setFont(new Font("Arial", Font.PLAIN, 16))
    statusDescription.setForeground(textSecondary)

    textPanel.add(statusTitle)
    textPanel.add(Box.createVerticalStrut(5))
    textPanel.add(statusDescription)

    leftPanel.add(iconPanel)
    leftPanel.add(textPanel)

    statusCard.add(leftPanel, BorderLayout.CENTER)

    statusCard
  }

  private def createAnalysisReportsSection(analysis: Analysis): JPanel = {
    val sectionPanel = new JPanel()
    sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS))
    sectionPanel.setBackground(backgroundColor)

    // Section header
    val headerPanel = new JPanel()
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS))
    headerPanel.setBackground(backgroundColor)
    headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0))

    val titleLabel = new JLabel("Analysis Reports")
    titleLabel.setFont(new Font("Arial", Font.BOLD, 36))
    titleLabel.setForeground(textPrimary)

    val subtitleLabel = new JLabel("Comprehensive Global Development Insights")
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18))
    subtitleLabel.setForeground(textSecondary)
    subtitleLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0))

    headerPanel.add(titleLabel)
    headerPanel.add(subtitleLabel)

    sectionPanel.add(headerPanel)

    // Analysis cards
    sectionPanel.add(createAnalysisCard("01", "🏥", "Life Expectancy Leadership", "Health Excellence",
      "Which country achieved the highest life expectancy standards?", getLifeExpectancyAnswer(analysis), primaryGreen))
    sectionPanel.add(Box.createVerticalStrut(25))

    sectionPanel.add(createAnalysisCard("02", "🎓", "Human Development Mastery", "Social Progress",
      "Which nation leads in comprehensive human development?", getHealthEducationAnswer(analysis), primaryBlue))
    sectionPanel.add(Box.createVerticalStrut(25))

    sectionPanel.add(createAnalysisCard("03", "🌳", "Environmental Impact Assessment", "Environmental Intelligence",
      "Which country experienced the most significant forest loss?", getForestLossAnswer(analysis), primaryRed))

    sectionPanel
  }

  private def createAnalysisCard(number: String, emoji: String, title: String, category: String,
                                 question: String, answer: String, accentColor: Color): JPanel = {
    val card = new JPanel(new BorderLayout())
    card.setBackground(Color.WHITE)
    card.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(borderColor, 1),
      BorderFactory.createEmptyBorder(35, 40, 35, 40)
    ))
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180))

    // Left side - Number badge, emoji and content
    val leftPanel = new JPanel(new BorderLayout())
    leftPanel.setBackground(Color.WHITE)

    // Top row with badge, emoji and title
    val topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    topPanel.setBackground(Color.WHITE)

    // Number badge
    val numberBadge = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(accentColor)
        g2.fillRoundRect(0, 0, 50, 50, 8, 8)
        g2.setColor(Color.WHITE)
        g2.setFont(new Font("Arial", Font.BOLD, 20))
        val fm = g2.getFontMetrics
        val x = (50 - fm.stringWidth(number)) / 2
        val y = ((50 - fm.getHeight) / 2) + fm.getAscent
        g2.drawString(number, x, y)
      }
    }
    numberBadge.setPreferredSize(new Dimension(50, 50))
    numberBadge.setBackground(Color.WHITE)

    val emojiLabel = new JLabel(emoji)
    emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28))
    emojiLabel.setBorder(BorderFactory.createEmptyBorder(10, 25, 0, 20))

    val titleLabel = new JLabel(title)
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24))
    titleLabel.setForeground(textPrimary)
    titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0))

    topPanel.add(numberBadge)
    topPanel.add(emojiLabel)
    topPanel.add(titleLabel)

    // Question content
    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(Color.WHITE)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 75, 0, 0))

    val questionLabel = new JLabel(question)
    questionLabel.setFont(new Font("Arial", Font.PLAIN, 16))
    questionLabel.setForeground(textSecondary)

    // Progress bar
    val progressPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(accentColor)
        g2.fillRoundRect(0, 10, 400, 4, 2, 2)
      }
    }
    progressPanel.setPreferredSize(new Dimension(400, 25))
    progressPanel.setBackground(Color.WHITE)

    contentPanel.add(questionLabel)
    contentPanel.add(Box.createVerticalStrut(15))
    contentPanel.add(progressPanel)

    leftPanel.add(topPanel, BorderLayout.NORTH)
    leftPanel.add(contentPanel, BorderLayout.CENTER)

    // Right side - Category badge
    val categoryBadge = new JLabel(category)
    categoryBadge.setFont(new Font("Arial", Font.BOLD, 14))
    categoryBadge.setForeground(accentColor)
    categoryBadge.setOpaque(true)
    categoryBadge.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 20))
    categoryBadge.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 60), 1),
      BorderFactory.createEmptyBorder(12, 20, 12, 20)
    ))

    val rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    rightPanel.setBackground(Color.WHITE)
    rightPanel.add(categoryBadge)

    card.add(leftPanel, BorderLayout.CENTER)
    card.add(rightPanel, BorderLayout.EAST)

    card
  }

  private def loadData(): Analysis = {
    val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
    val data = Loader.loadData(inputCsv)
    new Analysis(data)
  }

  private def getLifeExpectancyAnswer(analysis: Analysis): String = {
    analysis.highestLifeExpectancy match {
      case Some((country, year)) =>
        s"$country achieved the highest life expectancy in $year"
      case None =>
        "Life expectancy data is currently unavailable"
    }
  }

  private def getHealthEducationAnswer(analysis: Analysis): String = {
    analysis.topHealthAndEducationCountry match {
      case Some(country) =>
        s"$country demonstrates exceptional performance across health and education metrics"
      case None =>
        "Health and education assessment data is insufficient"
    }
  }

  private def getForestLossAnswer(analysis: Analysis): String = {
    analysis.highestForestLoss match {
      case Some((country, loss)) =>
        f"$country experienced $loss%.2f%% forest area reduction from 2000-2020"
      case None =>
        "Environmental change data is currently insufficient"
    }
  }
}