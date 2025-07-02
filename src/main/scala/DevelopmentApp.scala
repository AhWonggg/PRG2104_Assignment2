import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class DevelopmentApp extends Application {
  override def start(primaryStage: Stage): Unit = {
    val data = CountryDataLoader.loadData("src//main//resources//Global_Development_Indicators_2000_2020.csv")
    val analysis = new Analysis(data)
    val root = new DevelopmentUI(analysis).generateUI()
    val scene = new Scene(root, 800, 400)
    primaryStage.setTitle("Global Development Viewer")
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}

object DevelopmentUIApplication {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[DevelopmentApp], args: _*)
  }
}
