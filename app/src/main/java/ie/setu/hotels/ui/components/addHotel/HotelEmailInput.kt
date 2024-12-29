package ie.setu.hotels.ui.components.addHotel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.hotels.R
import ie.setu.hotels.ui.theme.HotelsTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun HotelEmailInput(
    modifier: Modifier = Modifier,
    onHotelEmailChange: (String) -> Unit
) {

    var hotelEmail by remember { mutableStateOf("") }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 2,
        value = hotelEmail,
        onValueChange = {
            hotelEmail = it
            onHotelEmailChange(hotelEmail)
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.enter_hotel_email)) }
    )
}
