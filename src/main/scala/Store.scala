import java.time.LocalDateTime
import java.time.LocalDate
import java.io._
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import java.time.format.DateTimeFormatter

/**
  * Created by Lewis on 19/06/2017.
  */
case class Store(id: String, basket: ArrayBuffer[Stock], listOfSales: ArrayBuffer[Sale], var customers: ArrayBuffer[Customer], var staff: ArrayBuffer[Staff], var heldStock: ArrayBuffer[Stock]) {

  def login(username: String, password: String): Boolean = {
    val toReturn = false
    toReturn
  }

  def createCustomer(id: Int, name: String, email: String, isLoyalCustomer: Boolean, loyaltyPoints: Int): Unit = {
    val tempCustomers = loadCustomers()
    tempCustomers += Customer(id, name, email, isLoyalCustomer, loyaltyPoints)
    saveCustomers(tempCustomers)
  }

  def createStaff(staffId: Int, firstName: String, surname: String, jobTitle: String): Unit = {
    val tempStaff = loadStaff()
    tempStaff += Staff(staffId, firstName, surname, jobTitle)
    saveStaff(tempStaff)
  }

  def createStock(id: Int, salePrice: Double, costPerUnit: Double, quantity: Int, typeOfStock: String,
                  productName: String, info: String, releaseDate: LocalDate): Unit = {
    val tempStock = loadStock()
    tempStock += Stock(id, salePrice, costPerUnit, quantity, typeOfStock, productName, info, releaseDate)
    saveStock(tempStock)
  }

  def search(searchTerm: String): ArrayBuffer[Stock]  = {
    var tempBuffer = new ArrayBuffer[Stock]()
    for(item <- loadStock()) item match {
      case _ if(item.productName.toLowerCase.contains(searchTerm.toLowerCase)) => tempBuffer += item
      case _ =>
    }
    tempBuffer
  }

  def editCustomer(customerToEdit: Customer): Unit = {
    val listOfCustomers: ArrayBuffer[Customer] = loadCustomers()
    val customerIndex = customerToEdit.id - 1
    if(listOfCustomers(customerIndex).id == customerToEdit.id) listOfCustomers(customerIndex) = customerToEdit
    saveCustomers(listOfCustomers)
  }

  def editStaff(staffToEdit: Staff): Unit = {
    val listOfStaff: ArrayBuffer[Staff] = loadStaff()
    val staffIndex = staffToEdit.staffId - 1
    if(listOfStaff(staffIndex).staffId == staffToEdit.staffId) listOfStaff(staffIndex) = staffToEdit
    saveStaff(listOfStaff)
  }

  def editStock(stockToEdit: Stock): Unit = {
    val listOfStock: ArrayBuffer[Stock] = loadStock()
    val stockIndex = stockToEdit.id - 1
    if(listOfStock(stockIndex).id == stockToEdit.id) listOfStock(stockIndex) = stockToEdit
    saveStock(listOfStock)
  }

  def delete[T](toDelete: T): Unit = {
    toDelete match{
      case toDelete: Stock => if(heldStock.nonEmpty) heldStock = heldStock.filter(_ != toDelete)
      case toDelete: Staff => if(staff.nonEmpty) staff = staff.filter(_ != toDelete)
      case toDelete: Customer => if(customers.nonEmpty) customers = customers.filter(_ != toDelete)
      case _ => println("Please select a Customer, Staff Member, or Stock Item to be deleted")
      //    { { { { { { C U R L Y B O I S } } } } } }
    }
  } //here

  def makeSale(listOfItems: ArrayBuffer[Stock],id: Int, timeOfSale: LocalDateTime, customer : Customer = null): Unit = {
    //generate receipt defined here

    if (customer == null) {
      listOfSales += Sale(id, timeOfSale, listOfItems)
    }
    else if
    (customer != null && !customer.isLoyalCustomer) {
      listOfSales += Sale(id, timeOfSale, listOfItems, 0 , customer)
    }
    else {
      listOfSales += Sale(id, timeOfSale, listOfItems, 0 , customer)
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

  var previousDaysSales = (yesterdaysSales: ArrayBuffer[Sale]) => {
    yesterdaysSales.map(sale=> {println(s"Sale " + sale.id); sale.listOfItems.foreach(stockItem => println("  " + stockItem.productName + "    = " + stockItem.salePrice)); println("Total = " + sale.totalPrice)})
  }

  def forecastExpectedProfit(): Unit = {

  }

  def loadCustomers(): ArrayBuffer[Customer] = {
    val currentDirectory = new java.io.File(".").getCanonicalPath  + "\\customer.txt"
    val arrayOfCustomers = ArrayBuffer[Customer]()
    for (line <- Source.fromFile(currentDirectory).getLines()) {
      val customersToReturn: Array[String] = line.split(",")
      val customerToAdd = new Customer(customersToReturn(0).toInt, customersToReturn(1), customersToReturn(2), customersToReturn(3).toBoolean, customersToReturn(4).toInt)
      arrayOfCustomers += customerToAdd
    }
    arrayOfCustomers
  }

  def clearListOfSales(): Unit = {
  listOfSales.clear()
  }

  def loadSales(): Unit = {
    val currentDirectory = new java.io.File(".").getCanonicalPath  + "\\Sales.txt"
    clearListOfSales()
    for (line <- Source.fromFile(currentDirectory).getLines()) {
      val salesToReturn: Array[String] = line.split(",")
      val stock = salesToReturn(2).split("@")
      var arrayOfStock = ArrayBuffer[Stock]()
      for (i <- 0 to stock.length - 1) {
        val stockToAdd = stock(i).split("#")
        arrayOfStock += new Stock(stockToAdd(0).toInt, stockToAdd(1).toDouble, stockToAdd(2).toDouble, stockToAdd(3).toInt, stockToAdd(4), stockToAdd(5), stockToAdd(6), stringToLocalDate(stockToAdd(7)))
      }
      val customerArray = salesToReturn(4).split("#")
      val customerToAdd = new Customer(customerArray(0).toInt, customerArray(1), customerArray(2), customerArray(3).toBoolean, customerArray(4).toInt)
      val salesToAdd = new Sale(salesToReturn(0).toInt, stringToLocalDateTime(salesToReturn(1)), arrayOfStock, 0, customerToAdd)
      listOfSales += salesToAdd
    }
  }

  def loadStaff():  ArrayBuffer[Staff] = {
    val currentDirectory = new java.io.File(".").getCanonicalPath  + "\\Staff.txt"
    val arrayOfStaff = ArrayBuffer[Staff]()
    for (line <- Source.fromFile(currentDirectory).getLines()) {
      val staffToReturn: Array[String] = line.split(",")
      val staffToAdd = new Staff(staffToReturn(0).toInt, staffToReturn(1), staffToReturn(2), staffToReturn(3))
      arrayOfStaff += staffToAdd
    }
    arrayOfStaff
  }

  def loadStock(): ArrayBuffer[Stock] = {
    val currentDirectory = new java.io.File(".").getCanonicalPath  + "\\Stock.txt"
    val arrayOfStock = ArrayBuffer[Stock]()
    for (line <- Source.fromFile(currentDirectory).getLines()) {
      val stockToReturn: Array[String] = line.split(",")
      val stockToAdd = new Stock(stockToReturn(0).toInt, stockToReturn(1).toDouble, stockToReturn(2).toDouble, stockToReturn(3).toInt, stockToReturn(4), stockToReturn(5), stockToReturn(6), stringToLocalDate(stockToReturn(7)))
      arrayOfStock += stockToAdd
    }
    arrayOfStock
  }

  def stringToLocalDateTime(toLocalDate: String): LocalDateTime = {
    val temp = LocalDateTime.parse(toLocalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    temp
  }

  def stringToLocalDate(toLocalDate: String): LocalDate = {
    val temp = LocalDate.parse(toLocalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    temp
  }

  def saveCustomers(customerToSave: ArrayBuffer[Customer]): Unit = {
    val pw = new PrintWriter("customer.txt")
    for(i<-0 to customerToSave.length-1){
      pw.println(customerToSave(i).id + "," + customerToSave(i).name + "," + customerToSave(i).email + "," + customerToSave(i).isLoyalCustomer + "," + customerToSave(i).loyaltyPoints)
    }
    pw.close()
  }

  def saveStaff(staffToSave: ArrayBuffer[Staff]): Unit = {
    val pw = new PrintWriter("Staff.txt")
    for(i<-0 to staffToSave.length-1){
      pw.println(staffToSave(i).staffId + "," + staffToSave(i).firstName + "," + staffToSave(i).surname + "," + staffToSave(i).jobTitle)
    }
    pw.close()
  }

  def saveStock(stockToSave: ArrayBuffer[Stock]): Unit = {
    val pw = new PrintWriter("Stock.txt")
    for(i<-0 to stockToSave.length-1){
      pw.println(stockToSave(i).id + "," + stockToSave(i).salePrice + "," + stockToSave(i).costPerUnit + "," + stockToSave(i).quantity + "," + stockToSave(i).typeOfStock + "," + stockToSave(i).productName + "," + stockToSave(i).info + "," + stockToSave(i).releaseDate.toString)
    }
    pw.close
  }

  def saveSales(): Unit = {
    val pw = new PrintWriter("Sales.txt")
    for(i<-0 to listOfSales.length-1){
      var stockForSale = ""
      for(j<-0 to listOfSales(i).listOfItems.length - 1){
        stockForSale += listOfSales(i).listOfItems(j).id + "#" + listOfSales(i).listOfItems(j).salePrice + "#" + listOfSales(i).listOfItems(j).costPerUnit + "#" + listOfSales(i).listOfItems(j).quantity + "#" + listOfSales(i).listOfItems(j).typeOfStock + "#" + listOfSales(i).listOfItems(j).productName + "#" + listOfSales(i).listOfItems(j).info + "#" + listOfSales(i).listOfItems(j).releaseDate.toString
        if(j != listOfSales(i).listOfItems.length - 1) stockForSale += "@"
      }
      var customerToAdd = listOfSales(i).customer.id + "#" + listOfSales(i).customer.name + "#" + listOfSales(i).customer.email + "#" + listOfSales(i).customer.isLoyalCustomer + "#" + listOfSales(i).customer.loyaltyPoints
      val fixFormating = listOfSales(i).timeOfSale.toString.split("T")
      val dateStringProper = fixFormating(0) + " " + fixFormating(1)
      pw.println(listOfSales(i).id + "," + dateStringProper + "," + stockForSale + "," + listOfSales(i).totalPrice + "," + customerToAdd)
    }
    pw.close
  }
}
