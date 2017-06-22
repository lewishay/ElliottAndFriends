import scala.collection.mutable.ArrayBuffer
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.Includes._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.paint.Color
import scalafx.stage.Stage

/**
  * Created by Lewis on 20/06/2017.
  */
object GUI extends JFXApp {
  val store = new Store("Manchester store", new ArrayBuffer[Stock], new ArrayBuffer[Sale], new ArrayBuffer[Customer],
                        new ArrayBuffer[Staff], new ArrayBuffer[Stock])
  var searchResults = new ArrayBuffer[Stock]
  val searchResultsList = new ListView[String]
  val gamesList = new ListView[String]
  val hardwareList = new ListView[String]
  val miscList = new ListView[String]
  val customerList = new ListView[String]
  val employeeList = new ListView[String]
  val basketList = new ListView[String]

  val loginStage: Stage = new JFXApp.PrimaryStage() {
    outer =>
    title = "Login"
    scene = new Scene(440, 240) {
      fill = Color.web("f7eaea")
      val usernameField = new TextField(); usernameField.setPrefSize(400, 50); usernameField.layoutX = 20
      usernameField.layoutY = 30; usernameField.setStyle("-fx-font-size: 18pt")
      val passwordField = new TextField(); passwordField.setPrefSize(400, 50); passwordField.layoutX = 20
      passwordField.layoutY = 105; passwordField.setStyle("-fx-font-size: 18pt")
      val usernameLabel = new Label("Username"); usernameLabel.setStyle("-fx-font-size: 10pt")
      usernameLabel.layoutX = 20; usernameLabel.layoutY = 10
      val passwordLabel = new Label("Password"); passwordLabel.setStyle("-fx-font-size: 10pt")
      passwordLabel.layoutX = 20; passwordLabel.layoutY = 85
      val loginButton = new Button("Login"); loginButton.setPrefSize(400, 50); loginButton.layoutX = 20
      loginButton.layoutY = 170; loginButton.setStyle("-fx-font-size: 18pt; -fx-background-color: #f9afac")
      loginButton.onAction = handle {
        //when login feature is implemented it goes here
        homeStage.show(); outer.close()
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
        store.loadStock().filter(x => x.typeOfStock == "Hardware").foreach(x => hardwareList.getItems.add(x.productName))
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
        financeStage.show(); outer.close()
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
      val searchField = new TextField(); searchField.layoutX = 50; searchField.layoutY = 200
      searchField.setPrefSize(900, 50); searchField.setStyle("-fx-font-size: 20pt")
      val searchButton = new Button("Search"); searchButton.setPrefSize(250, 50); searchButton.layoutX = 960
      searchButton.layoutY = 200; searchButton.setStyle("-fx-font-size: 20pt; -fx-background-color: #b6edcb")
      searchButton.onAction = handle {
        searchResultsList.getItems.clear
        searchResults = store.search(searchField.getText)
        searchResults.foreach(x => searchResultsList.getItems.add(x.productName))
        searchStage.show(); outer.close()
      }
      content = List(bannerView, gamesButton, hardwareButton, miscButton, financeButton, searchField, searchButton,
                    customerButton, employeeButton, basketButton)
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
        //method here
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 220; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #b6edcb")
      deleteButton.onAction = handle {
        //method here
      }
      content = List(resultsText, searchResultsList, homeButton, basketButton, editButton, deleteButton)
    }
  }

  val gamesStage: Stage = new Stage() {
    outer =>
    title = "Games"
    scene = new Scene(1280, 720) {
      fill = Color.web("#f7f2ff")
      val gameText = new Label("Games"); gameText.setStyle("-fx-font-size: 30pt");
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
        dialog.showAndWait()
//        store.createStock()
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 170; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      editButton.onAction = handle {
        //method here
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 290; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      deleteButton.onAction = handle {
        //method here
      }
      val preorderButton = new Button("View preorders"); preorderButton.setPrefSize(300, 50)
      preorderButton.layoutX = 550; preorderButton.layoutY = 10
      preorderButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      preorderButton.onAction = handle {
        //function to filter the loaded games by only games with a future release date
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
        //method here
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 170; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      editButton.onAction = handle {
        //method here
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 290; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      deleteButton.onAction = handle {
        //method here
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
        //method here
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 170; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      editButton.onAction = handle {
        //method here
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 290; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #d6bcf2")
      deleteButton.onAction = handle {
        //method here
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
        //method here
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 220; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      editButton.onAction = handle {
        //method here
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 340; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      deleteButton.onAction = handle {
        //method here
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
        //method here
      }
      val editButton = new Button("Edit"); editButton.setPrefSize(300, 100); editButton.layoutX = 950
      editButton.layoutY = 220; editButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      editButton.onAction = handle {
        //method here
      }
      val deleteButton = new Button("Delete"); deleteButton.setPrefSize(300, 100); deleteButton.layoutX = 950
      deleteButton.layoutY = 340; deleteButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #a0d9ef")
      deleteButton.onAction = handle {
        //method here
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
        //need to add in method to checkout items
        basketList.getItems.clear()
        store.clearBasket()
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
        //method here
      }
      val todaySalesButton = new Button("View today's sales"); todaySalesButton.setPrefSize(550, 50)
      todaySalesButton.layoutX = 60; todaySalesButton.layoutY = 150
      todaySalesButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #ffcd96")
      todaySalesButton.onAction = handle {
        //method here
      }
      val yesterSalesButton = new Button("View yesterday's sales"); yesterSalesButton.setPrefSize(550, 50)
      yesterSalesButton.layoutX = 670; yesterSalesButton.layoutY = 150
      yesterSalesButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #ffcd96")
      yesterSalesButton.onAction = handle {
        //method here
      }
      val forecastButton = new Button("Forecast future profit"); forecastButton.setPrefSize(550, 50)
      forecastButton.layoutX = 670; forecastButton.layoutY = 300
      forecastButton.setStyle("-fx-font-size: 25pt; -fx-background-color: #ffcd96")
      forecastButton.onAction = handle {
        //method here
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
