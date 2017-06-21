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

  def calculateTodaysProfit(todaysSales: ArrayBuffer[Sale]): Double = {
    var profit: Double = 0
    todaysSales.foreach(sale=> sale.listOfItems.foreach(stockItem => profit += (stockItem.salePrice - stockItem.costPerUnit)))
    profit
  }

  var listTodaysSales = (todaysSales: ArrayBuffer[Sale]) => {
    todaysSales.map(sale=> {println(s"Sale " + sale.id); sale.listOfItems.foreach(stockItem => println("  " + stockItem.productName + "    = " + stockItem.salePrice)); println("Total = " + sale.totalPrice)})
  }

  def previousDaysSales(): Unit = {

  }

  def forecastExpectedProfit(): Unit = {

  }

  def preorderGame(customer: Customer, game: Stock): Unit = {

  }
}
