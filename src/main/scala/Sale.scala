import java.time.LocalDateTime
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Iain on 21/06/2017.
  */
class Sale(id: Int, timeOfSale: LocalDateTime, listOfItems: ArrayBuffer[Stock],
           var totalPrice: Double = 0, customer: Customer = null) {
  for (item <- listOfItems) totalPrice = totalPrice + item.salePrice
  if (customer != null) {
    canPayWithLoyalty(customer)
  }


  def generateReceipt(): Unit = {

    println("the time of this sale is" + timeOfSale)
    println("the item you are buying" + listOfItems)
    println("the total cost of this product is" + totalPrice)
    println("this item could of gotten you" + (totalPrice + 10))

  }


def canPayWithLoyalty(customer: Customer): Unit = {
  if (customer != null && customer.isLoyalCustomer == true && customer.loyaltyPoints >= totalPrice) {

    customer.loyaltyPoints -= totalPrice.toInt

    println("the customers new loyalty points value is " + customer.loyaltyPoints)
  }
  else {
    println("the customer is a disloyal son of a bitch an needs to show you the scrilla ")
  }
}
}
