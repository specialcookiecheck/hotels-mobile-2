package ie.setu.hotels.ui.screens.addHotel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.hotels.data.model.HotelModel
import ie.setu.hotels.data.model.fakeHotels
import ie.setu.hotels.ui.components.addHotel.RoomRatePicker
import ie.setu.hotels.ui.components.addHotel.AddHotelButton
import ie.setu.hotels.ui.components.addHotel.CommentInput
import ie.setu.hotels.ui.components.addHotel.ProgressBar
import ie.setu.hotels.ui.components.addHotel.RadioButtonGroup
import ie.setu.hotels.ui.components.addHotel.WelcomeText
import ie.setu.hotels.ui.screens.hotels.HotelsViewModel
import ie.setu.hotels.ui.theme.HotelsTheme

@Composable
fun AddHotelScreen(modifier: Modifier = Modifier,
                   hotelsViewModel: HotelsViewModel = hiltViewModel(),
                   name: String
) {
    var preferredPaymentType by remember { mutableStateOf("Credit Card") }
    var roomRate by remember { mutableIntStateOf(30) }
    var hotelComments by remember { mutableStateOf("NIIIIIIIIIICE!") }
    var totalAddHotelAdded by remember { mutableIntStateOf(0) }
    val hotels = hotelsViewModel.uiHotels.collectAsState().value

    totalAddHotelAdded = hotels.sumOf { it.roomRate }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            WelcomeText(Modifier, name)
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                RadioButtonGroup(
                    modifier = modifier,
                    onPreferredPaymentTypeChange = { preferredPaymentType = it }
                )
                Spacer(modifier.weight(1f))
                RoomRatePicker(
                    onRoomRateAmountChange = { roomRate = it }
                )
            }
            ProgressBar(
                modifier = modifier,
                totalAddHotelAdded = totalAddHotelAdded)
            CommentInput(
                modifier = modifier,
                onCommentChange = { hotelComments = it }
            )
            AddHotelButton (
                modifier = modifier,
                hotel = HotelModel(preferredPaymentType = preferredPaymentType,
                    roomRate = roomRate,
                    message = hotelComments),
                onTotalAddHotelAddedChange = { totalAddHotelAdded = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddHotelScreenPreview() {
    HotelsTheme {
        PreviewAddHotelScreen( modifier = Modifier,
            hotels = fakeHotels.toMutableStateList())
    }
}

@Composable
fun PreviewAddHotelScreen(modifier: Modifier = Modifier,
                        hotels: SnapshotStateList<HotelModel>
) {
    var preferredPaymentType by remember { mutableStateOf("Credit Card") }
    var roomRate by remember { mutableIntStateOf(10) }
    var hotelComments by remember { mutableStateOf("Go Homer!") }
    var totalAddHotelAdded by remember { mutableIntStateOf(0) }

    totalAddHotelAdded = hotels.sumOf { it.roomRate }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            WelcomeText(modifier, "VincDefault")
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                RadioButtonGroup(
                    modifier = modifier,
                    onPreferredPaymentTypeChange = { preferredPaymentType = it }
                )
                Spacer(modifier.weight(1f))
                RoomRatePicker(
                    onRoomRateAmountChange = { roomRate = it }
                )
            }
            ProgressBar(
                modifier = modifier,
                totalAddHotelAdded = totalAddHotelAdded)
            CommentInput(
                modifier = modifier,
                onCommentChange = { hotelComments = it }
            )
            AddHotelButton (
                modifier = modifier,
                hotel = HotelModel(preferredPaymentType = preferredPaymentType,
                    roomRate = roomRate,
                    message = hotelComments),
                onTotalAddHotelAddedChange = { totalAddHotelAdded = it }
            )
        }
    }
}