import scala.collection.mutable.ArrayBuffer

/**
  * Created by Lewis on 19/06/2017.
  */
class Store(id: String, basket: ArrayBuffer[Stock], listOfSales: ArrayBuffer[Sale]) {
  def login(): Boolean

  def createCustomer()

  def createStaff()

  def createStock()

  def search(searchTerm: String): List[Stock]

  def editCustomer(customerToEdit: Customer)

  def editStaff(staffToEdit: Staff)

  def editStock(stockToEdit: Stock)

  def delete(toDelete: Any)

  def makeSale()

  def clearBasket()

  def calculateTodaysProfit(): Double

  def listTodaysSales()

  def previousDaysSales()

  def forecastExpectedProfit()

  def preorderGame(customer: Customer, game: Stock)
}
