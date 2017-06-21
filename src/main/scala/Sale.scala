import java.time.LocalDateTime
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Iain on 21/06/2017.
  */
class Sale(id: Int, timeOfSale: LocalDateTime, loyaltyPoints: Int, listOfItems: ArrayBuffer[Stock],
           var totalPrice: Double = 0, customer: Customer = null) {
  for (item <- listOfItems) totalPrice = totalPrice + item.salePrice
  if (customer != null) {
    customer.loyaltyPoints = totalPrice.toInt + 10
  }


  def generateReceipt(): Unit = {

    println("the time of this sale is" + timeOfSale)
    println("the item you are buying" + listOfItems)
    println("the total cost of this product is" + totalPrice)
    println("this item could of gotten you" + (totalPrice + 10))

  }
}

def canPayWithLoyalty(customer: Customer,totalPrice: Double, isLoyalCustomer : Boolean): Unit = {
  if (customer != null && isLoyalCustomer == true && customer.loyaltyPoints >= totalPrice) {

    customer.loyaltyPoints - item.saleprice = totalPrice

    println("your loyalty points have been used to pay for your item" + customer.loyaltyPoints)
  }
  else {
    println("you do not have enough loyalty points, show me the scrilla")
  }
}
