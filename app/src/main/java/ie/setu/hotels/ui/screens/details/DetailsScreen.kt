package ie.setu.hotels.ui.screens.details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.hotels.data.model.HotelModel
import ie.setu.hotels.ui.components.details.DetailsScreenText
import ie.setu.hotels.ui.components.details.ReadOnlyTextField
import ie.setu.hotels.ui.components.general.ShowLoader
import ie.setu.hotels.ui.theme.HotelsTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailsViewModel = hiltViewModel()
) {
    val hotel = detailViewModel.hotel.value
    val errorEmptyMessage = "Message Cannot be Empty..."
    val errorShortMessage = "Message must be at least 2 characters"
    var text by rememberSaveable { mutableStateOf("") }
    var onMessageChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val isError = detailViewModel.isErr.value
    val error = detailViewModel.error.value
    val isLoading = detailViewModel.isLoading.value

    if(isLoading) ShowLoader("Retrieving Hotel Details...")

    fun validate(text: String) {
        isEmptyError = text.isEmpty()
        isShortError = text.length < 2
        onMessageChanged = !(isEmptyError || isShortError)
    }

    if(isError)
        Toast.makeText(context,"Unable to fetch Details at this Time...",
            Toast.LENGTH_SHORT).show()
    if(!isError && !isLoading)
        Column(modifier = modifier.padding(
            start = 24.dp,
            end = 24.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        DetailsScreenText()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(
                start = 10.dp,
                end = 10.dp,
            ),
        )
        {
            //Payment Type Field
            ReadOnlyTextField(value = hotel.preferredPaymentType,
                label = "Payment Type")
            //Payment Amount Field
            ReadOnlyTextField(value = "€" + hotel.roomRate.toString(),
                label = "Payment Amount")
            //Date AddHotelAdded Field
            ReadOnlyTextField(value = hotel.dateAddHotelAdded.toString(),
                label = "Date AddHotelAdded")
            //Message Field
            text = hotel.message
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                    validate(text)
                    hotel.message = text
                },
                maxLines = 2,
                label = { Text(text = "Message") },
                isError = isEmptyError || isShortError,
                supportingText = {
                    if (isEmptyError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorEmptyMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else
                        if (isShortError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorShortMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                },
                trailingIcon = {
                    if (isEmptyError || isShortError)
                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add/edit",
                            tint = Color.Black
                        )
                },
                keyboardActions = KeyboardActions { validate(text) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )
            //End of Message Field
            Spacer(modifier.height(height = 48.dp))
            Button(
                onClick = {
                    detailViewModel.updateHotel(hotel)
                    onMessageChanged = false
                },
                elevation = ButtonDefaults.buttonElevation(20.dp),
                enabled = onMessageChanged
            ){
                Icon(Icons.Default.Save, contentDescription = "Save")
                Spacer(modifier.width(width = 8.dp))
                Text(
                    text = "Save",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    HotelsTheme {
        PreviewDetailScreen(modifier = Modifier)
    }
}

@Composable
fun PreviewDetailScreen(modifier: Modifier) {

    val hotel = HotelModel()
    val errorEmptyMessage = "Message Cannot be Empty..."
    val errorShortMessage = "Message must be at least 2 characters"
    var text by rememberSaveable { mutableStateOf("") }
    var onMessageChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }

    fun validate(text: String) {
        isEmptyError = text.isEmpty()
        isShortError = text.length < 2
        onMessageChanged = true
    }

    Column(
        modifier = modifier.padding(
            start = 10.dp,
            end = 10.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        DetailsScreenText()
        //           Row (modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                ),
        )
        {
            //Payment Type Field
            OutlinedTextField(modifier = modifier.fillMaxWidth(),
                value = hotel.preferredPaymentType,
                onValueChange = { },
                label = { Text(text = "Payment Type") },
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )
            //Payment Amount Field
            OutlinedTextField(modifier = modifier.fillMaxWidth(),
                value = "€" + hotel.roomRate.toString(),
                onValueChange = { },
                label = { Text(text = "Payment Amount") },
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )
            //Date AddHotelAdded Field
            OutlinedTextField(modifier = modifier.fillMaxWidth(),
                value = hotel.dateAddHotelAdded.toString(),
                onValueChange = { },
                label = { Text(text = "Date AddHotelAdded") },
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )
            //  Log.i("VM Call","Message is : ${hotel.message}")
            //Message Field
            text = hotel.message
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                    validate(text)
                    hotel.message = text
                },
                maxLines = 2,
                label = { Text(text = "Message") },
                isError = isEmptyError || isShortError,
                supportingText = {
                    if (isEmptyError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorEmptyMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else
                        if (isShortError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorShortMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                },
                trailingIcon = {
                    if (isEmptyError || isShortError)
                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add/edit",
                            tint = Color.Black
                        )
                },
                keyboardActions = KeyboardActions { validate(text) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )
            Spacer(modifier.height(height = 48.dp))
            Button(
                onClick = {
                    onMessageChanged = false
                },
                elevation = ButtonDefaults.buttonElevation(20.dp),
                enabled = onMessageChanged
            ){
                Icon(Icons.Default.Save, contentDescription = "Save")
                Spacer(modifier.width(width = 8.dp))
                Text(
                    text = "Save",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}
