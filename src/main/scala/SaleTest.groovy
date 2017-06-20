package main.scala


    class SalesTest extends FlatSpec with Matchers {
        val gameStore = new GameStore
        var stock = new Stock(1,"Best Game", 10, 1,"today")
        gameStore.itemsListBuffer += stock
        gameStore.receiptListBuffer += new Receipt(gameStore.itemsListBuffer, "16/06/17")
        gameStore.receiptListBuffer += new Receipt(gameStore.itemsListBuffer, "16/06/17")

        "adding up 2 receipt totals" should "display the correct profit" in {
            gameStore.getDaysProfit("16/06/17") shouldBe 20
        }

        "entering a date which has no receipts" should "return 0" in {
            gameStore.getDaysProfit("01/01/01") shouldBe 0
        }

    }
}
