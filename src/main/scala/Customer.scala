/**
  * Created by Lewis on 19/06/2017.
  */
class Customer(id: Int, name: String, email: String, isLoyalCustomer: Boolean, loyaltyPoints: Int) {
  def GetID(): Int = id

  def GetName(): String = name

  def GetEmail(): String = email

  def GetIsLoyalCustomer(): Boolean = isLoyalCustomer

  def GetLoyaltyPoints(): Int = loyaltyPoints
}
