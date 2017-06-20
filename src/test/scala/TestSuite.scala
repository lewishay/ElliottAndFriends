import org.scalatest.FunSuite


/**
  * Created by Administrator on 20/06/2017.
  */
class TestSuite extends FunSuite {
  test("Store class exists") {
    val theStore = new Store("test", null, null)
    assert(theStore.getID() == "test")
    assert(theStore.getSales() == null)
    assert(theStore.getStock() == null)
  }
  test("Customer class exists") {
    val theCustomer = new Customer(1, "Elliott", "Xx_WomackRocks_xX@Womack.Me", true, 9001)
    assert(theCustomer.GetID() == 1)
    assert(theCustomer.GetName() == "Elliott")
    assert(theCustomer.GetEmail() == "Xx_WomackRocks_xX@Womack.Me")
    assert(theCustomer.GetIsLoyalCustomer() == true)
    assert(theCustomer.GetLoyaltyPoints() == 9001)
  }
}
