import java.time.LocalDate

import java.time.LocalTime

import java.time.LocalDateTime

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 20/06/2017.
  */
class TestSuite extends FunSuite {
  test("Store class exists") {
    val theStore = new Store("test", null, null)
    assert(theStore.id == "test")
    assert(theStore.basket == null)
    assert(theStore.listOfSales == null)
  }
  test("Customer class exists") {
    val theCustomer = new Customer(1, "Elliott", "Xx_WomackRocks_xX@Womack.Me", true, 9001)
    assert(theCustomer.id == 1)
    assert(theCustomer.name == "Elliott")
    assert(theCustomer.email == "Xx_WomackRocks_xX@Womack.Me")
    assert(theCustomer.isLoyalCustomer == true)
    assert(theCustomer.loyaltyPoints == 9001)
  }
  test("Staff class exists") {
    val theStaff = new Staff(1, "Iain", "Fraser", "Staff")
    assert(theStaff.staffId == 1)
    assert(theStaff.firstName == "Iain")
    assert(theStaff.surname == "Fraser")
    assert(theStaff.jobTitle == "Staff")
  }
  test("Sale is made") {
    val dateTime = LocalDateTime.of(2017, 5, 16, 8, 30)
    val sale = new Sale(1, dateTime, new ArrayBuffer[Stock], null, 50.0)
    val store = new Store("1", new ArrayBuffer[Stock], new ArrayBuffer[Sale])
    store.listOfSales += sale
    assert(store.listOfSales.length == 1)
  }

  test("Basket is clear") {
    val date = LocalDate.of(2017, 4, 17)
    val store = new Store("1", new ArrayBuffer[Stock], new ArrayBuffer[Sale])
    val x = new Stock(1, 35.0, 30.0, 100, "Game", "Nioh", "Playstation 4 age 18", date)
    store.basket += x
    assert(store.basket.length == 1)
    store.clearBasket()
    assert(store.basket.isEmpty)
  }
}


