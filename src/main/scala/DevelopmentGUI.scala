import java.awt._
import javax.swing._

/**
 * Clean Modern Dashboard GUI following Scala conventions
 */
object DevelopmentGUI {

  // Clean modern color palette - using val for immutable values
  private val backgroundColor = new Color(243, 244, 246)
  private val cardBackground = Color.WHITE
  private val primaryBlue = new Color(59, 130, 246)
  private val primaryGreen = new Color(34, 197, 94)
  private val primaryOrange = new Color(251, 146, 60)
  private val textPrimary = new Color(15, 23, 42)
  private val textSecondary = new Color(100, 116, 139)
  private val borderColor = new Color(229, 231, 235)
  private val successGreen = new Color(34, 197, 94)

  // Vibrant colors for analysis cards
  private val vibrantGreen = new Color(16, 185, 129)
  private val vibrantBlue = new Color(59, 130, 246)
  private val vibrantRed = new Color(239, 68, 68)

  def launch(): Unit = {
    SwingUtilities.invokeLater(() => createModernDashboard())
  }

  private def createModernDashboard(): Unit = {
    try {
      val analysis = loadData()

      val frame = new JFrame("Global Development Analytics")
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
      frame.setSize(1400, 900)
      frame.setLocationRelativeTo(null)
      frame.setBackground(backgroundColor)

      val contentPanel = new JPanel()
      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
      contentPanel.setBackground(backgroundColor)
      contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40))

      // Header section
      contentPanel.add(createHeaderSection())
      contentPanel.add(Box.createVerticalStrut(40))

      // Metrics cards
      contentPanel.add(createMetricsSection(analysis))
      contentPanel.add(Box.createVerticalStrut(50))

      // System status
      contentPanel.add(createSystemStatusSection(analysis))
      contentPanel.add(Box.createVerticalStrut(60))

      // Analysis reports
      contentPanel.add(createAnalysisSection(analysis))

      val scrollPane = new JScrollPane(contentPanel)
      scrollPane.setBorder(null)
      scrollPane.getViewport.setBackground(backgroundColor)
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED)
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
      scrollPane.getVerticalScrollBar.setUnitIncrement(16)

      frame.add(scrollPane)
      frame.setVisible(true)
      frame.toFront()

    } catch {
      case e: Exception =>
        e.printStackTrace()
        JOptionPane.showMessageDialog(null, s"Error creating dashboard: ${e.getMessage}")
    }
  }

  private def createHeaderSection(): JPanel = {
    val headerPanel = new JPanel(new BorderLayout())
    headerPanel.setBackground(backgroundColor)
    headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80))

    // Left side - Icon and title
    val leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    leftPanel.setBackground(backgroundColor)

    // Blue circular icon
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
        val text = "📊"
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
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28))
    titleLabel.setForeground(textPrimary)

    val subtitleLabel = new JLabel("Professional Intelligence Dashboard")
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16))
    subtitleLabel.setForeground(textSecondary)

    titlePanel.add(titleLabel)
    titlePanel.add(subtitleLabel)

    leftPanel.add(iconPanel)
    leftPanel.add(titlePanel)

    // Right side - Status badge
    val statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0))
    statusPanel.setBackground(Color.WHITE)
    statusPanel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(34, 197, 94, 60), 1),
      BorderFactory.createEmptyBorder(12, 20, 12, 20)
    ))

    val checkIcon = new JLabel("✓")
    checkIcon.setFont(new Font("Arial", Font.BOLD, 16))
    checkIcon.setForeground(successGreen)

    val statusLabel = new JLabel("System Operational")
    statusLabel.setFont(new Font("Arial", Font.BOLD, 14))
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
    val countryCount = if (analysis.records.nonEmpty) {
      analysis.records.map(_.country_name).distinct.length
    } else 0

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
      BorderFactory.createEmptyBorder(25, 20, 25, 20)
    ))

    // Icon section - using JLabel for better emoji support
    val iconSection = new JPanel(new BorderLayout())
    iconSection.setBackground(Color.WHITE)
    iconSection.setPreferredSize(new Dimension(80, 70))

    val emojiLabel = new JLabel(emoji, SwingConstants.CENTER)
    emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24))
    emojiLabel.setHorizontalAlignment(SwingConstants.CENTER)
    emojiLabel.setVerticalAlignment(SwingConstants.CENTER)
    emojiLabel.setOpaque(true)
    emojiLabel.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 30))
    emojiLabel.setPreferredSize(new Dimension(70, 50))
    emojiLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8))

    iconSection.add(emojiLabel, BorderLayout.CENTER)

    // Content section
    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(Color.WHITE)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0))

    val titleLabel = new JLabel(title, SwingConstants.CENTER)
    titleLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    titleLabel.setForeground(textSecondary)
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    val valueLabel = new JLabel(value, SwingConstants.CENTER)
    valueLabel.setFont(new Font("Arial", Font.BOLD, 28))
    valueLabel.setForeground(textPrimary)
    valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    // Progress bar
    val progressPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(accentColor)
        val componentSize = getSize()
        g2.fillRoundRect(0, 12, componentSize.width, 4, 2, 2)
      }
    }
    progressPanel.setPreferredSize(new Dimension(200, 25))
    progressPanel.setBackground(Color.WHITE)
    progressPanel.setAlignmentX(Component.CENTER_ALIGNMENT)

    contentPanel.add(titleLabel)
    contentPanel.add(Box.createVerticalStrut(8))
    contentPanel.add(valueLabel)
    contentPanel.add(Box.createVerticalStrut(12))
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
      BorderFactory.createEmptyBorder(25, 30, 25, 30)
    ))
    statusCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150))
    statusCard.setMinimumSize(new Dimension(400, 150))
    statusCard.setPreferredSize(new Dimension(1200, 150))

    // Main content panel
    val mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10))
    mainPanel.setBackground(Color.WHITE)

    // Icon
    val iconLabel = new JLabel("✅")
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32))
    iconLabel.setForeground(successGreen)
    iconLabel.setVerticalAlignment(SwingConstants.TOP)
    iconLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 20))

    // Text panel with fixed layout
    val textPanel = new JPanel()
    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS))
    textPanel.setBackground(Color.WHITE)
    textPanel.setAlignmentY(Component.TOP_ALIGNMENT)

    val recordCount = analysis.records.length
    val countryCount = if (analysis.records.nonEmpty) {
      analysis.records.map(_.country_name).distinct.length
    } else 0

    val titleLabel = new JLabel("System Operational")
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24))
    titleLabel.setForeground(textPrimary)
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT)

    val descLabel = new JLabel(f"Processing $recordCount%,d indicators from $countryCount countries worldwide")
    descLabel.setFont(new Font("Arial", Font.PLAIN, 14))
    descLabel.setForeground(textSecondary)
    descLabel.setAlignmentX(Component.LEFT_ALIGNMENT)

    val statusLabel = new JLabel("● All systems running smoothly")
    statusLabel.setFont(new Font("Arial", Font.PLAIN, 12))
    statusLabel.setForeground(successGreen)
    statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT)

    textPanel.add(titleLabel)
    textPanel.add(Box.createVerticalStrut(8))
    textPanel.add(descLabel)
    textPanel.add(Box.createVerticalStrut(5))
    textPanel.add(statusLabel)
    textPanel.add(Box.createVerticalGlue())

    mainPanel.add(iconLabel)
    mainPanel.add(textPanel)

    // Simple progress bar at bottom
    val progressPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(new Color(34, 197, 94, 20))
        g2.fillRoundRect(0, 5, getWidth(), 3, 2, 2)
        g2.setColor(successGreen)
        g2.fillRoundRect(0, 5, (getWidth() * 0.95).toInt, 3, 2, 2)
      }
    }
    progressPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 15))
    progressPanel.setBackground(Color.WHITE)

    statusCard.add(mainPanel, BorderLayout.CENTER)
    statusCard.add(progressPanel, BorderLayout.SOUTH)
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

    val titleLabel = new JLabel("Analysis Reports", SwingConstants.CENTER)
    titleLabel.setFont(new Font("Arial", Font.BOLD, 32))
    titleLabel.setForeground(textPrimary)
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    val subtitleLabel = new JLabel("Comprehensive Global Development Insights", SwingConstants.CENTER)
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16))
    subtitleLabel.setForeground(textSecondary)
    subtitleLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0))
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT)

    headerPanel.add(titleLabel)
    headerPanel.add(subtitleLabel)

    sectionPanel.add(headerPanel)

    // Analysis cards with vibrant colors
    sectionPanel.add(createAnalysisCard("01", "Life Expectancy Leadership", "Health Excellence",
      "Which country achieved the highest life expectancy standards?", lifeExpectancyAnswer(analysis),
      vibrantGreen, "🏥"))
    sectionPanel.add(Box.createVerticalStrut(25))

    sectionPanel.add(createAnalysisCard("02", "Human Development Mastery", "Social Progress",
      "Which nation leads in comprehensive human development?", healthEducationAnswer(analysis),
      vibrantBlue, "🎓"))
    sectionPanel.add(Box.createVerticalStrut(25))

    sectionPanel.add(createAnalysisCard("03", "Environmental Impact Assessment", "Environmental Intelligence",
      "Which country experienced the most significant forest loss?", forestLossAnswer(analysis),
      vibrantRed, "🌲"))

    sectionPanel
  }

  private def createAnalysisCard(number: String, title: String, category: String,
                                 question: String, answer: String, accentColor: Color, emoji: String): JPanel = {
    val card = new JPanel(new BorderLayout())
    card.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))
    card.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(accentColor, 3),
      BorderFactory.createEmptyBorder(35, 40, 35, 40)
    ))
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200))

    val leftPanel = new JPanel(new BorderLayout())
    leftPanel.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))

    // Top section with enhanced colorful design
    val topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    topPanel.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))

    // Enhanced number badge with multiple visual layers
    val numberBadge = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Outer glow effect
        g2.setColor(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 40))
        g2.fillOval(-2, -2, 58, 58)

        // Main circle with vibrant color
        g2.setColor(accentColor)
        g2.fillOval(2, 2, 50, 50)

        // Inner highlight
        g2.setColor(new Color(255, 255, 255, 60))
        g2.fillOval(8, 8, 38, 38)

        // Inner shadow for depth
        g2.setColor(new Color(0, 0, 0, 30))
        g2.fillOval(12, 12, 30, 30)

        // Number text with shadow
        g2.setColor(new Color(0, 0, 0, 40))
        g2.setFont(new Font("Arial", Font.BOLD, 20))
        val fm = g2.getFontMetrics
        val x = (54 - fm.stringWidth(number)) / 2
        val y = ((54 - fm.getHeight) / 2) + fm.getAscent + 1
        g2.drawString(number, x + 1, y + 1)

        // Number text
        g2.setColor(Color.WHITE)
        g2.drawString(number, x, y)
      }
    }
    numberBadge.setPreferredSize(new Dimension(54, 54))
    numberBadge.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))

    // Enhanced emoji with multiple colorful layers
    val emojiPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Outer colorful ring
        g2.setColor(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 60))
        g2.fillOval(0, 0, 54, 54)

        // Middle ring with different opacity
        g2.setColor(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 120))
        g2.fillOval(6, 6, 42, 42)

        // Inner circle
        g2.setColor(new Color(255, 255, 255, 180))
        g2.fillOval(12, 12, 30, 30)

        // Highlight effect
        g2.setColor(new Color(255, 255, 255, 100))
        g2.fillOval(15, 15, 12, 12)

        // Emoji
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24))
        val fm = g2.getFontMetrics
        val x = (54 - fm.stringWidth(emoji)) / 2
        val y = ((54 - fm.getHeight) / 2) + fm.getAscent
        g2.drawString(emoji, x, y)
      }
    }
    emojiPanel.setPreferredSize(new Dimension(54, 54))
    emojiPanel.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))

    val titleLabel = new JLabel(title)
    titleLabel.setFont(new Font("Arial", Font.BOLD, 26))
    titleLabel.setForeground(new Color(
      Math.max(0, Math.min(255, accentColor.getRed - 30)),
      Math.max(0, Math.min(255, accentColor.getGreen - 30)),
      Math.max(0, Math.min(255, accentColor.getBlue - 30))
    ))
    titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 25, 0, 0))

    topPanel.add(numberBadge)
    topPanel.add(Box.createHorizontalStrut(20))
    topPanel.add(emojiPanel)
    topPanel.add(titleLabel)

    // Middle section with enhanced styling
    val middlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    middlePanel.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))
    middlePanel.setBorder(BorderFactory.createEmptyBorder(18, 75, 0, 0))

    val questionLabel = new JLabel(question)
    questionLabel.setFont(new Font("Arial", Font.PLAIN, 17))
    questionLabel.setForeground(new Color(60, 60, 60))

    middlePanel.add(questionLabel)

    // Bottom section with colorful answer
    val bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    bottomPanel.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 75, 0, 0))

    val answerLabel = new JLabel(answer)
    answerLabel.setFont(new Font("Arial", Font.BOLD, 16))
    answerLabel.setForeground(accentColor)

    bottomPanel.add(answerLabel)

    // Enhanced colorful progress bar with multiple effects
    val progressPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Background track with shadow
        g2.setColor(new Color(0, 0, 0, 20))
        g2.fillRoundRect(76, 12, 420, 8, 4, 4)

        // Background track
        g2.setColor(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 30))
        g2.fillRoundRect(75, 10, 420, 8, 4, 4)

        // Main progress bar with vibrant color
        g2.setColor(accentColor)
        g2.fillRoundRect(75, 10, 340, 8, 4, 4)

        // Inner highlight for 3D effect
        g2.setColor(new Color(255, 255, 255, 120))
        g2.fillRoundRect(75, 10, 340, 3, 2, 2)

        // Animated dots effect (static but colorful)
        for (i <- 0 until 8) {
          val dotX = 85 + (i * 40)
          g2.setColor(new Color(255, 255, 255, 80 - (i * 10)))
          g2.fillOval(dotX, 12, 4, 4)
        }
      }
    }
    progressPanel.setPreferredSize(new Dimension(520, 30))
    progressPanel.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))

    leftPanel.add(topPanel, BorderLayout.NORTH)
    leftPanel.add(middlePanel, BorderLayout.CENTER)
    leftPanel.add(bottomPanel, BorderLayout.SOUTH)

    val bottomContainer = new JPanel(new BorderLayout())
    bottomContainer.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))
    bottomContainer.add(progressPanel, BorderLayout.SOUTH)

    // Enhanced vibrant category badge with multiple effects
    val categoryBadge = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Shadow effect
        g2.setColor(new Color(0, 0, 0, 30))
        g2.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, 12, 12)

        // Main background with vibrant color
        g2.setColor(accentColor)
        g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 12, 12)

        // Top highlight
        g2.setColor(new Color(255, 255, 255, 60))
        g2.fillRoundRect(2, 2, getWidth() - 6, (getHeight() - 6) / 2, 10, 10)

        // Text with shadow
        g2.setFont(new Font("Arial", Font.BOLD, 14))
        val fm = g2.getFontMetrics
        val textWidth = fm.stringWidth(category)
        val x = (getWidth() - textWidth) / 2
        val y = ((getHeight() - fm.getHeight) / 2) + fm.getAscent

        // Text shadow
        g2.setColor(new Color(0, 0, 0, 60))
        g2.drawString(category, x + 1, y + 1)

        // Main text
        g2.setColor(Color.WHITE)
        g2.drawString(category, x, y)
      }
    }
    categoryBadge.setPreferredSize(new Dimension(180, 45))
    categoryBadge.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))

    val rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10))
    rightPanel.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))
    rightPanel.add(categoryBadge)

    val mainContainer = new JPanel(new BorderLayout())
    mainContainer.setBackground(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 15))
    mainContainer.add(leftPanel, BorderLayout.CENTER)
    mainContainer.add(rightPanel, BorderLayout.EAST)
    mainContainer.add(bottomContainer, BorderLayout.SOUTH)

    card.add(mainContainer, BorderLayout.CENTER)
    card
  }

  private def loadData(): Analysis = {
    try {
      val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
      val data = Loader.loadData(inputCsv)
      Analysis(data)
    } catch {
      case e: Exception =>
        println(s"Error loading data: ${e.getMessage}")
        Analysis(Seq.empty)
    }
  }

  // Scala convention: method names should be camelCase, not getXxx
  private def lifeExpectancyAnswer(analysis: Analysis): String = {
    analysis.highestLifeExpectancy match {
      case Some((country, year)) =>
        s"$country achieved peak longevity in $year"
      case None =>
        "No data available"
    }
  }

  private def healthEducationAnswer(analysis: Analysis): String = {
    analysis.topHealthAndEducationCountry match {
      case Some(country) =>
        s"$country excels in comprehensive development"
      case None =>
        "No data available"
    }
  }

  private def forestLossAnswer(analysis: Analysis): String = {
    analysis.highestForestLoss match {
      case Some((country, loss)) =>
        f"$country faced $loss%.1f%% forest reduction"
      case None =>
        "No data available"
    }
  }
}