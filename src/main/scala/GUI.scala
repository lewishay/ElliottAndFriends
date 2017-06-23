import java.time.LocalDate
import javafx.scene.control.Alert.AlertType

import scala.collection.mutable.ArrayBuffer
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.GridPane
import scalafx.scene.paint.Color
import scalafx.stage.Stage

/**
  * Created by Lewis on 20/06/2017.
  */
object GUI extends JFXApp {
  val store = new Store("Manchester store", new ArrayBuffer[Stock], new ArrayBuffer[Sale])
  var searchResults = new ArrayBuffer[Stock]
  val searchResultsList = new ListView[String]
  val gamesList = new ListView[String]
  val hardwareList = new ListView[String]
  val miscList = new ListView[String]
  val customerList = new ListView[String]
  val employeeList = new ListView[String]
  val basketList = new ListView[String]
  val searchField = new TextField()

  val loginStage: Stage = new JFXApp.PrimaryStage() {
    outer =>
    title = "Login"
    scene = new Scene(440, 240) {
      fill = Color.web("f7eaea")
      val usernameField = new TextField(); usernameField.setPrefSize(400, 50); usernameField.layoutX = 20
      usernameField.layoutY = 30; usernameField.setStyle("-fx-font-size: 18pt")
      val passwordField = new PasswordField(); passwordField.setPrefSize(400, 50); passwordField.layoutX = 20
      passwordField.layoutY = 105; passwordField.setStyle("-fx-font-size: 18pt")
      val usernameLabel = new Label("Staff ID"); usernameLabel.setStyle("-fx-font-size: 10pt")
      usernameLabel.layoutX = 20; usernameLabel.layoutY = 10
      val passwordLabel = new Label("Password"); passwordLabel.setStyle("-fx-font-size: 10pt")
      passwordLabel.layoutX = 20; passwordLabel.layoutY = 85
      val loginButton = new Button("Login"); loginButton.setPrefSize(400, 50); loginButton.layoutX = 20
      loginButton.layoutY = 170; loginButton.setStyle("-fx-font-size: 18pt; -fx-background-color: #f9afac")
      loginButton.onAction = handle {
        usernameField match {
          case _ if(store.login(usernameField.getText, passwordField.getText)) => {homeStage.show(); outer.close()}
          case _ => new Alert (AlertType.ERROR, "Not a valid login").showAndWait()
        }
      }
      content = List(loginButton, usernameField, passwordField, usernameLabel, passwordLabel)
    }
  }

  val homeStage = new Stage() {
    outer =>
    title = "Home"
    scene = new Scene(1280, 720) {
      fill = Color.web("#5b5853")
      val elliotBanner = new Image("elliottbannerdark.png"); val bannerView = new ImageView(elliotBanner)
      bannerView.layoutY = 20
      val gamesButton = new Button("Games"); gamesButton.setPrefSize(380, 200); gamesButton.layoutX = 35
      gamesButton.layoutY = 450; gamesButton.setStyle("-fx-font-size: 30pt; -fx-background-color: #d6bcf2")
      gamesButton.onAction = handle {
        gamesList.getItems.clear
        store.loadStock().filter(x => x.typeOfStock == "Game").foreach(x => gamesList.getItems.add(x.productName))
        gamesStage.show(); outer.close()
      }
      val hardwareButton = new Button("Hardware"); hardwareButton.setPrefSize(380, 200); hardwareButton.layoutX = 450
      hardwareButton.layoutY = 450; hardwareButton.setStyle("-fx-font-size: 30pt; -fx-background-color: #d6bcf2")
      hardwareButton.onAction = handle {
        hardwareList.getItems.clear
        store.loadStock().filter(x => x.typeOfStock == "Hardware").foreach(x => hardwareList.getItems.
                                                                                                  add(x.productName))
        hardwareStage.show(); outer.close()
      }
      val miscButton = new Button("Misc."); miscButton.setPrefSize(380, 200); miscButton.layoutX = 865
      miscButton.layoutY = 450; miscButton.setStyle("-fx-font-size: 30pt; -fx-background-color: #d6bcf2")
      miscButton.onAction = handle {
        miscList.getItems.clear
        store.loadStock().filter(x => x.typeOfStock == "Misc").foreach(x => miscList.getItems.add(x.productName))
        miscStage.show(); outer.close()
      }
      val financeButton = new Button("Finance"); financeButton.setPrefSize(250, 100); financeButton.layoutX = 1000
      financeButton.layoutY = 300; financeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #ffcd96")
      financeButton.onAction = handle {
        financeButton match {
          case _ if(store.loggedInStaff.jobTitle == "Manager") => {financeStage.show(); outer.close()}
          case _ => new Alert (AlertType.ERROR, "You do not have permission to view this.").showAndWait ()
        }
      }
      val customerButton = new Button("Customers"); customerButton.setPrefSize(250, 100); customerButton.layoutX = 30
      customerButton.layoutY = 300; customerButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      customerButton.onAction = handle {
        customerList.getItems.clear
        store.loadCustomers().foreach(x => customerList.getItems.add(x.name))
        customerStage.show(); outer.close()
      }
      val employeeButton = new Button("Employees"); employeeButton.setPrefSize(250, 100); employeeButton.layoutX = 300
      employeeButton.layoutY = 300; employeeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      employeeButton.onAction = handle {
        employeeList.getItems.clear
        store.loadStaff().foreach(x => employeeList.getItems.add(x.firstName + " " + x.surname))
        employeeStage.show(); outer.close()
      }
      val basketButton = new Button("Basket"); basketButton.setPrefSize(300, 100); basketButton.layoutX = 620
      basketButton.layoutY = 300; basketButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #f7ed9e")
      basketButton.onAction = handle {
        basketList.getItems.clear
        store.basket.foreach(x => basketList.getItems.add(x.productName))
        basketStage.show(); outer.close()
      }
      searchField.layoutX = 50; searchField.layoutY = 200
      searchField.setPrefSize(900, 50); searchField.setStyle("-fx-font-size: 20pt")
      val searchButton = new Button("Search"); searchButton.setPrefSize(250, 50); searchButton.layoutX = 960
      searchButton.layoutY = 200; searchButton.setStyle("-fx-font-size: 20pt; -fx-background-color: #b6edcb")
      searchButton.onAction = handle {
        searchResultsList.getItems.clear
        searchResults = store.search(searchField.getText)
        searchResults.foreach(x => searchResultsList.getItems.add(x.productName))
        searchStage.show(); outer.close()
      }
      val logoutButton = new Button("Logout"); logoutButton.setPrefSize(100, 10); logoutButton.layoutX = 1150
      logoutButton.layoutY = 670; logoutButton.setStyle("-fx-font-size: 10pt; -fx-background-color: #f9afac")
      logoutButton.onAction = handle {
        store.loggedInStaff = null; loginStage.show(); outer.close()
      }
      content = List(bannerView, gamesButton, hardwareButton, miscButton, financeButton, searchField, searchButton,
                    customerButton, employeeButton, basketButton, logoutButton)
    }
  }

  val searchStage: Stage = new Stage() {
    outer =>
    title = "Search results"
    scene = new Scene(1280, 720) {
      fill = Color.web("#f2fff7")
      val resultsText = new Label("Search Results"); resultsText.setStyle("-fx-font-size: 30pt");
      resultsText.layoutX = 20; resultsText.layoutY = 20
      searchResultsList.layoutX = 20; searchResultsList.layoutY = 100; searchResultsList.setStyle("-fx-font-size: 20pt")
      searchResultsList.setPrefSize(900, 600); searchResultsList.setEditable(false)
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 150); homeButton.layoutX = 950
      homeButton.layoutY = 550; homeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #b6edcb")
      val basketButton = new Button("Add to\nbasket"); basketButton.setPrefSize(300, 150); basketButton.layoutX = 950
      basketButton.layoutY = 360; basketButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #b6edcb")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      basketButton.onAction = handle {
        for(item <- store.loadStock()) {
          if(item.productName == searchResultsList.getSelectionModel.getSelectedItem) store.basket += item
        }
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 100; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #b6edcb")
      editButton.onAction = handle {
        var theGame: Stock = null
        for(item <- store.loadStock()) {
          if(item.productName == gamesList.getSelectionModel.getSelectedItem) theGame = item
        }
        val dialog = new Dialog[Stock]() {
          initOwner(gamesStage)
          title = "Edit game details"
          headerText = "Edit game details"
        }
        val salePrice = new TextField(); val costPerUnit = new TextField(); val quantity = new TextField()
        val typeOfStock = new TextField(); val productName = new TextField(); val info = new TextField()
        val releaseDate = new DatePicker()
        val accept = new ButtonType("Update", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        salePrice.setText(theGame.salePrice.toString); costPerUnit.setText(theGame.costPerUnit.toString)
        quantity.setText(theGame.quantity.toString); typeOfStock.setText(theGame.typeOfStock)
        productName.setText(theGame.productName); info.setText(theGame.info); releaseDate.setValue(theGame.releaseDate)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Sale Price:"), 0, 0); add(salePrice, 1, 0)
          add(new Label("Cost Per Unit:"), 0, 1); add(costPerUnit, 1, 1)
          add(new Label("Quantity"), 0, 2); add(quantity, 1, 2)
          add(new Label("Type of Stock"), 0, 3); add(typeOfStock, 1, 3)
          add(new Label("Product name"), 0, 4); add(productName, 1, 4)
          add(new Label("Info"), 0, 5); add(info, 1, 5)
          add(new Label("Release date"), 0, 6); add(releaseDate, 1, 6)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createStock(99, salePrice.getText.toDouble, costPerUnit.getText.toDouble,
            quantity.getText.toInt, typeOfStock.getText, productName.getText, info.getText, releaseDate.getValue)
            gamesList.getItems.clear()
            store.delete(theGame)
            store.loadStock().filter(x => x.typeOfStock == "Game").foreach(x => gamesList.getItems.add(x.productName))
          case _ =>
        }
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 220; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #b6edcb")
      deleteButton.onAction = handle {
        for(item <- store.loadStock()) {
          if(item.productName == searchResultsList.getSelectionModel.getSelectedItem) store.delete(item)
        }
        searchResultsList.getItems.clear
        searchResults = store.search(searchField.getText)
        searchResults.foreach(x => searchResultsList.getItems.add(x.productName))
      }
      content = List(resultsText, searchResultsList, homeButton, basketButton, editButton, deleteButton)
    }
  }

  val gamesStage: Stage = new Stage() {
    outer =>
    title = "Games"
    scene = new Scene(1280, 720) {
      fill = Color.web("#f7f2ff")
      val gameText = new Label("Games"); gameText.setStyle("-fx-font-size: 30pt")
      gameText.layoutX = 20; gameText.layoutY = 20
      gamesList.setPrefSize(900, 600); gamesList.layoutX = 20; gamesList.layoutY = 100
      gamesList.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 100); homeButton.layoutX = 950
      homeButton.layoutY = 600; homeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      val basketButton = new Button("Add to\nbasket"); basketButton.setPrefSize(300, 150); basketButton.layoutX = 950
      basketButton.layoutY = 420; basketButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      basketButton.onAction = handle {
        for(item <- store.loadStock()) {
          if(item.productName == gamesList.getSelectionModel.getSelectedItem) store.basket += item
        }
      }
      val createButton = new Button("Add"); createButton.setPrefSize(300, 100); createButton.layoutX = 950
      createButton.layoutY = 50; createButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      createButton.onAction = handle {
        val dialog = new Dialog[Stock]() {
          initOwner(gamesStage)
          title = "Add game"
          headerText = "Enter game details"
        }
        val salePrice = new TextField(); val costPerUnit = new TextField(); val quantity = new TextField()
        val typeOfStock = new TextField(); val productName = new TextField(); val info = new TextField()
        val releaseDate = new DatePicker()
        val accept = new ButtonType("Accept", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Sale Price:"), 0, 0); add(salePrice, 1, 0)
          add(new Label("Cost Per Unit:"), 0, 1); add(costPerUnit, 1, 1)
          add(new Label("Quantity"), 0, 2); add(quantity, 1, 2)
          add(new Label("Type of Stock"), 0, 3); add(typeOfStock, 1, 3)
          add(new Label("Product name"), 0, 4); add(productName, 1, 4)
          add(new Label("Info"), 0, 5); add(info, 1, 5)
          add(new Label("Release date"), 0, 6); add(releaseDate, 1, 6)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createStock(99, salePrice.getText.toDouble, costPerUnit.getText.toDouble,
            quantity.getText.toInt, typeOfStock.getText, productName.getText, info.getText, releaseDate.getValue)
            gamesList.getItems.clear()
            store.loadStock().filter(x => x.typeOfStock == "Game").foreach(x => gamesList.getItems.add(x.productName))
          case _ =>
        }
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 170; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      editButton.onAction = handle {
        var theGame: Stock = null
        for(item <- store.loadStock()) {
          if(item.productName == gamesList.getSelectionModel.getSelectedItem) theGame = item
        }
        val dialog = new Dialog[Stock]() {
          initOwner(gamesStage)
          title = "Edit game details"
          headerText = "Edit game details"
        }
        val salePrice = new TextField(); val costPerUnit = new TextField(); val quantity = new TextField()
        val typeOfStock = new TextField(); val productName = new TextField(); val info = new TextField()
        val releaseDate = new DatePicker()
        val accept = new ButtonType("Update", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        salePrice.setText(theGame.salePrice.toString); costPerUnit.setText(theGame.costPerUnit.toString)
        quantity.setText(theGame.quantity.toString); typeOfStock.setText(theGame.typeOfStock)
        productName.setText(theGame.productName); info.setText(theGame.info); releaseDate.setValue(theGame.releaseDate)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Sale Price:"), 0, 0); add(salePrice, 1, 0)
          add(new Label("Cost Per Unit:"), 0, 1); add(costPerUnit, 1, 1)
          add(new Label("Quantity"), 0, 2); add(quantity, 1, 2)
          add(new Label("Type of Stock"), 0, 3); add(typeOfStock, 1, 3)
          add(new Label("Product name"), 0, 4); add(productName, 1, 4)
          add(new Label("Info"), 0, 5); add(info, 1, 5)
          add(new Label("Release date"), 0, 6); add(releaseDate, 1, 6)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createStock(99, salePrice.getText.toDouble, costPerUnit.getText.toDouble,
            quantity.getText.toInt, typeOfStock.getText, productName.getText, info.getText, releaseDate.getValue)
            gamesList.getItems.clear()
            store.delete(theGame)
            store.loadStock().filter(x => x.typeOfStock == "Game").foreach(x => gamesList.getItems.add(x.productName))
          case _ =>
        }
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 290; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      deleteButton.onAction = handle {
        for(item <- store.loadStock()) {
          if(item.productName == gamesList.getSelectionModel.getSelectedItem) store.delete(item)
        }
        gamesList.getItems.clear()
        store.loadStock().filter(x => x.typeOfStock == "Game").foreach(x => gamesList.getItems.add(x.productName))
      }
      val preorderButton = new Button("View preorders"); preorderButton.setPrefSize(300, 50)
      preorderButton.layoutX = 550; preorderButton.layoutY = 10
      preorderButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      preorderButton.onAction = handle {
        gamesList.getItems.clear
        store.loadStock().filter(x => x.typeOfStock == "Game").filter(x => x.releaseDate.isAfter(LocalDate.now())).
          foreach(x => gamesList.getItems.add(x.productName))
      }
      content = List(gameText, gamesList, homeButton, basketButton, createButton, editButton, deleteButton,
                    preorderButton)
    }
  }

  val hardwareStage: Stage = new Stage() {
    outer =>
    title = "Hardware"
    scene = new Scene(1280, 720) {
      fill = Color.web("#f7f2ff")
      val hardwareText = new Label("Hardware"); hardwareText.setStyle("-fx-font-size: 30pt");
      hardwareText.layoutX = 20; hardwareText.layoutY = 20
      hardwareList.setPrefSize(900, 600); hardwareList.layoutX = 20; hardwareList.layoutY = 100
      hardwareList.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 100); homeButton.layoutX = 950
      homeButton.layoutY = 600; homeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      val basketButton = new Button("Add to\nbasket"); basketButton.setPrefSize(300, 150); basketButton.layoutX = 950
      basketButton.layoutY = 420; basketButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      basketButton.onAction = handle {
        for(item <- store.loadStock()) {
          if(item.productName == hardwareList.getSelectionModel.getSelectedItem) store.basket += item
        }
      }
      val createButton = new Button("Add"); createButton.setPrefSize(300, 100); createButton.layoutX = 950
      createButton.layoutY = 50; createButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      createButton.onAction = handle {
        val dialog = new Dialog[Stock]() {
          initOwner(hardwareStage)
          title = "Add game"
          headerText = "Enter hardware details"
        }
        val salePrice = new TextField(); val costPerUnit = new TextField(); val quantity = new TextField()
        val typeOfStock = new TextField(); val productName = new TextField(); val info = new TextField()
        val releaseDate = new DatePicker()
        val accept = new ButtonType("Accept", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Sale Price:"), 0, 0); add(salePrice, 1, 0)
          add(new Label("Cost Per Unit:"), 0, 1); add(costPerUnit, 1, 1)
          add(new Label("Quantity"), 0, 2); add(quantity, 1, 2)
          add(new Label("Type of Stock"), 0, 3); add(typeOfStock, 1, 3)
          add(new Label("Product name"), 0, 4); add(productName, 1, 4)
          add(new Label("Info"), 0, 5); add(info, 1, 5)
          add(new Label("Release date"), 0, 6); add(releaseDate, 1, 6)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createStock(99, salePrice.getText.toDouble, costPerUnit.getText.toDouble,
            quantity.getText.toInt, typeOfStock.getText, productName.getText, info.getText, releaseDate.getValue)
            hardwareList.getItems.clear()
            store.loadStock().filter(x => x.typeOfStock == "Hardware").foreach(x => hardwareList.getItems.
                                                                                                add(x.productName))
          case _ =>
        }
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 170; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      editButton.onAction = handle {
        var theHardware: Stock = null
        for(item <- store.loadStock()) {
          if(item.productName == hardwareList.getSelectionModel.getSelectedItem) theHardware = item
        }
        val dialog = new Dialog[Stock]() {
          initOwner(hardwareStage)
          title = "Edit hardware details"
          headerText = "Edit hardware details"
        }
        val salePrice = new TextField(); val costPerUnit = new TextField(); val quantity = new TextField()
        val typeOfStock = new TextField(); val productName = new TextField(); val info = new TextField()
        val releaseDate = new DatePicker()
        val accept = new ButtonType("Update", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        salePrice.setText(theHardware.salePrice.toString); costPerUnit.setText(theHardware.costPerUnit.toString)
        quantity.setText(theHardware.quantity.toString); typeOfStock.setText(theHardware.typeOfStock)
        productName.setText(theHardware.productName); info.setText(theHardware.info);
        releaseDate.setValue(theHardware.releaseDate)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Sale Price:"), 0, 0); add(salePrice, 1, 0)
          add(new Label("Cost Per Unit:"), 0, 1); add(costPerUnit, 1, 1)
          add(new Label("Quantity"), 0, 2); add(quantity, 1, 2)
          add(new Label("Type of Stock"), 0, 3); add(typeOfStock, 1, 3)
          add(new Label("Product name"), 0, 4); add(productName, 1, 4)
          add(new Label("Info"), 0, 5); add(info, 1, 5)
          add(new Label("Release Date"), 0, 6); add(releaseDate, 1, 6)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createStock(99, salePrice.getText.toDouble, costPerUnit.getText.toDouble,
            quantity.getText.toInt, typeOfStock.getText, productName.getText, info.getText, releaseDate.getValue)
            hardwareList.getItems.clear()
            store.delete(theHardware)
            store.loadStock().filter(x => x.typeOfStock == "Hardware").foreach(x => hardwareList.getItems.
                                                                                                add(x.productName))
          case _ =>
        }
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 290; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      deleteButton.onAction = handle {
        for(item <- store.loadStock()) {
          if(item.productName == hardwareList.getSelectionModel.getSelectedItem) store.delete(item)
        }
        hardwareList.getItems.clear
        store.loadStock().filter(x => x.typeOfStock == "Hardware").foreach(x => hardwareList.getItems.
                                                                                                add(x.productName))
      }
      content = List(hardwareText, hardwareList, homeButton, basketButton, createButton, editButton, deleteButton)
    }
  }

  val miscStage: Stage = new Stage() {
    outer =>
    title = "Misc. items"
    scene = new Scene(1280, 720) {
      fill = Color.web("#f7f2ff")
      val miscText = new Label("Miscellaneous items"); miscText.setStyle("-fx-font-size: 30pt");
      miscText.layoutX = 20; miscText.layoutY = 20
      miscList.setPrefSize(900, 600); miscList.layoutX = 20; miscList.layoutY = 100
      miscList.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 100); homeButton.layoutX = 950
      homeButton.layoutY = 600; homeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      val basketButton = new Button("Add to\nbasket"); basketButton.setPrefSize(300, 150); basketButton.layoutX = 950
      basketButton.layoutY = 420; basketButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      basketButton.onAction = handle {
        for(item <- store.loadStock()) {
          if(item.productName == miscList.getSelectionModel.getSelectedItem) store.basket += item
        }
      }
      val createButton = new Button("Add"); createButton.setPrefSize(300, 100); createButton.layoutX = 950
      createButton.layoutY = 50; createButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      createButton.onAction = handle {
        val dialog = new Dialog[Stock]() {
          initOwner(miscStage)
          title = "Add game"
          headerText = "Enter item details"
        }
        val salePrice = new TextField(); val costPerUnit = new TextField(); val quantity = new TextField()
        val typeOfStock = new TextField(); val productName = new TextField(); val info = new TextField()
        val releaseDate = new DatePicker()
        val accept = new ButtonType("Accept", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Sale Price:"), 0, 0); add(salePrice, 1, 0)
          add(new Label("Cost Per Unit:"), 0, 1); add(costPerUnit, 1, 1)
          add(new Label("Quantity"), 0, 2); add(quantity, 1, 2)
          add(new Label("Type of Stock"), 0, 3); add(typeOfStock, 1, 3)
          add(new Label("Product name"), 0, 4); add(productName, 1, 4)
          add(new Label("Info"), 0, 5); add(info, 1, 5)
          add(new Label("Release Date"), 0, 6); add(releaseDate, 1, 6)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createStock(99, salePrice.getText.toDouble, costPerUnit.getText.toDouble,
            quantity.getText.toInt, typeOfStock.getText, productName.getText, info.getText, releaseDate.getValue)
            miscList.getItems.clear()
            store.loadStock().filter(x => x.typeOfStock == "Misc").foreach(x => miscList.getItems.add(x.productName))
          case _ =>
        }
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 170; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      editButton.onAction = handle {
        var theMisc: Stock = null
        for(item <- store.loadStock()) {
          if(item.productName == miscList.getSelectionModel.getSelectedItem) theMisc = item
        }
        val dialog = new Dialog[Stock]() {
          initOwner(miscStage)
          title = "Edit item details"
          headerText = "Edit item details"
        }
        val salePrice = new TextField(); val costPerUnit = new TextField(); val quantity = new TextField()
        val typeOfStock = new TextField(); val productName = new TextField(); val info = new TextField()
        val releaseDate = new DatePicker()
        val accept = new ButtonType("Update", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        salePrice.setText(theMisc.salePrice.toString); costPerUnit.setText(theMisc.costPerUnit.toString)
        quantity.setText(theMisc.quantity.toString); typeOfStock.setText(theMisc.typeOfStock)
        productName.setText(theMisc.productName); info.setText(theMisc.info); releaseDate.setValue(theMisc.releaseDate)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Sale Price:"), 0, 0); add(salePrice, 1, 0)
          add(new Label("Cost Per Unit:"), 0, 1); add(costPerUnit, 1, 1)
          add(new Label("Quantity"), 0, 2); add(quantity, 1, 2)
          add(new Label("Type of Stock"), 0, 3); add(typeOfStock, 1, 3)
          add(new Label("Product name"), 0, 4); add(productName, 1, 4)
          add(new Label("Info"), 0, 5); add(info, 1, 5)
          add(new Label("Release Date"), 0, 6); add(releaseDate, 1, 6)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createStock(99, salePrice.getText.toDouble, costPerUnit.getText.toDouble,
            quantity.getText.toInt, typeOfStock.getText, productName.getText, info.getText, releaseDate.getValue)
            miscList.getItems.clear()
            store.delete(theMisc)
            store.loadStock().filter(x => x.typeOfStock == "Misc").foreach(x => miscList.getItems.add(x.productName))
          case _ =>
        }
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 290; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      deleteButton.onAction = handle {
        for(item <- store.loadStock()) {
          if(item.productName == miscList.getSelectionModel.getSelectedItem) store.delete(item)
        }
        miscList.getItems.clear
        store.loadStock().filter(x => x.typeOfStock == "Misc").foreach(x => miscList.getItems.add(x.productName))
      }
      content = List(miscText, miscList, homeButton, basketButton, createButton, editButton, deleteButton)
    }
  }

  val customerStage: Stage = new Stage() {
    outer =>
    title = "Customers"
    scene = new Scene(1280, 720) {
      fill = Color.AliceBlue
      val customerText = new Label("Customers"); customerText.setStyle("-fx-font-size: 30pt");
      customerText.layoutX = 20; customerText.layoutY = 20
      customerList.setPrefSize(900, 600); customerList.layoutX = 20; customerList.layoutY = 100
      customerList.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 150); homeButton.layoutX = 950
      homeButton.layoutY = 500; homeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      val createButton = new Button("Add"); createButton.setPrefSize(300, 100); createButton.layoutX = 950
      createButton.layoutY = 100; createButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      createButton.onAction = handle {
        val dialog = new Dialog[Stock]() {
          initOwner(customerStage)
          title = "Add game"
          headerText = "Enter customer details"
        }
        val name = new TextField(); val email = new TextField(); val isLoyalCustomer = new ComboBox[Boolean]
        isLoyalCustomer.getItems().addAll(true, false)
        val accept = new ButtonType("Accept", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Name:"), 0, 0); add(name, 1, 0)
          add(new Label("Email:"), 0, 1); add(email, 1, 1)
          add(new Label("Loyal customer?"), 0, 2); add(isLoyalCustomer, 1, 2)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createCustomer(99, name.getText, email.getText,
                                                    isLoyalCustomer.getSelectionModel.getSelectedItem, 0)
            customerList.getItems.clear()
            store.loadCustomers().foreach(x => customerList.getItems.add(x.name))
          case _ =>
        }
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 220; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      editButton.onAction = handle {
        var theCustomer: Customer = null
        for(item <- store.loadCustomers()) {
          if(item.name == customerList.getSelectionModel.getSelectedItem) theCustomer = item
        }
        val dialog = new Dialog[Stock]() {
          initOwner(customerStage)
          title = "Edit customer"
          headerText = "Edit customer details"
        }
        val name = new TextField(); val email = new TextField(); val isLoyalCustomer = new ComboBox[Boolean]
        isLoyalCustomer.getItems().addAll(true, false); name.setText(theCustomer.name)
        email.setText(theCustomer.email); val loyaltyPoints = new TextField()
        loyaltyPoints.setText(theCustomer.loyaltyPoints.toString)
        if(theCustomer.isLoyalCustomer) isLoyalCustomer.getSelectionModel.selectFirst()
        else isLoyalCustomer.getSelectionModel.selectLast()
        val accept = new ButtonType("Accept", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Name:"), 0, 0); add(name, 1, 0)
          add(new Label("Email:"), 0, 1); add(email, 1, 1)
          add(new Label("Loyal customer?"), 0, 2); add(isLoyalCustomer, 1, 2)
          add(new Label("Loyalty points"), 0, 3); add(loyaltyPoints, 1, 3)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createCustomer(99, name.getText, email.getText,
            isLoyalCustomer.getSelectionModel.getSelectedItem, 0)
            customerList.getItems.clear()
            store.delete(theCustomer)
            store.loadCustomers().foreach(x => customerList.getItems.add(x.name))
          case _ =>
        }
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 340; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      deleteButton.onAction = handle {
        for(item <- store.loadCustomers()) {
          if(item.name == customerList.getSelectionModel.getSelectedItem) store.delete(item)
        }
        customerList.getItems.clear
        store.loadCustomers().foreach(x => customerList.getItems.add(x.name))
      }
      content = List(customerText, customerList, homeButton, createButton, editButton, deleteButton)
    }
  }

  val employeeStage: Stage = new Stage() {
    outer =>
    title = "Employees"
    scene = new Scene(1280, 720) {
      fill = Color.AliceBlue
      val employeeText = new Label("Employees"); employeeText.setStyle("-fx-font-size: 30pt");
      employeeText.layoutX = 20; employeeText.layoutY = 20
      employeeList.setPrefSize(900, 600); employeeList.layoutX = 20; employeeList.layoutY = 100
      employeeList.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 150); homeButton.layoutX = 950
      homeButton.layoutY = 500; homeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      val createButton = new Button("Add"); createButton.setPrefSize(300, 100); createButton.layoutX = 950
      createButton.layoutY = 100; createButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      createButton.onAction = handle {
        val dialog = new Dialog[Stock]() {
          initOwner(employeeStage)
          title = "Add employee"
          headerText = "Enter employee details"
        }
        val firstName = new TextField(); val jobTitle = new TextField(); val surname = new TextField()
        val accept = new ButtonType("Accept", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("First name:"), 0, 0); add(firstName, 1, 0)
          add(new Label("Surname:"), 0, 1); add(surname, 1, 1)
          add(new Label("Job title"), 0, 2); add(jobTitle, 1, 2)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => store.createStaff(99, firstName.getText, surname.getText, jobTitle.getText)
            employeeList.getItems.clear()
            store.loadStaff().foreach(x => employeeList.getItems.add(x.firstName + " " + x.surname))
          case _ =>
        }
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 220; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      editButton.onAction = handle {
        var theEmployee: Staff = null
        for(item <- store.loadStaff()) {
          if((item.firstName + " " + item.surname) == employeeList.getSelectionModel.getSelectedItem) theEmployee = item
        }
        val dialog = new Dialog[Stock]() {
          initOwner(employeeStage)
          title = "Add employee"
          headerText = "Enter employee details"
        }
        val firstName = new TextField(); val jobTitle = new TextField(); val surname = new TextField()
        firstName.setText(theEmployee.firstName); surname.setText(theEmployee.surname)
        jobTitle.setText(theEmployee.jobTitle)
        val accept = new ButtonType("Accept", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("First name:"), 0, 0); add(firstName, 1, 0)
          add(new Label("Surname:"), 0, 1); add(surname, 1, 1)
          add(new Label("Job title"), 0, 2); add(jobTitle, 1, 2)
        }
        grid match {
          case _ if(store.loggedInStaff.jobTitle == "Manager") => {
            dialog.dialogPane().content = grid
            val result = dialog.showAndWait()
            result match {
              case Some(accept) => store.createStaff(99, firstName.getText, surname.getText, jobTitle.getText)
                employeeList.getItems.clear()
                store.delete(theEmployee)
                store.loadStaff().foreach(x => employeeList.getItems.add(x.firstName + " " + x.surname))
              case _ =>
            }
          }
          case _ => new Alert(AlertType.ERROR, "You do not have permission to do this.").showAndWait()
        }
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 340; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      deleteButton.onAction = handle {
        for(item <- store.loadStaff()) {
          if((item.firstName + " " + item.surname) == employeeList.getSelectionModel.getSelectedItem) store.delete(item)
        }
        employeeList.getItems.clear()
        store.loadStaff().foreach(x => employeeList.getItems.add(x.firstName + " " + x.surname))
      }
      content = List(employeeText, employeeList, homeButton, createButton, editButton, deleteButton)
    }
  }

  val basketStage: Stage = new Stage() {
    outer =>
    title = "Basket"
    scene = new Scene(1280, 720) {
      fill = Color.web("#fffded")
      val basketText = new Label("Basket"); basketText.setStyle("-fx-font-size: 30pt")
      basketText.layoutX = 20; basketText.layoutY = 20
      basketList.setPrefSize(900, 600); basketList.layoutX = 20; basketList.layoutY = 100
      basketList.setStyle("-fx-font-size: 20pt")
      val homeButton = new Button("Home"); homeButton.setPrefSize(300, 150); homeButton.layoutX = 950
      homeButton.layoutY = 500; homeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #f7ed9e")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      val checkoutButton = new Button("Complete\ncheckout"); checkoutButton.setPrefSize(300, 150)
      checkoutButton.layoutX = 950; checkoutButton.layoutY = 300;
      checkoutButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #f7ed9e")
      checkoutButton.onAction = handle {
        val dialog = new Dialog[Stock]() {
          initOwner(basketStage)
          title = "Select customer"
          headerText = "Select customer"
        }
        val custBox = new ComboBox[String]()
        store.loadCustomers().foreach(x => custBox.getItems.add(x.name))
        val accept = new ButtonType("Accept", ButtonData.OKDone)
        val back = new ButtonType("Back", ButtonData.CancelClose)
        dialog.dialogPane().buttonTypes = Seq(accept, back)
        val grid = new GridPane() {
          hgap = 10; vgap = 10; padding = Insets(20, 100, 10, 10)
          add(new Label("Customer:"), 0, 0); add(custBox, 1, 0)
        }
        dialog.dialogPane().content = grid
        val result = dialog.showAndWait()
        result match {
          case Some(accept) => for(item <- store.loadCustomers()) {
            if(item.name == custBox.getSelectionModel.getSelectedItem) store.makeSale(99, item, 0)
            basketList.getItems.clear()
            store.clearBasket()
          }
          case _ =>
        }
      }
      val removeButton = new Button("Remove item"); removeButton.setPrefSize(300, 100); removeButton.layoutX = 950;
      removeButton.layoutY = 120; removeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #f7ed9e")
      removeButton.onAction = handle {
        for(item <- store.loadStock()) {
          if(item.productName == basketList.getSelectionModel.getSelectedItem) store.basket -= item
        }
        basketList.getItems.remove(basketList.getSelectionModel.getSelectedItem)
      }
      content = List(basketText, basketList, homeButton, checkoutButton, removeButton)
    }
  }

  val financeStage: Stage = new Stage() {
    outer =>
    title = "Financial information"
    scene = new Scene(1280, 720) {
      fill = Color.web("#fceede")
      val financeLabel = new Label("Finance"); financeLabel.setStyle("-fx-font-size: 30pt")
      financeLabel.layoutX = 20; financeLabel.layoutY = 20
      val resultLabel = new Label("Result:"); resultLabel.layoutX = 250; resultLabel.layoutY = 500
      resultLabel.setStyle("-fx-font-size: 20pt")
      val resultField = new TextField(); resultField.setPrefSize(600, 50); resultField.layoutX = 250
      resultField.layoutY = 550; resultField.setStyle("-fx-font-size: 18pt")
      val todayProfButton = new Button("View today's profit"); todayProfButton.setPrefSize(550, 50)
      todayProfButton.layoutX = 60; todayProfButton.layoutY = 300
      todayProfButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #ffcd96")
      todayProfButton.onAction = handle {
        resultField.setText("Today's profit is: £" + store.calculateTodaysProfit(store.listOfSales))
      }
      val todaySalesButton = new Button("View today's sales"); todaySalesButton.setPrefSize(550, 50)
      todaySalesButton.layoutX = 60; todaySalesButton.layoutY = 150
      todaySalesButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #ffcd96")
      todaySalesButton.onAction = handle {
        resultField.setText("Today's sellings are: £" + store.listTodaysSales(store.listOfSales))
      }
      val yesterSalesButton = new Button("View yesterday's sales"); yesterSalesButton.setPrefSize(550, 50)
      yesterSalesButton.layoutX = 670; yesterSalesButton.layoutY = 150
      yesterSalesButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #ffcd96")
      yesterSalesButton.onAction = handle {
        resultField.setText("Yesterday's sellings were £:" + store.previousDaysSales)
      }
      val forecastButton = new Button("Forecast future profit"); forecastButton.setPrefSize(550, 50)
      forecastButton.layoutX = 670; forecastButton.layoutY = 300
      forecastButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #ffcd96")
      forecastButton.onAction = handle {
        //store.forecastExpectedProfit()
      }
      val homeButton = new Button("Home"); homeButton.setPrefSize(200, 150); homeButton.layoutX = 1000
      homeButton.layoutY = 500; homeButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #ffcd96")
      homeButton.onAction = handle {
        homeStage.show(); outer.close()
      }
      content = List(resultField, todayProfButton, todaySalesButton, yesterSalesButton, resultLabel, forecastButton,
                      homeButton, financeLabel)
    }
  }
}
