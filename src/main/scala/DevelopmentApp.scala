import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.stage.Stage

object DevelopmentApp extends JFXApp3 {
  override def start(): Unit = {
    // Load data using the updated Loader
    val inputCsv = "src/main/resources/Global_Development_Indicators_2000_2020.csv"
    val data = Loader.loadData(inputCsv)
    val analysis = new Analysis(data)
    
    // Create the UI
    val ui = new DevelopmentUI(analysis)
    
    // Configure the window
    stage = new JFXApp3.PrimaryStage {
      title = "Global Development Data Analysis - GUI"
      scene = new Scene(ui.generateUI(), 900, 600)
      resizable = true
    }
  }
}
