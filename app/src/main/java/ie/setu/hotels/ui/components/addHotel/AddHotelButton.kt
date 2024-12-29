package ie.setu.hotels.ui.components.addHotel

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.hotels.R
import ie.setu.hotels.data.model.HotelModel
import ie.setu.hotels.data.model.fakeHotels
import ie.setu.hotels.ui.screens.addHotel.AddHotelViewModel
import ie.setu.hotels.ui.screens.map.MapViewModel
import ie.setu.hotels.ui.screens.hotels.HotelsViewModel
import ie.setu.hotels.ui.theme.HotelsTheme
import timber.log.Timber.Forest.i

@Composable
fun AddHotelButton(
    modifier: Modifier = Modifier,
    hotel: HotelModel,
    addHotelViewModel: AddHotelViewModel = hiltViewModel(),
    hotelsViewModel: HotelsViewModel = hiltViewModel(),
    mapViewModel: MapViewModel = hiltViewModel(),
) {
    val hotels = hotelsViewModel.uiHotels.collectAsState().value
    val context = LocalContext.current
    stringResource(R.string.limitExceeded,hotel.roomRate)

    val isError = addHotelViewModel.isErr.value
    val error = addHotelViewModel.error.value
    val locationLatLng = mapViewModel.currentLatLng.collectAsState().value

    LaunchedEffect(mapViewModel.currentLatLng){
        mapViewModel.getLocationUpdates()
    }

    i("AddHotel BUTTON LAT/LNG COORDINATES " +
            "lat/Lng: " + "$locationLatLng ")

    Row {
        Button(
            onClick = {
                i("button clicked")
                i("hotel: $hotel")
                val hotelLatLng = hotel.copy(
                    latitude = locationLatLng.latitude,
                    longitude = locationLatLng.longitude
                )
                addHotelViewModel.insert(hotelLatLng)
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Hotel")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.addHotelButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }

    i("AddHotel Button = : ${error.message}")
    //Required to refresh our 'totalAddHotelAdded'
    if(isError)
        Toast.makeText(context,"Unable to add hotel at this time!",
            Toast.LENGTH_SHORT).show()
    else
        hotelsViewModel.getHotels()
}

@Preview(showBackground = true)
@Composable
fun AddHotelButtonPreview() {
    HotelsTheme {
        PreviewAddHotelButton(
            Modifier,
            HotelModel(),
            hotels = fakeHotels.toMutableStateList()
        ) {}
    }
}

@Composable
fun PreviewAddHotelButton(
    modifier: Modifier = Modifier,
    hotel: HotelModel,
    hotels: SnapshotStateList<HotelModel>,
    onTotalAddHotelAddedChange: (Int) -> Unit
) {

    var totalAddHotelAdded = hotels.sumOf { it.roomRate }
    val context = LocalContext.current
    val comment = stringResource(R.string.limitExceeded,hotel.roomRate)

    Row {
        Button(
            onClick = {
                if(totalAddHotelAdded + hotel.roomRate <= 10000) {
                    totalAddHotelAdded+=hotel.roomRate
                    onTotalAddHotelAddedChange(totalAddHotelAdded)
                    hotels.add(hotel)
                    i("Hotel info : $hotel")
                    i("Hotel List info : ${hotels.toList()}")
                }
                else
                    Toast.makeText(context,comment,
                        Toast.LENGTH_SHORT).show()
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "AddHotel")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.addHotelButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier.weight(1f))
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(R.string.total) + " €")
                }


                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)
                ) {
                    append(totalAddHotelAdded.toString())
                }
            })
    }
}