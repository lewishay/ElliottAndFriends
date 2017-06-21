import java.time.LocalDate

import java.time.LocalDateTime

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 20/06/2017.
  */
class TestSuite extends FunSuite {
  test("Store class exists") {
    val theStore = Store("test", null, null, null, null, null)
    assert(theStore.id == "test")
    assert(theStore.basket == null)
    assert(theStore.listOfSales == null)
    assert(theStore.customers == null)
    assert(theStore.staff == null)
  }

  test("Customer class exists") {
    val theCustomer = Customer(1, "Elliott", "Xx_WomackRocks_xX@Womack.Me", true, 9001)
    assert(theCustomer.id == 1)
    assert(theCustomer.name == "Elliott")
    assert(theCustomer.email == "Xx_WomackRocks_xX@Womack.Me")
    assert(theCustomer.isLoyalCustomer)
    assert(theCustomer.loyaltyPoints == 9001)
  }

  test("Staff class exists") {
    val theStaff = Staff(1, "Iain", "Fraser", "Staff")
    assert(theStaff.staffId == 1)
    assert(theStaff.firstName == "Iain")
    assert(theStaff.surname == "Fraser")
    assert(theStaff.jobTitle == "Staff")
  }

  test("Cased delete function works"){
    val store = Store("Elliott & Friends", ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty)

    store.staff += Staff(1, "Jane", "Doe", "Manager")
    assert (store.staff.nonEmpty)
    store.heldStock += Stock(1, 20.00, 10.00, 5, "Game", "Nier: Automata", " ", LocalDate.now)
    assert (store.heldStock.nonEmpty)
    store.customers += Customer(1, "Jane Doe", "jane.doe@gmail.com", true, 100)
    assert (store.customers.nonEmpty)


    val jane: Staff = Staff(1, "Jane", "Doe", "Manager")
    store.delete(jane)
    assert (store.staff.isEmpty)

    val nier: Stock = Stock(1, 20.00, 10.00, 5, "Game", "Nier: Automata", " ", LocalDate.now)
    store.delete(nier)
    assert (store.heldStock.isEmpty)

    val janedoe: Customer = Customer(1, "Jane Doe", "jane.doe@gmail.com", true, 100)
    store.delete(janedoe)
    assert (store.customers.isEmpty)

    store.delete("{ { { { { C U R L Y B O I S } } } } }")
  }


  test("Sale is made") {
    val dateTime = LocalDateTime.of(2017, 5, 16, 8, 30)
    val sale = Sale(1, dateTime, new ArrayBuffer[Stock], null, 50.0)
    val store = Store("1", ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty)
    store.listOfSales += sale
    assert(store.listOfSales.length == 1)
  }

  test("Basket is clear") {
    val date = LocalDate.of(2017, 4, 17)
    val store = Store("1", ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty)
    val x = Stock(1, 35.0, 30.0, 100, "Game", "Nioh", "Playstation 4 age 18", date)
    store.basket += x
    assert(store.basket.length == 1)
    store.clearBasket()
    assert(store.basket.isEmpty)
  }

  test("Calculate today's profits"){
    var store = Store("Elliott & Friends", ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty)
    var todaysSales: ArrayBuffer[Sale] = ArrayBuffer.empty
    todaysSales += Sale(
      1,
      LocalDateTime.now,
      ArrayBuffer[Stock](
        Stock(1, 20.00, 10.00, 5, "Game", "Nier: Automata", " ", LocalDate.now),
        Stock(2, 50.00, 25.00, 5, "Software", "Photoshop CS6", " ", LocalDate.now))
    )
    todaysSales += Sale(
      2,
      LocalDateTime.now,
      ArrayBuffer[Stock](
        Stock(3, 10, 4, 20, "Misc", "Pocketu-Monsteru Cardo", " ", LocalDate.now),
        Stock(4, 40, 20, 12, "Game", "Half Life 3", " ", LocalDate.now))
    )
    assert(store.calculateTodaysProfit(todaysSales) == 61.00)
  }

  test("List today's sales"){
    var store = Store("Elliott & Friends", ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty , ArrayBuffer.empty, ArrayBuffer.empty)
    var todaysSales: ArrayBuffer[Sale] = ArrayBuffer.empty
    todaysSales += Sale(
      1,
      LocalDateTime.now,
      ArrayBuffer[Stock](
        Stock(1, 20.00, 10.00, 5, "Game", "Nier: Automata", " ", LocalDate.now),
        Stock(2, 50.00, 25.00, 5, "Software", "Photoshop CS6", " ", LocalDate.now))
    )
    todaysSales += Sale(
      2,
      LocalDateTime.now,
      ArrayBuffer[Stock](
        Stock(3, 10, 4, 20, "Misc", "Pocketu-Monsteru Cardo", " ", LocalDate.now),
        Stock(4, 40, 20, 12, "Game", "Half Life 3", " ", LocalDate.now))
    )
    store.listTodaysSales(todaysSales)
  }

}


