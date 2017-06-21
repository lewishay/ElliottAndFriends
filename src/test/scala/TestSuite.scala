import org.scalatest.FunSuite


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
  test("Staff class exists"){
    val theStaff = new Staff(1, "Iain", "Fraser", "Staff")
    assert (theStaff.staffId == 1)
    assert (theStaff.firstName == "Iain")
    assert (theStaff.surname == "Fraser")
    assert (theStaff.jobTitle == "Staff")
  }

}
