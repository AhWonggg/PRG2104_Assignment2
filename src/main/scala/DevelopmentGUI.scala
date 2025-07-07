import javax.swing._
import java.awt._
import java.awt.event.{MouseAdapter, MouseEvent}

/**
 * Modern Dashboard GUI matching the professional design layout
 */
object DevelopmentGUI {

  // Modern color palette
  private val backgroundColor = new Color(243, 244, 246)
  private val cardBackground = Color.WHITE
  private val primaryBlue = new Color(59, 130, 246)
  private val primaryGreen = new Color(34, 197, 94)
  private val primaryOrange = new Color(251, 146, 60)
  private val textPrimary = new Color(17, 24, 39)
  private val textSecondary = new Color(107, 114, 128)
  private val borderColor = new Color(229, 231, 235)
  private val successGreen = new Color(16, 185, 129)
  private val progressBarGreen = new Color(34, 197, 94)

  def launch(): Unit = {
    SwingUtilities.invokeLater(() => createModernDashboard())
  }

  private def createModernDashboard(): Unit = {
    val analysis = loadData()

    val frame = new JFrame("Global Development Analytics")
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setSize(1200, 800)
    frame.setLocationRelativeTo(null)
    frame.setBackground(backgroundColor)

    val mainPanel = new JPanel(new BorderLayout())
    mainPanel.setBackground(backgroundColor)

    mainPanel.add(createHeader(), BorderLayout.NORTH)
    mainPanel.add(createDashboardContent(analysis), BorderLayout.CENTER)

    frame.add(mainPanel)
    frame.setVisible(true)
    frame.toFront()
  }

  private def createHeader(): JPanel = {
    val headerPanel = new JPanel(new BorderLayout())
    headerPanel.setBackground(backgroundColor)
    headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40))

    // Left side - Title and subtitle
    val leftPanel = new JPanel()
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS))
    leftPanel.setBackground(backgroundColor)

    val iconTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    iconTitlePanel.setBackground(backgroundColor)

    val iconLabel = new JLabel("🌐")
    iconLabel.setFont(new Font("Arial", Font.PLAIN, 32))
    iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15))

    val titleLabel = new JLabel("Global Development Analytics")
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28))
    titleLabel.setForeground(textPrimary)

    iconTitlePanel.add(iconLabel)
    iconTitlePanel.add(titleLabel)

    val subtitleLabel = new JLabel("Professional Intelligence Dashboard")
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16))
    subtitleLabel.setForeground(textSecondary)
    subtitleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0))

    leftPanel.add(iconTitlePanel)
    leftPanel.add(subtitleLabel)

    // Right side - Status indicator
    val statusPanel = createStatusIndicator()
    val rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    rightPanel.setBackground(backgroundColor)
    rightPanel.add(statusPanel)

    headerPanel.add(leftPanel, BorderLayout.WEST)
    headerPanel.add(rightPanel, BorderLayout.EAST)

    headerPanel
  }

  private def createStatusIndicator(): JPanel = {
    val statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0))
    statusPanel.setBackground(cardBackground)
    statusPanel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(borderColor, 1),
      BorderFactory.createEmptyBorder(12, 20, 12, 20)
    ))

    val statusIcon = new JLabel("✓")
    statusIcon.setFont(new Font("Arial", Font.BOLD, 16))
    statusIcon.setForeground(successGreen)

    val statusLabel = new JLabel("System Operational")
    statusLabel.setFont(new Font("Arial", Font.BOLD, 14))
    statusLabel.setForeground(successGreen)

    statusPanel.add(statusIcon)
    statusPanel.add(statusLabel)

    statusPanel
  }

  private def createDashboardContent(analysis: Analysis): JScrollPane = {
    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(backgroundColor)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40))

    // Metrics cards row
    contentPanel.add(createMetricsRow(analysis))
    contentPanel.add(Box.createVerticalStrut(30))

    // System status section
    contentPanel.add(createSystemStatus(analysis))
    contentPanel.add(Box.createVerticalStrut(40))

    // Analysis reports section
    contentPanel.add(createAnalysisSection(analysis))

    val scrollPane = new JScrollPane(contentPanel)
    scrollPane.setBorder(null)
    scrollPane.getViewport.setBackground(backgroundColor)
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED)
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
    scrollPane.getVerticalScrollBar.setUnitIncrement(16)

    scrollPane
  }

  private def createMetricsRow(analysis: Analysis): JPanel = {
    val metricsPanel = new JPanel(new GridLayout(1, 3, 20, 0))
    metricsPanel.setBackground(backgroundColor)
    metricsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120))

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

  private def createMetricCard(icon: String, title: String, value: String, accentColor: Color): JPanel = {
    val card = new JPanel(new BorderLayout())
    card.setBackground(cardBackground)
    card.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(borderColor, 1),
      BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ))

    // Icon with colored background
    val iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    iconPanel.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 20))
    iconPanel.setPreferredSize(new Dimension(50, 40))

    val iconLabel = new JLabel(icon)
    iconLabel.setFont(new Font("Arial", Font.PLAIN, 20))
    iconPanel.add(iconLabel)

    // Content
    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(cardBackground)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0))

    val titleLabel = new JLabel(title, SwingConstants.CENTER)
    titleLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    titleLabel.setForeground(textSecondary)
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    val valueLabel = new JLabel(value, SwingConstants.CENTER)
    valueLabel.setFont(new Font("Arial", Font.BOLD, 24))
    valueLabel.setForeground(textPrimary)
    valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    contentPanel.add(titleLabel)
    contentPanel.add(Box.createVerticalStrut(5))
    contentPanel.add(valueLabel)

    card.add(iconPanel, BorderLayout.NORTH)
    card.add(contentPanel, BorderLayout.CENTER)

    card
  }

  private def createSystemStatus(analysis: Analysis): JPanel = {
    val statusCard = new JPanel(new BorderLayout())
    statusCard.setBackground(cardBackground)
    statusCard.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(borderColor, 1),
      BorderFactory.createEmptyBorder(25, 30, 25, 30)
    ))
    statusCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100))

    val leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    leftPanel.setBackground(cardBackground)

    val statusIcon = new JLabel("✓")
    statusIcon.setFont(new Font("Arial", Font.BOLD, 24))
    statusIcon.setForeground(successGreen)
    statusIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15))

    val textPanel = new JPanel()
    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS))
    textPanel.setBackground(cardBackground)

    val statusTitle = new JLabel("System Operational")
    statusTitle.setFont(new Font("Arial", Font.BOLD, 18))
    statusTitle.setForeground(textPrimary)

    val statusDescription = new JLabel(f"Processing ${analysis.records.length}%,d indicators from ${analysis.records.map(_.country_name).distinct.length} countries worldwide")
    statusDescription.setFont(new Font("Arial", Font.PLAIN, 14))
    statusDescription.setForeground(textSecondary)

    textPanel.add(statusTitle)
    textPanel.add(statusDescription)

    leftPanel.add(statusIcon)
    leftPanel.add(textPanel)

    statusCard.add(leftPanel, BorderLayout.WEST)

    statusCard
  }

  private def createAnalysisSection(analysis: Analysis): JPanel = {
    val sectionPanel = new JPanel()
    sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS))
    sectionPanel.setBackground(backgroundColor)

    // Section header
    val headerPanel = new JPanel()
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS))
    headerPanel.setBackground(backgroundColor)
    headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0))

    val titleLabel = new JLabel("Analysis Reports")
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28))
    titleLabel.setForeground(textPrimary)

    val subtitleLabel = new JLabel("Comprehensive Global Development Insights")
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16))
    subtitleLabel.setForeground(textSecondary)
    subtitleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0))

    headerPanel.add(titleLabel)
    headerPanel.add(subtitleLabel)

    sectionPanel.add(headerPanel)

    // Analysis cards
    sectionPanel.add(createAnalysisCard(
      "01", "🏥", "Life Expectancy Leadership", "Health Excellence",
      "Which country achieved the highest life expectancy standards?",
      getLifeExpectancyAnswer(analysis),
      primaryGreen
    ))
    sectionPanel.add(Box.createVerticalStrut(20))

    sectionPanel.add(createAnalysisCard(
      "02", "🎓", "Human Development Mastery", "Social Progress",
      "Which nation leads in comprehensive human development?",
      getHealthEducationAnswer(analysis),
      primaryBlue
    ))
    sectionPanel.add(Box.createVerticalStrut(20))

    sectionPanel.add(createAnalysisCard(
      "03", "🌳", "Environmental Impact Assessment", "Environmental Intelligence",
      "Which country experienced the most significant forest loss?",
      getForestLossAnswer(analysis),
      new Color(239, 68, 68)
    ))

    sectionPanel
  }

  private def createAnalysisCard(number: String, icon: String, title: String, category: String,
                                 question: String, answer: String, accentColor: Color): JPanel = {
    val card = new JPanel(new BorderLayout())
    card.setBackground(cardBackground)
    card.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(borderColor, 1),
      BorderFactory.createEmptyBorder(25, 30, 25, 30)
    ))
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150))

    // Left side - Number badge and content
    val leftPanel = new JPanel(new BorderLayout())
    leftPanel.setBackground(cardBackground)

    val badgePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    badgePanel.setBackground(cardBackground)

    val numberBadge = new JLabel(number)
    numberBadge.setFont(new Font("Arial", Font.BOLD, 18))
    numberBadge.setForeground(Color.WHITE)
    numberBadge.setOpaque(true)
    numberBadge.setBackground(accentColor)
    numberBadge.setHorizontalAlignment(SwingConstants.CENTER)
    numberBadge.setPreferredSize(new Dimension(40, 40))

    val iconLabel = new JLabel(icon)
    iconLabel.setFont(new Font("Arial", Font.PLAIN, 20))
    iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 15))

    val titleLabel = new JLabel(title)
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20))
    titleLabel.setForeground(textPrimary)

    badgePanel.add(numberBadge)
    badgePanel.add(iconLabel)
    badgePanel.add(titleLabel)

    // Content panel
    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(cardBackground)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 60, 0, 0))

    val questionLabel = new JLabel(question)
    questionLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    questionLabel.setForeground(textSecondary)

    // Progress bar (visual element)
    val progressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8))
    progressPanel.setBackground(cardBackground)

    val progressBar = new JPanel()
    progressBar.setBackground(progressBarGreen)
    progressBar.setPreferredSize(new Dimension(200, 4))
    progressPanel.add(progressBar)

    contentPanel.add(questionLabel)
    contentPanel.add(progressPanel)

    leftPanel.add(badgePanel, BorderLayout.NORTH)
    leftPanel.add(contentPanel, BorderLayout.CENTER)

    // Right side - Category badge
    val rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    rightPanel.setBackground(cardBackground)

    val categoryBadge = new JLabel(category)
    categoryBadge.setFont(new Font("Arial", Font.BOLD, 12))
    categoryBadge.setForeground(accentColor)
    categoryBadge.setOpaque(true)
    categoryBadge.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 20))
    categoryBadge.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 60), 1),
      BorderFactory.createEmptyBorder(8, 16, 8, 16)
    ))

    rightPanel.add(categoryBadge)

    card.add(leftPanel, BorderLayout.CENTER)
    card.add(rightPanel, BorderLayout.EAST)

    // Add hover effect
    addHoverEffect(card)

    card
  }

  private def addHoverEffect(component: JComponent): Unit = {
    component.addMouseListener(new MouseAdapter {
      override def mouseEntered(e: MouseEvent): Unit = {
        component.setBackground(new Color(249, 250, 251))
        component.repaint()
      }

      override def mouseExited(e: MouseEvent): Unit = {
        component.setBackground(cardBackground)
        component.repaint()
      }
    })
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