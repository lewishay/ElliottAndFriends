import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.Includes._
import scalafx.stage.Stage

/**
  * Created by Administrator on 20/06/2017.
  */
object GUI extends JFXApp {
  val homeStage = new JFXApp.PrimaryStage() {
    outer =>
    title = "Home"
    scene = new Scene(1280, 720) {
      val elliotBanner = new Image("http://i.imgur.com/HjrMDyZ.png"); val bannerView = new ImageView(elliotBanner)
      val gamesButton = new Button("Games"); gamesButton.setPrefSize(380, 200); gamesButton.layoutX = 35
      gamesButton.layoutY = 450; gamesButton.setStyle("-fx-font-size: 30pt")
      gamesButton.onAction = handle {
        gamesStage.show(); outer.close()
      }
      val hardwareButton = new Button("Hardware"); hardwareButton.setPrefSize(380, 200); hardwareButton.layoutX = 450
      hardwareButton.layoutY = 450; hardwareButton.setStyle("-fx-font-size: 30pt")
      hardwareButton.onAction = handle {
        hardwareStage.show(); outer.close()
      }
      val miscButton = new Button("Misc."); miscButton.setPrefSize(380, 200); miscButton.layoutX = 865
      miscButton.layoutY = 450; miscButton.setStyle("-fx-font-size: 30pt")
      miscButton.onAction = handle {
        miscStage.show(); outer.close()
      }
      val adminButton = new Button("Admin"); adminButton.setPrefSize(250, 100); adminButton.layoutX = 950
      adminButton.layoutY = 200; adminButton.setStyle("-fx-font-size: 25pt")
      adminButton.onAction = handle {
        loginStage.show(); outer.close()
      }
      val searchField = new TextField(); searchField.layoutX = 50; searchField.layoutY = 350
      searchField.setPrefSize(900, 50); searchField.setStyle("-fx-font-size: 20pt")
      val searchButton = new Button("Search"); searchButton.setPrefSize(250, 50); searchButton.layoutX = 960
      searchButton.layoutY = 350; searchButton.setStyle("-fx-font-size: 20pt")
      searchButton.onAction = handle {
        //search for searchField.text with the Store method
        searchStage.show(); outer.close()
      }
      content = List(bannerView, gamesButton, hardwareButton, miscButton, adminButton, searchField, searchButton)
    }
  }

  val searchStage: Stage = new Stage() {
    outer =>
    title = "Search results"
    scene = new Scene(1280, 720) {
      val resultsText = new Label("Search Results"); resultsText.setStyle("-fx-font-size: 30pt");
      resultsText.layoutX = 20; resultsText.layoutY = 20
      val resultsBox = new TextArea() //need to populate with search results
      resultsBox.setPrefSize(900, 600); resultsBox.layoutX = 20; resultsBox.layoutY = 100
      resultsBox.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 150); homeButton.layoutX = 950
      homeButton.layoutY = 500; homeButton.setStyle("-fx-font-size: 25pt")
      val basketButton = new Button("Add to\nbasket"); basketButton.setPrefSize(300, 150); basketButton.layoutX = 950
      basketButton.layoutY = 150; basketButton.setStyle("-fx-font-size: 25pt")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      basketButton.onAction = handle {
        //method to add item to basket
      }
      content = List(resultsText, resultsBox, homeButton, basketButton)
    }
  }

  val gamesStage: Stage = new Stage() {
    outer =>
    title = "Games"
    scene = new Scene(1280, 720) {
      val gameText = new Label("Games"); gameText.setStyle("-fx-font-size: 30pt");
      gameText.layoutX = 20; gameText.layoutY = 20
      val gameList = new TextArea() //need to populate with game titles sorted alphabetically
      gameList.setPrefSize(900, 600); gameList.layoutX = 20; gameList.layoutY = 100
      gameList.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 150); homeButton.layoutX = 950
      homeButton.layoutY = 500; homeButton.setStyle("-fx-font-size: 25pt")
      val basketButton = new Button("Add to\nbasket"); basketButton.setPrefSize(300, 150); basketButton.layoutX = 950
      basketButton.layoutY = 150; basketButton.setStyle("-fx-font-size: 25pt")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      basketButton.onAction = handle {
        //method to add item to basket
      }
      content = List(gameText, gameList, homeButton, basketButton)
    }
  }

  val hardwareStage: Stage = new Stage() {
    outer =>
    title = "Hardware"
    scene = new Scene(1280, 720) {
      val hardwareText = new Label("Hardware"); hardwareText.setStyle("-fx-font-size: 30pt");
      hardwareText.layoutX = 20; hardwareText.layoutY = 20
      val hardwareList = new TextArea() //need to populate with hardware titles sorted alphabetically
      hardwareList.setPrefSize(900, 600); hardwareList.layoutX = 20; hardwareList.layoutY = 100
      hardwareList.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 150); homeButton.layoutX = 950
      homeButton.layoutY = 500; homeButton.setStyle("-fx-font-size: 25pt")
      val basketButton = new Button("Add to\nbasket"); basketButton.setPrefSize(300, 150); basketButton.layoutX = 950
      basketButton.layoutY = 150; basketButton.setStyle("-fx-font-size: 25pt")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      basketButton.onAction = handle {
        //method to add item to basket
      }
      content = List(hardwareText, hardwareList, homeButton, basketButton)
    }
  }

  val miscStage: Stage = new Stage() {
    outer =>
    title = "Misc. items"
    scene = new Scene(1280, 720) {
      val miscText = new Label("Miscellaneous items"); miscText.setStyle("-fx-font-size: 30pt");
      miscText.layoutX = 20; miscText.layoutY = 20
      val miscList = new TextArea() //need to populate with misc items sorted alphabetically
      miscList.setPrefSize(900, 600); miscList.layoutX = 20; miscList.layoutY = 100
      miscList.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 150); homeButton.layoutX = 950
      homeButton.layoutY = 500; homeButton.setStyle("-fx-font-size: 25pt")
      val basketButton = new Button("Add to\nbasket"); basketButton.setPrefSize(300, 150); basketButton.layoutX = 950
      basketButton.layoutY = 150; basketButton.setStyle("-fx-font-size: 25pt")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      basketButton.onAction = handle {
        //method to add item to basket
      }
      content = List(miscText, miscList, homeButton, basketButton)
    }
  }

  val loginStage = new Stage() {
    title = "t"
    scene = new Scene(1280, 720) {
      val coolButton = new Button("Cool"); coolButton.setPrefSize(300, 300); coolButton.layoutX = 300
      coolButton.layoutY = 300
      content = coolButton
    }
  }
}
