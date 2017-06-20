import org.scalatest.{funSuite}

/**
  * Created by Administrator on 20/06/2017.
  */
class getDaysProfitTest extends funSuite {
  val daysProfit = new DaysProfit
  var stock = new Stock()
  ElliotAndFriends.itemsListBuffer += stock
  ElliotAndFriends.receiptListBuffer += new Receipt(ElliotAndFriends.itemsListBuffer, "16/06/17")
  ElliotAndFriends.receiptListBuffer += new Receipt(ElliotAndFriends.itemsListBuffer, "16/06/17")

  "adding up 2 receipt totals" should "display the correct profit" in {
    ElliotAndfriends.getDaysProfit("16/06/17") shouldBe 20
  }

  "entering a date which has no receipts" should "return 0" in {
    ElliotAndFriends.getDaysProfit("01/01/01") shouldBe 0
  }

}