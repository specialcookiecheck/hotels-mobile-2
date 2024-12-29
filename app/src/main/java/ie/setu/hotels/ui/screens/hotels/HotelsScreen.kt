package ie.setu.hotels.ui.screens.hotels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.hotels.R
import ie.setu.hotels.data.model.HotelModel
import ie.setu.hotels.data.model.fakeHotels
import ie.setu.hotels.ui.components.general.Centre
import ie.setu.hotels.ui.components.general.ShowError
import ie.setu.hotels.ui.components.general.ShowLoader
import ie.setu.hotels.ui.components.hotels.HotelCardList
import ie.setu.hotels.ui.components.hotels.HotelsText
import ie.setu.hotels.ui.theme.HotelsTheme
import timber.log.Timber


@Composable
fun HotelsScreen(modifier: Modifier = Modifier,
                 onClickHotelDetails: (String) -> Unit,
                 hotelsViewModel: HotelsViewModel = hiltViewModel(),
                 userName: String
) {

    val hotels = hotelsViewModel.uiHotels.collectAsState().value
    val isError = hotelsViewModel.iserror.value
    val error = hotelsViewModel.error.value
    val isLoading = hotelsViewModel.isloading.value

    Timber.i("RS : Hotels List = $hotels")

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            if(isLoading) ShowLoader("Loading Hotels...")
            HotelsText(modifier, userName)
            if (hotels.isEmpty() && !isError)
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            if (!isError) {
                HotelCardList(
                    hotels = hotels,
                    onClickHotelDetails = onClickHotelDetails,
                    onDeleteHotel = { hotel: HotelModel ->
                        hotelsViewModel.deleteHotel(hotel)
                    },
                )
            }
            if (isError) {
                ShowError(headline = error.message!! + " error...",
                    subtitle = error.toString(),
                    onClick = { hotelsViewModel.getHotels() })
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HotelsScreenPreview() {
    HotelsTheme {
        PreviewHotelsScreen( modifier = Modifier,
            hotels = fakeHotels.toMutableStateList()
        )
    }
}

@Composable
fun PreviewHotelsScreen(modifier: Modifier = Modifier,
                        hotels: SnapshotStateList<HotelModel>
) {

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            HotelsText(userName = "VincPreview")
            if(hotels.isEmpty())
                Centre(Modifier.fillMaxSize()) {
                    Text(color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            else
                HotelCardList(
                    hotels = hotels,
                    onDeleteHotel = {}
                ) { }
        }
    }
}