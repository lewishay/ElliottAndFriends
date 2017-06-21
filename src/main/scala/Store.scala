import java.time.LocalDateTime

import java.time.LocalDate

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Lewis on 19/06/2017.
  */
case class Store(id: String, basket: ArrayBuffer[Stock], listOfSales: ArrayBuffer[Sale], var customers: ArrayBuffer[Customer], var staff: ArrayBuffer[Staff], var heldStock: ArrayBuffer[Stock]) {

  def login(username: String, password: String): Boolean = {
    val toReturn = false
    toReturn
  }

  def createCustomer(id: Int, name: String, email: String, isLoyalCustomer: Boolean, loyaltyPoints: Int): Unit = {
    customers += Customer(id, name, email, isLoyalCustomer, loyaltyPoints)
  }

  def createStaff(staffId: Int, firstName: String, surname: String, jobTitle: String): Unit = {
    staff += Staff(staffId, firstName, surname, jobTitle)
  }

  def createStock(id: Int, salePrice: Double, costPerUnit: Double, quantity: Int, typeOfStock: String,
                  productName: String, info: String, releaseDate: LocalDate): Unit = {
    heldStock += Stock(id, salePrice, costPerUnit, quantity, typeOfStock, productName, info, releaseDate)
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

  def delete[T](toDelete: T): Unit = {
    toDelete match{
      case toDelete: Stock => if(!heldStock.isEmpty) heldStock = heldStock.filter(_ != toDelete)
      case toDelete: Staff => if(!staff.isEmpty) staff = staff.filter(_ != toDelete)
      case toDelete: Customer => if(!customers.isEmpty) customers = customers.filter(_ != toDelete)
      case _ => println("Please select a Customer, Staff Member, or Stock Item to be deleted")
      //    { { { { { { C U R L Y B O I S } } } } } }
    }
  }

  def makeSale(listOfItems: ArrayBuffer[Stock],id: Int, timeOfSale: LocalDateTime, customer : Customer = null): Unit = {
    if (customer == null) {
      listOfSales += Sale(id, timeOfSale, listOfItems)
    }
    else{
     listOfSales += Sale(id, timeOfSale, listOfItems, customer)
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
