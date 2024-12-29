package ie.setu.hotels.ui.screens.addHotel

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ie.setu.hotels.data.model.HotelModel
import ie.setu.hotels.data.model.fakeHotels
import ie.setu.hotels.ui.components.addHotel.RoomRatePicker
import ie.setu.hotels.ui.components.addHotel.AddHotelButton
import ie.setu.hotels.ui.components.addHotel.CommentInput
import ie.setu.hotels.ui.components.addHotel.HotelNameInput
import ie.setu.hotels.ui.components.addHotel.RadioButtonGroup
import ie.setu.hotels.ui.components.addHotel.AddHotelText
import ie.setu.hotels.ui.components.addHotel.HotelCityInput
import ie.setu.hotels.ui.components.addHotel.HotelEmailInput
import ie.setu.hotels.ui.components.addHotel.HotelPhoneInput
import ie.setu.hotels.ui.components.general.HotelImagePicker
import ie.setu.hotels.ui.screens.hotels.HotelsViewModel
import ie.setu.hotels.ui.theme.HotelsTheme

@Composable
fun AddHotelScreen(modifier: Modifier = Modifier,
                   hotelsViewModel: HotelsViewModel = hiltViewModel(),
                   addHotelViewModel: AddHotelViewModel = hiltViewModel(),
                   userName: String,
) {
    var hotelName by remember { mutableStateOf("Hotel name") }
    var hotelCity by remember { mutableStateOf("Hotel city") }
    var hotelEmail by remember { mutableStateOf("Hotel email") }
    var hotelPhone by remember { mutableIntStateOf(1234567890) }
    var preferredPaymentType by remember { mutableStateOf("Credit Card") }
    var roomRate by remember { mutableIntStateOf(30) }
    var hotelComments by remember { mutableStateOf("NIIIIIIIIIICE!") }
    var totalAddHotelAdded by remember { mutableIntStateOf(0) }
    val hotels = hotelsViewModel.uiHotels.collectAsState().value
    var imageUri: Uri? by remember { mutableStateOf(addHotelViewModel.imageUri) }

    totalAddHotelAdded = hotels.sumOf { it.roomRate }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(1.dp),
        ) {
            AddHotelText(Modifier, userName)
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                if (imageUri.toString().isNotEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = Crop,
                        modifier = Modifier.clip(CircleShape).width(100.dp).height(100.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
                HotelImagePicker(
                    onImageUriChanged = {
                        imageUri = it
                        //addHotelViewModel.updateImageUri(imageUri!!)
                    }
                )

                Spacer(modifier.weight(1f))
            }
            HotelNameInput(
                modifier = modifier,
                onHotelNameChange = { hotelName = it }
            )
            HotelCityInput(
                modifier = modifier,
                onHotelCityChange = { hotelCity = it }
            )
            HotelEmailInput(
                modifier = modifier,
                onHotelEmailChange = { hotelEmail = it }
            )
            HotelPhoneInput(
                modifier = modifier,
                onHotelPhoneChange = {
                    hotelPhone = if (it.isEmpty() || !it.isDigitsOnly()){
                        0
                    } else {
                        it.toInt()
                    }
                }
            )
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
            CommentInput(
                modifier = modifier,
                onCommentChange = { hotelComments = it }
            )
            AddHotelButton (
                modifier = modifier,
                hotel = HotelModel(
                    hotelName = hotelName,
                    hotelCity = hotelCity,
                    hotelEmail = hotelEmail,
                    hotelPhone = hotelPhone,
                    preferredPaymentType = preferredPaymentType,
                    roomRate = roomRate,
                    comment = hotelComments,
                    imageUri = imageUri.toString()
                )
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
    var hotelName by remember { mutableStateOf("Hotel name") }
    var hotelCity by remember { mutableStateOf("Hotel city") }
    var hotelEmail by remember { mutableStateOf("Hotel email") }
    var hotelPhone by remember { mutableStateOf("Hotel phone") }
    var preferredPaymentType by remember { mutableStateOf("Credit Card") }
    var roomRate by remember { mutableIntStateOf(10) }
    var hotelComments by remember { mutableStateOf("CommentA on HotelB") }
    var totalAddHotelAdded by remember { mutableIntStateOf(0) }
    var imageUri by remember { mutableStateOf("www.nothing.com") }

    totalAddHotelAdded = hotels.sumOf { it.roomRate }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            AddHotelText(modifier, "VincDefault")
            HotelNameInput(
                modifier = modifier,
                onHotelNameChange = { hotelName = it }
            )
            HotelCityInput(
                modifier = modifier,
                onHotelCityChange = { hotelCity = it }
            )
            HotelEmailInput(
                modifier = modifier,
                onHotelEmailChange = { hotelEmail = it }
            )
            HotelPhoneInput(
                modifier = modifier,
                onHotelPhoneChange = { hotelPhone = it }
            )
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
            CommentInput(
                modifier = modifier,
                onCommentChange = { hotelComments = it }
            )
            AddHotelButton (
                modifier = modifier,
                hotel = HotelModel(
                    hotelName = hotelName,
                    preferredPaymentType = preferredPaymentType,
                    roomRate = roomRate,
                    comment = hotelComments,
                    imageUri = imageUri
                ),
            )
        }
    }
}