import java.time.LocalDateTime
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Lewis on 19/06/2017.
  */
case class Sale(id: Int, timeOfSale: LocalDateTime, listOfItems: ArrayBuffer[Stock],
           customer: Customer = null, var totalPrice: Double = 0) {
  for(item <- listOfItems) totalPrice = totalPrice + item.salePrice


  def generateReceipt(): Unit = {

  }

  def canPayWithLoyalty(customer: Customer): Boolean = {
    false
  }
}
