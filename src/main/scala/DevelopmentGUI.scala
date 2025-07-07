import javax.swing._
import java.awt._
import java.awt.event.{MouseAdapter, MouseEvent}

/**
 * Dynamic Visual GUI with Left-Aligned Questions and Rich Visual Appeal
 */
object DevelopmentGUI {

  // Rich color palette
  private val primaryDark = new Color(15, 23, 42)
  private val primaryBlue = new Color(37, 99, 235)
  private val primaryBlueLight = new Color(59, 130, 246)
  private val surfaceWhite = new Color(255, 255, 255)
  private val backgroundGray = new Color(248, 250, 252)
  private val backgroundGradient = new Color(243, 244, 246)
  private val accentGreen = new Color(34, 197, 94)
  private val accentGreenLight = new Color(74, 222, 128)
  private val accentOrange = new Color(234, 88, 12)
  private val accentOrangeLight = new Color(251, 146, 60)
  private val accentRed = new Color(239, 68, 68)
  private val accentRedLight = new Color(248, 113, 113)
  private val textPrimary = new Color(15, 23, 42)
  private val textSecondary = new Color(100, 116, 139)
  private val textMuted = new Color(148, 163, 184)
  private val borderLight = new Color(226, 232, 240)
  private val shadowColor = new Color(0, 0, 0, 15)

  def launch(): Unit = {
    SwingUtilities.invokeLater(() => createDynamicGUI())
  }

  private def createDynamicGUI(): Unit = {
    val analysis = loadData()

    val frame = new JFrame("Global Development Analytics")
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setSize(1400, 950)
    frame.setLocationRelativeTo(null)
    frame.setBackground(backgroundGray)

    val mainContainer = new JPanel(new BorderLayout())
    mainContainer.setBackground(backgroundGray)

    mainContainer.add(createRichHeader(analysis), BorderLayout.NORTH)
    mainContainer.add(createDynamicBody(analysis), BorderLayout.CENTER)
    mainContainer.add(createStylizedFooter(), BorderLayout.SOUTH)

    frame.add(mainContainer)
    frame.setVisible(true)
    frame.toFront()
  }

  private def createRichHeader(analysis: Analysis): JPanel = {
    val headerContainer = new JPanel(new BorderLayout())
    headerContainer.setBackground(surfaceWhite)
    headerContainer.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(primaryBlue.getRed, primaryBlue.getGreen, primaryBlue.getBlue, 30)),
      BorderFactory.createEmptyBorder(50, 60, 50, 60)
    ))

    // Dynamic brand section
    val brandSection = new JPanel()
    brandSection.setLayout(new BoxLayout(brandSection, BoxLayout.Y_AXIS))
    brandSection.setBackground(surfaceWhite)

    val brandContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0))
    brandContainer.setBackground(surfaceWhite)

    val brandIcon = new JLabel("⬢")
    brandIcon.setFont(new Font("Arial", Font.BOLD, 36))
    brandIcon.setForeground(primaryBlue)

    val mainTitle = new JLabel("Global Development Analytics")
    mainTitle.setFont(new Font("Arial", Font.BOLD, 42))
    mainTitle.setForeground(textPrimary)

    brandContainer.add(brandIcon)
    brandContainer.add(mainTitle)

    val subtitle = new JLabel("Advanced Intelligence & Data Visualization Platform", SwingConstants.CENTER)
    subtitle.setFont(new Font("Arial", Font.PLAIN, 17))
    subtitle.setForeground(textSecondary)
    subtitle.setAlignmentX(Component.CENTER_ALIGNMENT)

    brandSection.add(brandContainer)
    brandSection.add(Box.createVerticalStrut(15))
    brandSection.add(subtitle)

    // Rich metrics dashboard
    val metricsSection = createRichMetricsGrid(analysis)

    headerContainer.add(brandSection, BorderLayout.CENTER)
    headerContainer.add(metricsSection, BorderLayout.SOUTH)

    headerContainer
  }

  private def createRichMetricsGrid(analysis: Analysis): JPanel = {
    val metricsContainer = new JPanel(new GridBagLayout())
    metricsContainer.setBackground(surfaceWhite)
    metricsContainer.setBorder(BorderFactory.createEmptyBorder(45, 0, 0, 0))

    val gbc = new GridBagConstraints()
    gbc.insets = new Insets(0, 25, 0, 25)
    gbc.fill = GridBagConstraints.BOTH
    gbc.weightx = 1.0

    val recordCount = analysis.records.length
    val countryCount = analysis.records.map(_.country_name).distinct.length
    val yearRange = if (analysis.records.nonEmpty) {
      val years = analysis.records.map(_.year)
      s"${years.min}–${years.max}"
    } else "No data"

    gbc.gridx = 0
    metricsContainer.add(createDynamicMetricCard("📊", "Data Records", f"$recordCount%,d", "Total Indicators", primaryBlue, primaryBlueLight), gbc)

    gbc.gridx = 1
    metricsContainer.add(createDynamicMetricCard("🌍", "Global Reach", countryCount.toString, "Countries Analyzed", accentGreen, accentGreenLight), gbc)

    gbc.gridx = 2
    metricsContainer.add(createDynamicMetricCard("📅", "Time Coverage", yearRange, "Years of Data", accentOrange, accentOrangeLight), gbc)

    metricsContainer
  }

  private def createDynamicMetricCard(icon: String, title: String, value: String, subtitle: String,
                                      primaryColor: Color, lightColor: Color): JPanel = {
    val card = new JPanel(new BorderLayout())
    card.setBackground(surfaceWhite)
    card.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(borderLight, 1),
        BorderFactory.createLineBorder(new Color(primaryColor.getRed, primaryColor.getGreen, primaryColor.getBlue, 40), 2)
      ),
      BorderFactory.createEmptyBorder(25, 20, 25, 20)
    ))
    card.setPreferredSize(new Dimension(300, 150))

    // Icon section
    val iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    iconPanel.setBackground(new Color(primaryColor.getRed, primaryColor.getGreen, primaryColor.getBlue, 20))
    iconPanel.setPreferredSize(new Dimension(300, 40))

    val iconLabel = new JLabel(icon)
    iconLabel.setFont(new Font("Arial", Font.BOLD, 20))
    iconPanel.add(iconLabel)

    // Content section
    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(surfaceWhite)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0))

    val titleLabel = new JLabel(title, SwingConstants.CENTER)
    titleLabel.setFont(new Font("Arial", Font.BOLD, 13))
    titleLabel.setForeground(textSecondary)
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    val valueLabel = new JLabel(value, SwingConstants.CENTER)
    valueLabel.setFont(new Font("Arial", Font.BOLD, 28))
    valueLabel.setForeground(primaryColor)
    valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    val subtitleLabel = new JLabel(subtitle, SwingConstants.CENTER)
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 11))
    subtitleLabel.setForeground(textMuted)
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    contentPanel.add(titleLabel)
    contentPanel.add(Box.createVerticalStrut(8))
    contentPanel.add(valueLabel)
    contentPanel.add(Box.createVerticalStrut(5))
    contentPanel.add(subtitleLabel)

    card.add(iconPanel, BorderLayout.NORTH)
    card.add(contentPanel, BorderLayout.CENTER)

    // Add hover effect
    addInteractiveEffect(card, primaryColor, lightColor)

    card
  }

  private def createDynamicBody(analysis: Analysis): JScrollPane = {
    val bodyPanel = new JPanel()
    bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS))
    bodyPanel.setBackground(backgroundGray)
    bodyPanel.setBorder(BorderFactory.createEmptyBorder(50, 60, 80, 60))

    // Rich status section
    bodyPanel.add(createRichStatusSection(analysis))
    bodyPanel.add(Box.createVerticalStrut(60))

    // Dynamic section header
    bodyPanel.add(createDynamicSectionHeader())
    bodyPanel.add(Box.createVerticalStrut(50))

    // Dynamic analysis cards with left-aligned questions
    bodyPanel.add(createDynamicAnalysisCard(
      "01", "🏥", "Life Expectancy Excellence",
      "Which country achieved the highest life expectancy standards and set the global benchmark for healthcare quality?",
      getLifeExpectancyAnswer(analysis),
      accentGreen,
      accentGreenLight,
      "Health Intelligence"
    ))
    bodyPanel.add(Box.createVerticalStrut(35))

    bodyPanel.add(createDynamicAnalysisCard(
      "02", "🎓", "Human Development Leadership",
      "Which nation demonstrates the most comprehensive excellence across health, education, and social development metrics?",
      getHealthEducationAnswer(analysis),
      primaryBlue,
      primaryBlueLight,
      "Social Progress"
    ))
    bodyPanel.add(Box.createVerticalStrut(35))

    bodyPanel.add(createDynamicAnalysisCard(
      "03", "🌳", "Environmental Impact Assessment",
      "Which country experienced the most significant environmental transformation and what does this reveal about conservation challenges?",
      getForestLossAnswer(analysis),
      accentRed,
      accentRedLight,
      "Environmental Intelligence"
    ))

    val scrollPane = new JScrollPane(bodyPanel)
    scrollPane.setBorder(null)
    scrollPane.getViewport.setBackground(backgroundGray)
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED)
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)

    // Ultra-smooth scrolling
    scrollPane.getVerticalScrollBar.setUnitIncrement(25)
    scrollPane.getVerticalScrollBar.setBlockIncrement(100)

    scrollPane
  }

  private def createRichStatusSection(analysis: Analysis): JPanel = {
    val isLoaded = analysis.records.nonEmpty
    val statusColor = if (isLoaded) accentGreen else accentRed
    val lightColor = if (isLoaded) accentGreenLight else accentRedLight
    val statusIcon = if (isLoaded) "✓" else "✗"
    val statusText = if (isLoaded) "System Status: Fully Operational" else "System Status: Error Detected"
    val detailText = if (isLoaded)
      f"Successfully processing ${analysis.records.length}%,d development indicators from ${analysis.records.map(_.country_name).distinct.length} countries across the globe"
    else
      "Data pipeline validation failed - please verify source configuration and retry"

    val dashboard = new JPanel(new BorderLayout())
    dashboard.setBackground(surfaceWhite)
    dashboard.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(statusColor.getRed, statusColor.getGreen, statusColor.getBlue, 60), 2),
        BorderFactory.createLineBorder(surfaceWhite, 3)
      ),
      BorderFactory.createEmptyBorder(30, 40, 30, 40)
    ))
    dashboard.setMaximumSize(new Dimension(1100, 130))

    // Status header with icon
    val statusContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0))
    statusContainer.setBackground(surfaceWhite)

    val iconBadge = new JLabel(statusIcon)
    iconBadge.setFont(new Font("Arial", Font.BOLD, 20))
    iconBadge.setForeground(Color.WHITE)
    iconBadge.setOpaque(true)
    iconBadge.setBackground(statusColor)
    iconBadge.setHorizontalAlignment(SwingConstants.CENTER)
    iconBadge.setPreferredSize(new Dimension(40, 40))
    iconBadge.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8))

    val statusLabel = new JLabel(statusText)
    statusLabel.setFont(new Font("Arial", Font.BOLD, 20))
    statusLabel.setForeground(statusColor)

    statusContainer.add(iconBadge)
    statusContainer.add(statusLabel)

    val detailLabel = new JLabel(detailText, SwingConstants.CENTER)
    detailLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    detailLabel.setForeground(textSecondary)

    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(surfaceWhite)

    statusContainer.setAlignmentX(Component.CENTER_ALIGNMENT)
    detailLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    contentPanel.add(statusContainer)
    contentPanel.add(Box.createVerticalStrut(12))
    contentPanel.add(detailLabel)

    dashboard.add(contentPanel, BorderLayout.CENTER)

    val wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER))
    wrapper.setBackground(backgroundGray)
    wrapper.add(dashboard)

    wrapper
  }

  private def createDynamicSectionHeader(): JPanel = {
    val headerContainer = new JPanel()
    headerContainer.setLayout(new BoxLayout(headerContainer, BoxLayout.Y_AXIS))
    headerContainer.setBackground(backgroundGray)

    val titleLabel = new JLabel("Intelligence Analysis Reports", SwingConstants.CENTER)
    titleLabel.setFont(new Font("Arial", Font.BOLD, 36))
    titleLabel.setForeground(textPrimary)
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    val subtitleLabel = new JLabel("Comprehensive Global Development Insights & Strategic Intelligence", SwingConstants.CENTER)
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16))
    subtitleLabel.setForeground(textSecondary)
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    // Decorative divider
    val dividerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER))
    dividerContainer.setBackground(backgroundGray)

    val leftDivider = new JPanel()
    leftDivider.setBackground(primaryBlue)
    leftDivider.setPreferredSize(new Dimension(80, 3))

    val centerDot = new JLabel("●")
    centerDot.setFont(new Font("Arial", Font.BOLD, 12))
    centerDot.setForeground(primaryBlue)

    val rightDivider = new JPanel()
    rightDivider.setBackground(primaryBlue)
    rightDivider.setPreferredSize(new Dimension(80, 3))

    dividerContainer.add(leftDivider)
    dividerContainer.add(Box.createHorizontalStrut(15))
    dividerContainer.add(centerDot)
    dividerContainer.add(Box.createHorizontalStrut(15))
    dividerContainer.add(rightDivider)

    headerContainer.add(titleLabel)
    headerContainer.add(Box.createVerticalStrut(10))
    headerContainer.add(subtitleLabel)
    headerContainer.add(Box.createVerticalStrut(25))
    headerContainer.add(dividerContainer)

    headerContainer
  }

  private def createDynamicAnalysisCard(number: String, icon: String, title: String, question: String,
                                        answer: String, primaryColor: Color, lightColor: Color, category: String): JPanel = {
    val card = new JPanel(new BorderLayout())
    card.setBackground(surfaceWhite)
    card.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(borderLight, 1),
        BorderFactory.createMatteBorder(0, 5, 0, 0, primaryColor)
      ),
      BorderFactory.createEmptyBorder(35, 40, 35, 40)
    ))
    card.setMaximumSize(new Dimension(1200, 280))
    card.setPreferredSize(new Dimension(1200, 280))

    // Header with number badge, icon, and title
    val headerPanel = new JPanel(new BorderLayout())
    headerPanel.setBackground(surfaceWhite)

    val leftSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    leftSection.setBackground(surfaceWhite)

    val numberBadge = new JLabel(number)
    numberBadge.setFont(new Font("Arial", Font.BOLD, 18))
    numberBadge.setForeground(Color.WHITE)
    numberBadge.setOpaque(true)
    numberBadge.setBackground(primaryColor)
    numberBadge.setHorizontalAlignment(SwingConstants.CENTER)
    numberBadge.setPreferredSize(new Dimension(45, 45))
    numberBadge.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10))

    val iconLabel = new JLabel(icon)
    iconLabel.setFont(new Font("Arial", Font.BOLD, 24))
    iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 15))

    val titleLabel = new JLabel(title)
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24))
    titleLabel.setForeground(textPrimary)

    leftSection.add(numberBadge)
    leftSection.add(iconLabel)
    leftSection.add(titleLabel)

    val categoryBadge = new JLabel(category)
    categoryBadge.setFont(new Font("Arial", Font.BOLD, 12))
    categoryBadge.setForeground(primaryColor)
    categoryBadge.setOpaque(true)
    categoryBadge.setBackground(new Color(primaryColor.getRed, primaryColor.getGreen, primaryColor.getBlue, 25))
    categoryBadge.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(primaryColor.getRed, primaryColor.getGreen, primaryColor.getBlue, 80), 1),
      BorderFactory.createEmptyBorder(10, 20, 10, 20)
    ))

    headerPanel.add(leftSection, BorderLayout.WEST)
    headerPanel.add(categoryBadge, BorderLayout.EAST)

    // Main content with left question and right answer
    val contentPanel = new JPanel(new BorderLayout(40, 0))
    contentPanel.setBackground(surfaceWhite)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0))

    // Left side - Question
    val questionPanel = new JPanel(new BorderLayout())
    questionPanel.setBackground(new Color(textSecondary.getRed, textSecondary.getGreen, textSecondary.getBlue, 15))
    questionPanel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(textSecondary.getRed, textSecondary.getGreen, textSecondary.getBlue, 40), 1),
      BorderFactory.createEmptyBorder(25, 25, 25, 25)
    ))
    questionPanel.setPreferredSize(new Dimension(500, 120))

    val questionHeader = new JLabel("Research Question")
    questionHeader.setFont(new Font("Arial", Font.BOLD, 12))
    questionHeader.setForeground(textSecondary)

    val questionLabel = new JLabel(s"<html><div style='width: 450px; line-height: 1.4;'>$question</div></html>")
    questionLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    questionLabel.setForeground(textSecondary)

    val questionContent = new JPanel()
    questionContent.setLayout(new BoxLayout(questionContent, BoxLayout.Y_AXIS))
    questionContent.setBackground(new Color(textSecondary.getRed, textSecondary.getGreen, textSecondary.getBlue, 15))

    questionHeader.setAlignmentX(Component.LEFT_ALIGNMENT)
    questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT)

    questionContent.add(questionHeader)
    questionContent.add(Box.createVerticalStrut(8))
    questionContent.add(questionLabel)

    questionPanel.add(questionContent, BorderLayout.CENTER)

    // Right side - Answer
    val answerPanel = new JPanel(new BorderLayout())
    answerPanel.setBackground(new Color(primaryColor.getRed, primaryColor.getGreen, primaryColor.getBlue, 15))
    answerPanel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(primaryColor.getRed, primaryColor.getGreen, primaryColor.getBlue, 60), 2),
      BorderFactory.createEmptyBorder(25, 25, 25, 25)
    ))

    val answerHeader = new JLabel("Analysis Result")
    answerHeader.setFont(new Font("Arial", Font.BOLD, 12))
    answerHeader.setForeground(primaryColor)

    val answerLabel = new JLabel(s"<html><div style='width: 580px; line-height: 1.5; font-weight: bold;'>$answer</div></html>")
    answerLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    answerLabel.setForeground(new Color(primaryColor.getRed, primaryColor.getGreen, primaryColor.getBlue, 200))

    val answerContent = new JPanel()
    answerContent.setLayout(new BoxLayout(answerContent, BoxLayout.Y_AXIS))
    answerContent.setBackground(new Color(primaryColor.getRed, primaryColor.getGreen, primaryColor.getBlue, 15))

    answerHeader.setAlignmentX(Component.LEFT_ALIGNMENT)
    answerLabel.setAlignmentX(Component.LEFT_ALIGNMENT)

    answerContent.add(answerHeader)
    answerContent.add(Box.createVerticalStrut(8))
    answerContent.add(answerLabel)

    answerPanel.add(answerContent, BorderLayout.CENTER)

    contentPanel.add(questionPanel, BorderLayout.WEST)
    contentPanel.add(answerPanel, BorderLayout.CENTER)

    card.add(headerPanel, BorderLayout.NORTH)
    card.add(contentPanel, BorderLayout.CENTER)

    // Add interactive effects
    addInteractiveEffect(card, primaryColor, lightColor)

    val wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER))
    wrapper.setBackground(backgroundGray)
    wrapper.add(card)

    wrapper
  }

  private def createStylizedFooter(): JPanel = {
    val footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    footerPanel.setBackground(surfaceWhite)
    footerPanel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(primaryBlue.getRed, primaryBlue.getGreen, primaryBlue.getBlue, 30)),
      BorderFactory.createEmptyBorder(35, 0, 35, 0)
    ))

    val footerLabel = new JLabel("Global Development Analytics • Advanced Intelligence Platform • Enterprise Edition © 2024")
    footerLabel.setFont(new Font("Arial", Font.ITALIC, 14))
    footerLabel.setForeground(textMuted)

    footerPanel.add(footerLabel)
    footerPanel
  }

  private def addInteractiveEffect(component: JComponent, primaryColor: Color, lightColor: Color): Unit = {
    component.addMouseListener(new MouseAdapter {
      override def mouseEntered(e: MouseEvent): Unit = {
        component.setBackground(new Color(primaryColor.getRed, primaryColor.getGreen, primaryColor.getBlue, 8))
        component.repaint()
      }

      override def mouseExited(e: MouseEvent): Unit = {
        component.setBackground(surfaceWhite)
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
        s"$country achieved the highest life expectancy in $year, establishing itself as the global leader in healthcare excellence and quality of life standards."
      case None =>
        "Life expectancy data is currently unavailable for comprehensive analysis."
    }
  }

  private def getHealthEducationAnswer(analysis: Analysis): String = {
    analysis.topHealthAndEducationCountry match {
      case Some(country) =>
        s"$country demonstrates exceptional performance across all health and education metrics, setting the global standard for comprehensive human development."
      case None =>
        "Health and education assessment data is insufficient for leadership determination."
    }
  }

  private def getForestLossAnswer(analysis: Analysis): String = {
    analysis.highestForestLoss match {
      case Some((country, loss)) =>
        f"$country experienced the most significant environmental transformation with $loss%.2f%% forest area reduction from 2000-2020, highlighting critical conservation challenges."
      case None =>
        "Environmental change data is currently insufficient for forest impact analysis."
    }
  }
}