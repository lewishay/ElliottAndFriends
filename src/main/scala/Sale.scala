import java.time.LocalDateTime
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Lewis on 19/06/2017.
  */
class Sale(id: Int, timeOfSale: LocalDateTime, listOfItems: ArrayBuffer[Stock],
           var totalPrice: Double, customer: Customer = null) {
  for(item <- listOfItems) totalPrice = totalPrice + item.salePrice


  def generateReceipt()

  def canPayWithLoyalty(customer: Customer): Boolean
}
