package ie.setu.hotels.data.model

import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import java.util.Date

@Entity
data class HotelModel(
    @DocumentId val _id: String = "N/A",
    val preferredPaymentType: String = "N/A",
    val roomRate: Int = 0,
    var message: String = "Go Homer!",
    val dateAddHotelAdded: Date = Date(),
    val dateModified: Date = Date(),
    var email: String = "joe@bloggs.com",
    var imageUri: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)

val fakeHotels = List(30) { i ->
    HotelModel(
        _id = "12345" + i,
        "PayPal $i",
        i.toInt(),
        "Message $i",
        Date(),
        Date()
    )
}
