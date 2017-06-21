import java.time.LocalDateTime

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Lewis on 19/06/2017.
  */
case class Store(id: String, basket: ArrayBuffer[Stock], listOfSales: ArrayBuffer[Sale]) {

  def login(username: String, password: String): Boolean = {
    var toReturn = false
    toReturn
  }

  def createCustomer(): Unit = {

  }

  def createStaff(): Unit = {

  }

  def createStock(): Unit = {

  }

  def search(searchTerm: String): List[Stock]  = {
    null
  }

  def editCustomer(customerToEdit: Customer): Unit = {

  }

  def editStaff(staffToEdit: Staff): Unit = {

  }

  def editStock(stockToEdit: Stock): Unit = {

  }

  def delete(toDelete: Any): Unit = {

  }

  def makeSale(listOfItems: ArrayBuffer[Stock],id: Int, timeOfSale: LocalDateTime, customer : Customer = null): Unit = {
    if (customer == null) {
      listOfSales += new Sale(id, timeOfSale, listOfItems)
    }
    else{
     listOfSales += new Sale(id, timeOfSale, listOfItems, customer)
    }

  }

  def clearBasket(): Unit = {
    basket.clear()
  }

  def calculateTodaysProfit(): Double = {
    3.0
  }

  def listTodaysSales(todaysSales: ArrayBuffer[Sale]): Unit = {
    //if we have a sales.txt file we can literally do this dynamically with a string of .maps filtering by date
    //LocalDate use for release date of an item in stock is not good - we will have to be able to specify a date for a preorder
    //stock needs getters/to be a case class


  }

  def previousDaysSales(): Unit = {

  }

  def forecastExpectedProfit(): Unit = {

  }

  def preorderGame(customer: Customer, game: Stock): Unit = {

  }
}
