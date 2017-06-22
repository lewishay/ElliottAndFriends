import java.time.LocalDate

/**
  * Created by Lewis on 19/06/2017.
  */
case class Stock(id: Int, var salePrice: Double, costPerUnit: Double, quantity: Int, typeOfStock: String,
            productName: String, info: String, releaseDate: LocalDate)
