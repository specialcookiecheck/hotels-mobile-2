package ie.setu.hotels.data.model

import android.net.Uri
import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import java.util.Date

@Entity
data class HotelModel(
    @DocumentId val _id: String = "N/A",
    var preferredPaymentType: String = "N/A",
    var hotelName: String = "N/A",
    var hotelCity: String = "N/A",
    var hotelEmail: String = "N/A",
    var hotelPhone: Int = 0,
    var roomRate: Int = 0,
    var comment: String = "Not too bad!",
    val dateAddHotelAdded: Date = Date(),
    val dateModified: Date = Date(),
    var email: String = "vinc@test.com",
    var imageUri: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)

val fakeHotels = List(30) { i ->
    HotelModel(
        _id = "12345" + i,
        "Cash $i",
        "testHotel",
        "testHotelCity",
        "testHotelEmail",
        12345677,
        999,
        "Comment $i",
        Date(),
        Date()
    )
}
