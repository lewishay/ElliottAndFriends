import scala.collection.mutable.ArrayBuffer

/**
  * Created by Lewis on 19/06/2017.
  */
class Store(id: String, basket: ArrayBuffer[Stock], listOfSales: ArrayBuffer[Sale]) {
  def getID(): String = id

  def getStock(): ArrayBuffer[Stock] = basket

  def getSales(): ArrayBuffer[Sale] = listOfSales

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

  def makeSale(): Unit = {

  }

  def clearBasket(): Unit = {

  }

  def calculateTodaysProfit(): Double = {
    3.0
  }

  def listTodaysSales(): Unit = {

  }

  def previousDaysSales(): Unit = {

  }

  def forecastExpectedProfit(): Unit = {

  }

  def preorderGame(customer: Customer, game: Stock): Unit = {

  }
}
