import java.awt._
import javax.swing._

object DevelopmentGUI {

  // Enhanced modern color palette
  private val backgroundColor = new Color(248, 250, 252)
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

      val contentPanel = createContentPanel(analysis)
      val scrollPane = createScrollPane(contentPanel)

      frame.add(scrollPane)
      frame.setVisible(true)
      frame.toFront()

    } catch {
      case e: Exception =>
        e.printStackTrace()
        JOptionPane.showMessageDialog(null, s"Error creating dashboard: ${e.getMessage}")
    }
  }

  private def createContentPanel(analysis: Analysis): JPanel = {
    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setBackground(backgroundColor)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40))

    // Add all sections
    contentPanel.add(createHeaderSection())
    contentPanel.add(Box.createVerticalStrut(40))
    contentPanel.add(createMetricsSection(analysis))
    contentPanel.add(Box.createVerticalStrut(50))
    contentPanel.add(createSystemStatusSection(analysis))
    contentPanel.add(Box.createVerticalStrut(60))
    contentPanel.add(createAnalysisSection(analysis))

    contentPanel
  }

  private def createScrollPane(contentPanel: JPanel): JScrollPane = {
    val scrollPane = new JScrollPane(contentPanel)
    scrollPane.setBorder(null)
    scrollPane.getViewport.setBackground(backgroundColor)
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED)
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
    scrollPane.getVerticalScrollBar.setUnitIncrement(16)
    scrollPane
  }

  // Utility method for creating gradient backgrounds
  private def paintGradientBackground(g: Graphics, compWidth: Int, compHeight: Int,
                                      startColor: Color, endColor: Color, cornerRadius: Int = 0): Unit = {
    val g2 = g.asInstanceOf[Graphics2D]
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    val gradient = new GradientPaint(0, 0, startColor, 0, compHeight, endColor)
    g2.setPaint(gradient)

    if (cornerRadius > 0) {
      g2.fillRoundRect(0, 0, compWidth, compHeight, cornerRadius, cornerRadius)
    } else {
      g2.fillRect(0, 0, compWidth, compHeight)
    }
  }

  // Utility method for creating shadows
  private def paintShadow(g: Graphics, compWidth: Int, compHeight: Int,
                          shadowColor: Color, offset: Int = 3, cornerRadius: Int = 0): Unit = {
    val g2 = g.asInstanceOf[Graphics2D]
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2.setColor(shadowColor)

    if (cornerRadius > 0) {
      g2.fillRoundRect(offset, offset, compWidth - offset, compHeight - offset, cornerRadius, cornerRadius)
    } else {
      g2.fillRect(offset, offset, compWidth - offset, compHeight - offset)
    }
  }

  private def createHeaderSection(): JPanel = {
    val headerPanel = new JPanel(new BorderLayout())
    headerPanel.setBackground(backgroundColor)
    headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80))

    headerPanel.add(createHeaderLeft(), BorderLayout.WEST)
    headerPanel.add(createHeaderRight(), BorderLayout.EAST)

    headerPanel
  }

  private def createHeaderLeft(): JPanel = {
    val leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    leftPanel.setBackground(backgroundColor)

    val iconPanel = createCircularIcon("📊", primaryBlue, 60)
    val titlePanel = createTitlePanel()

    leftPanel.add(iconPanel)
    leftPanel.add(titlePanel)
    leftPanel
  }

  private def createCircularIcon(emoji: String, color: Color, iconSize: Int): JPanel = {
    new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(color)
        g2.fillOval(5, 5, iconSize - 10, iconSize - 10)
        g2.setColor(Color.WHITE)
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, iconSize / 3))
        val fm = g2.getFontMetrics
        val text = emoji
        val x = (iconSize - fm.stringWidth(text)) / 2
        val y = ((iconSize - fm.getHeight) / 2) + fm.getAscent
        g2.drawString(text, x, y)
      }
      setPreferredSize(new Dimension(iconSize, iconSize))
      setBackground(backgroundColor)
    }
  }

  private def createTitlePanel(): JPanel = {
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
    titlePanel
  }

  private def createHeaderRight(): JPanel = {
    val statusPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val compSize = getSize()
        paintShadow(g, compSize.width, compSize.height, new Color(0, 0, 0, 10), 2, 20)
        paintGradientBackground(g, compSize.width - 2, compSize.height - 2, Color.WHITE, new Color(249, 250, 251), 20)

        val g2 = g.asInstanceOf[Graphics2D]
        g2.setColor(new Color(34, 197, 94, 40))
        g2.setStroke(new BasicStroke(1.5f))
        g2.drawRoundRect(0, 0, compSize.width - 2, compSize.height - 2, 20, 20)
      }
    }
    statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 12))
    statusPanel.setPreferredSize(new Dimension(200, 45))

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
    rightPanel
  }

  private def createMetricsSection(analysis: Analysis): JPanel = {
    val metricsPanel = new JPanel(new GridLayout(1, 3, 30, 0))
    metricsPanel.setBackground(backgroundColor)
    metricsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200))

    val (recordCount, countryCount, yearRange) = getMetricsData(analysis)

    metricsPanel.add(createMetricCard("📊", "Data Records", f"$recordCount%,d", primaryBlue))
    metricsPanel.add(createMetricCard("🌍", "Global Reach", countryCount.toString, primaryGreen))
    metricsPanel.add(createMetricCard("📅", "Timeline", yearRange, primaryOrange))

    metricsPanel
  }

  private def getMetricsData(analysis: Analysis): (Int, Int, String) = {
    val recordCount = analysis.records.length
    val countryCount = if (analysis.records.nonEmpty) {
      analysis.records.map(_.country_name).distinct.length
    } else 0

    val yearRange = if (analysis.records.nonEmpty) {
      val years = analysis.records.map(_.year)
      s"${years.min}—${years.max}"
    } else "No data"

    (recordCount, countryCount, yearRange)
  }

  private def createMetricCard(emoji: String, title: String, value: String, accentColor: Color): JPanel = {
    val card = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val compSize = getSize()
        paintShadow(g, compSize.width, compSize.height, new Color(0, 0, 0, 8), 3, 16)
        paintGradientBackground(g, compSize.width - 3, compSize.height - 3, Color.WHITE, new Color(252, 253, 254), 16)

        val g2 = g.asInstanceOf[Graphics2D]
        g2.setColor(new Color(229, 231, 235, 80))
        g2.setStroke(new BasicStroke(1f))
        g2.drawRoundRect(0, 0, compSize.width - 3, compSize.height - 3, 16, 16)
      }
    }
    card.setLayout(new BorderLayout())
    card.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20))

    val iconSection = createMetricIconSection(emoji, accentColor)
    val contentPanel = createMetricContentPanel(title, value, accentColor)

    card.add(iconSection, BorderLayout.NORTH)
    card.add(contentPanel, BorderLayout.CENTER)

    card
  }

  private def createMetricIconSection(emoji: String, accentColor: Color): JPanel = {
    val iconSection = new JPanel(new BorderLayout())
    iconSection.setOpaque(false)
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
    iconSection
  }

  private def createMetricContentPanel(title: String, value: String, accentColor: Color): JPanel = {
    val contentPanel = new JPanel()
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS))
    contentPanel.setOpaque(false)
    contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0))

    val titleLabel = createCenteredLabel(title, new Font("Arial", Font.PLAIN, 14), textSecondary)
    val valueLabel = createCenteredLabel(value, new Font("Arial", Font.BOLD, 28), textPrimary)
    val progressPanel = createProgressBar(accentColor)

    contentPanel.add(titleLabel)
    contentPanel.add(Box.createVerticalStrut(8))
    contentPanel.add(valueLabel)
    contentPanel.add(Box.createVerticalStrut(12))
    contentPanel.add(progressPanel)

    contentPanel
  }

  private def createCenteredLabel(text: String, font: Font, color: Color): JLabel = {
    val label = new JLabel(text, SwingConstants.CENTER)
    label.setFont(font)
    label.setForeground(color)
    label.setAlignmentX(Component.CENTER_ALIGNMENT)
    label
  }

  private def createProgressBar(accentColor: Color): JPanel = {
    new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        val compSize = getSize()
        val gradient = new GradientPaint(0, 0, accentColor, compSize.width, 0,
          new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 120))
        g2.setPaint(gradient)
        g2.fillRoundRect(0, 12, compSize.width, 4, 2, 2)
      }
      setPreferredSize(new Dimension(200, 25))
      setOpaque(false)
      setAlignmentX(Component.CENTER_ALIGNMENT)
    }
  }

  private def createSystemStatusSection(analysis: Analysis): JPanel = {
    val statusCard = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val compSize = getSize()
        paintShadow(g, compSize.width, compSize.height, new Color(0, 0, 0, 8), 3, 20)
        paintGradientBackground(g, compSize.width - 3, compSize.height - 3, Color.WHITE, new Color(249, 250, 251), 20)

        val g2 = g.asInstanceOf[Graphics2D]
        g2.setColor(new Color(229, 231, 235, 60))
        g2.setStroke(new BasicStroke(1f))
        g2.drawRoundRect(0, 0, compSize.width - 3, compSize.height - 3, 20, 20)
      }
    }
    statusCard.setLayout(new BorderLayout())
    statusCard.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30))
    statusCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150))

    val mainPanel = createStatusMainPanel(analysis)
    val progressPanel = createStatusProgressBar()

    statusCard.add(mainPanel, BorderLayout.CENTER)
    statusCard.add(progressPanel, BorderLayout.SOUTH)
    statusCard
  }

  private def createStatusMainPanel(analysis: Analysis): JPanel = {
    val mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10))
    mainPanel.setOpaque(false)

    val iconLabel = new JLabel("✅")
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32))
    iconLabel.setForeground(successGreen)
    iconLabel.setVerticalAlignment(SwingConstants.TOP)
    iconLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 20))

    val textPanel = createStatusTextPanel(analysis)

    mainPanel.add(iconLabel)
    mainPanel.add(textPanel)
    mainPanel
  }

  private def createStatusTextPanel(analysis: Analysis): JPanel = {
    val textPanel = new JPanel()
    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS))
    textPanel.setOpaque(false)

    val (recordCount, countryCount, _) = getMetricsData(analysis)

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

    textPanel
  }

  private def createStatusProgressBar(): JPanel = {
    new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        val compSize = getSize()
        g2.setColor(new Color(34, 197, 94, 20))
        g2.fillRoundRect(0, 5, compSize.width, 3, 2, 2)

        val gradient = new GradientPaint(0, 0, successGreen, compSize.width * 0.95f, 0,
          new Color(successGreen.getRed, successGreen.getGreen, successGreen.getBlue, 180))
        g2.setPaint(gradient)
        g2.fillRoundRect(0, 5, (compSize.width * 0.95).toInt, 3, 2, 2)
      }
      setPreferredSize(new Dimension(Integer.MAX_VALUE, 15))
      setOpaque(false)
    }
  }

  private def createAnalysisSection(analysis: Analysis): JPanel = {
    val sectionPanel = new JPanel()
    sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS))
    sectionPanel.setBackground(backgroundColor)

    sectionPanel.add(createAnalysisHeader())
    sectionPanel.add(createAnalysisCards(analysis))

    sectionPanel
  }

  private def createAnalysisHeader(): JPanel = {
    val headerPanel = new JPanel()
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS))
    headerPanel.setBackground(backgroundColor)
    headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0))

    val titleLabel = createCenteredLabel("Analysis Reports", new Font("Arial", Font.BOLD, 32), textPrimary)
    val subtitleLabel = createCenteredLabel("Comprehensive Global Development Insights",
      new Font("Arial", Font.PLAIN, 16), textSecondary)
    subtitleLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0))

    headerPanel.add(titleLabel)
    headerPanel.add(subtitleLabel)
    headerPanel
  }

  private def createAnalysisCards(analysis: Analysis): JPanel = {
    val cardsPanel = new JPanel()
    cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS))
    cardsPanel.setBackground(backgroundColor)

    val cardData = Seq(
      ("01", "Life Expectancy Leadership", "Health Excellence",
        "Which country achieved the highest life expectancy standards?",
        lifeExpectancyAnswer(analysis), vibrantGreen, "🏥"),
      ("02", "Human Development Mastery", "Social Progress",
        "Which nation leads in comprehensive human development?",
        healthEducationAnswer(analysis), vibrantBlue, "🎓"),
      ("03", "Environmental Impact Assessment", "Environmental Intelligence",
        "Which country experienced the most significant forest loss?",
        forestLossAnswer(analysis), vibrantRed, "🌲")
    )

    cardData.zipWithIndex.foreach { case ((number, title, category, question, answer, color, emoji), index) =>
      cardsPanel.add(createAnalysisCard(number, title, category, question, answer, color, emoji))
      if (index < cardData.length - 1) {
        cardsPanel.add(Box.createVerticalStrut(25))
      }
    }

    cardsPanel
  }

  private def createAnalysisCard(number: String, title: String, category: String,
                                 question: String, answer: String, accentColor: Color, emoji: String): JPanel = {
    val card = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val compSize = getSize()
        paintShadow(g, compSize.width, compSize.height, new Color(0, 0, 0, 6), 4, 20)

        val bgGradient = new GradientPaint(0, 0,
          new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 8),
          0, compSize.height,
          new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 25))
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setPaint(bgGradient)
        g2.fillRoundRect(0, 0, compSize.width - 4, compSize.height - 4, 20, 20)

        g2.setColor(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 60))
        g2.setStroke(new BasicStroke(2f))
        g2.drawRoundRect(1, 1, compSize.width - 6, compSize.height - 6, 20, 20)
      }
    }

    card.setLayout(new BorderLayout())
    card.setBorder(BorderFactory.createEmptyBorder(35, 40, 35, 40))
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200))

    val leftPanel = createAnalysisCardLeft(number, title, question, answer, accentColor, emoji)
    val rightIndicator = createCircularIndicator(accentColor)
    val progressPanel = createAnalysisProgressBar(accentColor)

    val mainContainer = new JPanel(new BorderLayout())
    mainContainer.setOpaque(false)
    mainContainer.add(leftPanel, BorderLayout.CENTER)
    mainContainer.add(createRightPanel(rightIndicator), BorderLayout.EAST)

    val bottomContainer = new JPanel(new BorderLayout())
    bottomContainer.setOpaque(false)
    bottomContainer.add(progressPanel, BorderLayout.SOUTH)

    card.add(mainContainer, BorderLayout.CENTER)
    card.add(bottomContainer, BorderLayout.SOUTH)
    card
  }

  private def createAnalysisCardLeft(number: String, title: String, question: String,
                                     answer: String, accentColor: Color, emoji: String): JPanel = {
    val leftPanel = new JPanel(new BorderLayout())
    leftPanel.setOpaque(false)

    val topPanel = createAnalysisTopPanel(number, title, accentColor, emoji)
    val middlePanel = createAnalysisQuestionPanel(question)
    val bottomPanel = createAnalysisAnswerPanel(answer, accentColor)

    leftPanel.add(topPanel, BorderLayout.NORTH)
    leftPanel.add(middlePanel, BorderLayout.CENTER)
    leftPanel.add(bottomPanel, BorderLayout.SOUTH)

    leftPanel
  }

  private def createAnalysisTopPanel(number: String, title: String, accentColor: Color, emoji: String): JPanel = {
    val topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    topPanel.setOpaque(false)

    val numberBadge = createNumberBadge(number, accentColor)
    val emojiPanel = createEmojiPanel(emoji, accentColor)
    val titleLabel = createAnalysisTitleLabel(title, accentColor)

    topPanel.add(numberBadge)
    topPanel.add(Box.createHorizontalStrut(20))
    topPanel.add(emojiPanel)
    topPanel.add(titleLabel)

    topPanel
  }

  private def createNumberBadge(number: String, accentColor: Color): JPanel = {
    new JPanel() {
      private val badgeSize = 54
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        val gradient = new GradientPaint(0, 0, accentColor, badgeSize, badgeSize,
          new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 180))
        g2.setPaint(gradient)
        g2.fillOval(2, 2, 50, 50)

        g2.setColor(new Color(255, 255, 255, 40))
        g2.fillOval(8, 8, 38, 38)

        g2.setColor(Color.WHITE)
        g2.setFont(new Font("Arial", Font.BOLD, 20))
        val fm = g2.getFontMetrics
        val x = (badgeSize - fm.stringWidth(number)) / 2
        val y = ((badgeSize - fm.getHeight) / 2) + fm.getAscent
        g2.drawString(number, x, y)
      }
      setPreferredSize(new Dimension(badgeSize, badgeSize))
      setOpaque(false)
    }
  }

  private def createEmojiPanel(emoji: String, accentColor: Color): JPanel = {
    val panel = new JPanel()
    panel.setLayout(new BorderLayout())
    val panelSize = 54
    panel.setPreferredSize(new Dimension(panelSize, panelSize))
    panel.setOpaque(false)

    // Create background panel with gradient
    val backgroundPanel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        val gradient = new GradientPaint(0, 0,
          new Color(255, 255, 255, 120), panelSize, panelSize,
          new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 40))
        g2.setPaint(gradient)
        g2.fillOval(0, 0, panelSize, panelSize)
      }
    }
    backgroundPanel.setLayout(new BorderLayout())
    backgroundPanel.setOpaque(false)

    // Create emoji label
    val emojiLabel = new JLabel(emoji, SwingConstants.CENTER)
    emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28))
    emojiLabel.setHorizontalAlignment(SwingConstants.CENTER)
    emojiLabel.setVerticalAlignment(SwingConstants.CENTER)
    emojiLabel.setOpaque(false)

    backgroundPanel.add(emojiLabel, BorderLayout.CENTER)
    panel.add(backgroundPanel, BorderLayout.CENTER)

    panel
  }

  private def createAnalysisTitleLabel(title: String, accentColor: Color): JLabel = {
    val titleLabel = new JLabel(title)
    titleLabel.setFont(new Font("Arial", Font.BOLD, 26))
    titleLabel.setForeground(new Color(
      Math.max(0, Math.min(255, accentColor.getRed - 20)),
      Math.max(0, Math.min(255, accentColor.getGreen - 20)),
      Math.max(0, Math.min(255, accentColor.getBlue - 20))
    ))
    titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 25, 0, 0))
    titleLabel
  }

  private def createAnalysisQuestionPanel(question: String): JPanel = {
    val middlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    middlePanel.setOpaque(false)
    middlePanel.setBorder(BorderFactory.createEmptyBorder(18, 75, 0, 0))

    val questionLabel = new JLabel(question)
    questionLabel.setFont(new Font("Arial", Font.PLAIN, 17))
    questionLabel.setForeground(new Color(60, 60, 60))

    middlePanel.add(questionLabel)
    middlePanel
  }

  private def createAnalysisAnswerPanel(answer: String, accentColor: Color): JPanel = {
    val bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0))
    bottomPanel.setOpaque(false)
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 75, 0, 0))

    val answerLabel = new JLabel(answer)
    answerLabel.setFont(new Font("Arial", Font.BOLD, 16))
    answerLabel.setForeground(accentColor)

    bottomPanel.add(answerLabel)
    bottomPanel
  }

  private def createCircularIndicator(accentColor: Color): JPanel = {
    new JPanel() {
      private val indicatorSize = 40
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Soft shadow
        g2.setColor(new Color(0, 0, 0, 15))
        g2.fillOval(2, 2, 36, 36)

        // Main circle with gradient
        val gradient = new GradientPaint(0, 0, accentColor, indicatorSize, indicatorSize,
          new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 160))
        g2.setPaint(gradient)
        g2.fillOval(0, 0, 36, 36)

        // Inner highlight
        g2.setColor(new Color(255, 255, 255, 60))
        g2.fillOval(6, 6, 24, 24)

        // Center dot
        g2.setColor(Color.WHITE)
        g2.fillOval(14, 14, 8, 8)
      }
      setPreferredSize(new Dimension(indicatorSize, indicatorSize))
      setOpaque(false)
    }
  }

  private def createRightPanel(indicator: JPanel): JPanel = {
    val rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10))
    rightPanel.setOpaque(false)
    rightPanel.add(indicator)
    rightPanel
  }

  private def createAnalysisProgressBar(accentColor: Color): JPanel = {
    new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Background track
        g2.setColor(new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 20))
        g2.fillRoundRect(75, 10, 420, 8, 4, 4)

        // Progress gradient
        val gradient = new GradientPaint(75, 0, accentColor, 415, 0,
          new Color(accentColor.getRed, accentColor.getGreen, accentColor.getBlue, 120))
        g2.setPaint(gradient)
        g2.fillRoundRect(75, 10, 340, 8, 4, 4)

        // Subtle highlight
        g2.setColor(new Color(255, 255, 255, 80))
        g2.fillRoundRect(75, 10, 340, 3, 2, 2)
      }
      setPreferredSize(new Dimension(520, 30))
      setOpaque(false)
    }
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