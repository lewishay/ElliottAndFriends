import org.scalatest.FunSuite


/**
  * Created by Administrator on 20/06/2017.
  */
class TestSuite extends FunSuite {
  test("Store class exists") {
    val theStore = new Store("test", null, null)
    assert(theStore.getID() == "test")
    assert(theStore.getSales() == null)
  }
  test("Customer class exists") {
    val theCustomer = new Customer(1, "Elliott", "Xx_WomackRocks_xX@Womack.Me", true, 9001)
    assert(theCustomer. == "test")
  }
}
//id: Int, name: String, email: String, isLoyalCustomer: Boolean, loyaltyPoints: Int