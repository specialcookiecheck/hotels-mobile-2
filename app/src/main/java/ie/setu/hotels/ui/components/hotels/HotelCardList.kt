package ie.setu.hotels.ui.components.hotels

import android.net.Uri
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import ie.setu.hotels.data.model.HotelModel
import ie.setu.hotels.data.model.fakeHotels
import ie.setu.hotels.ui.theme.HotelsTheme
import java.text.DateFormat

@Composable
internal fun HotelCardList(
    hotels: List<HotelModel>,
    modifier: Modifier = Modifier,
    onDeleteHotel: (HotelModel) -> Unit,
    onClickHotelDetails: (String) -> Unit
) {
    LazyColumn {
        items(
            items = hotels,
            key = { hotel -> hotel._id }
        ) { hotel ->
            HotelCard(
                preferredPaymentType = hotel.preferredPaymentType,
                roomRate = hotel.roomRate,
                message = hotel.message,
                dateCreated = DateFormat.getDateTimeInstance().format(hotel.dateAddHotelAdded),
                dateModified = DateFormat.getDateTimeInstance().format(hotel.dateModified),
                onClickDelete = { onDeleteHotel(hotel) },
                onClickHotelDetails = { onClickHotelDetails(hotel._id) },
                photoUri = Uri.parse(hotel.imageUri)
            )
        }
    }

}

@Preview(showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun HotelCardListPreview() {
    HotelsTheme {
        HotelCardList(
            fakeHotels.toMutableStateList(),
            onDeleteHotel = {}
        ) { }
    }
    }
