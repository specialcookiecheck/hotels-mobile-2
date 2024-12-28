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
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.hotels.data.model.HotelModel
import ie.setu.hotels.ui.components.details.DetailsScreenText
import ie.setu.hotels.ui.components.details.ReadOnlyTextField
import ie.setu.hotels.ui.components.general.ShowLoader
import ie.setu.hotels.ui.theme.HotelsTheme
import timber.log.Timber.Forest.i
import kotlin.reflect.typeOf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailsViewModel = hiltViewModel()
) {
    val hotel = detailViewModel.hotel.value
    val inputErrorMessage = "Invalid input, unable to save!"
    val errorShortComment = "Comment must be at least 2 characters"
    var hotelNameText by rememberSaveable { mutableStateOf("") }
    var hotelPreferredPaymentText by rememberSaveable { mutableStateOf("") }
    var hotelRoomRateText by rememberSaveable { mutableStateOf("") }
    var commentText by rememberSaveable { mutableStateOf("") }
    var onValueChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }
    var isLowError by rememberSaveable { mutableStateOf(false) }
    var isNotIntError by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val isError = detailViewModel.isErr.value
    val error = detailViewModel.error.value
    val isLoading = detailViewModel.isLoading.value

    if(isLoading) ShowLoader("Retrieving Hotel Details...")

    fun validateString(text: String) {
        i("validateString started")
        isEmptyError = text.isEmpty()
        isShortError = text.length < 2
        onValueChanged = !(isEmptyError || isShortError)
    }

    fun validateInt(number: String) {
        i("number: $number")
        i("validateInt started")
        if(number.isNotEmpty()){
            i("not empty...")
            if(number.isDigitsOnly()) {
                i("is only digits...")
                if(number.toInt() >= 10) {
                    i("number is equal or greater than 10")
                } else {
                    i("number is less than 10")
                    isLowError = number.toInt() < 10
                }
            } else {
                i("is NOT only digits...")
                isNotIntError = !number.isDigitsOnly()
            }
        } else {
            i("number is empty")
            isEmptyError = number.isEmpty()
        }
        onValueChanged = !(isEmptyError || isLowError || isNotIntError)
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
            // hotel name edit
            hotelNameText = hotel.hotelName
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = hotelNameText,
                onValueChange = {
                    hotelNameText = it
                    validateString(hotelNameText)
                    hotel.hotelName = hotelNameText
                },
                maxLines = 2,
                label = { Text(text = "Hotel Name") },
                isError = isEmptyError || isShortError,
                supportingText = {
                    if (isEmptyError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = inputErrorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else
                        if (isShortError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorShortComment,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                },
                trailingIcon = {
                    if (isEmptyError || isShortError)
                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add or edit",
                            tint = Color.Black
                        )
                },
                keyboardActions = KeyboardActions { validateString(hotelNameText) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )

            // preferred payment edit
            hotelPreferredPaymentText = hotel.preferredPaymentType
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = hotelPreferredPaymentText,
                onValueChange = {
                    i("onValueChange started")
                    hotelPreferredPaymentText = it
                    i("attempting validation")
                    validateString(hotelPreferredPaymentText)
                    hotel.preferredPaymentType = hotelPreferredPaymentText
                },
                maxLines = 2,
                label = { Text(text = "Hotel Preferred Payment type") },
                isError = isEmptyError || isShortError,
                supportingText = {
                    if (isEmptyError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = inputErrorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else
                        if (isShortError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorShortComment,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                },
                trailingIcon = {
                    if (isEmptyError || isShortError)
                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add or edit",
                            tint = Color.Black
                        )
                },
                keyboardActions = KeyboardActions { validateString(hotelPreferredPaymentText) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )

            // room rate edit
            hotelRoomRateText = hotel.roomRate.toString()
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = hotelRoomRateText,
                onValueChange = {
                    hotelRoomRateText = it
                    validateInt(hotelRoomRateText)
                    if (hotelRoomRateText.isNotEmpty() && hotelRoomRateText.isDigitsOnly()) {
                        hotel.roomRate = hotelRoomRateText.toInt()
                    }
                },
                maxLines = 2,
                label = { Text(text = "Hotel Room rate") },
                isError = isLowError || isNotIntError || isEmptyError,
                supportingText = {
                    if (isLowError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Amount is too low",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else if (isNotIntError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Not a number!",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    else if (isEmptyError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "No value entered!",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isEmptyError || isShortError)
                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add or edit",
                            tint = Color.Black
                        )
                },
                keyboardActions = KeyboardActions { validateInt(hotelRoomRateText) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )

            // date hotel added read-only
            ReadOnlyTextField(
                value = hotel.dateAddHotelAdded.toString(),
                label = "Date Hotel added"
            )

            // comment edit
            commentText = hotel.comment
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = commentText,
                onValueChange = {
                    commentText = it
                    validateString(commentText)
                    hotel.comment = commentText
                },
                maxLines = 2,
                label = { Text(text = "Comment") },
                isError = isEmptyError || isShortError,
                supportingText = {
                    if (isEmptyError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = inputErrorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else
                        if (isShortError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorShortComment,
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
                keyboardActions = KeyboardActions { validateString(commentText) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )
            Spacer(modifier.height(height = 48.dp))
            Button(
                onClick = {
                    detailViewModel.updateHotel(hotel)
                    onValueChanged = false
                },
                elevation = ButtonDefaults.buttonElevation(20.dp),
                enabled = onValueChanged
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
fun PreviewDetailScreen(
    modifier: Modifier,
    detailViewModel: DetailsViewModel = hiltViewModel()
    ) {

    val hotel = HotelModel()
    val inputErrorMessage = "Comment Cannot be Empty..."
    val errorShortComment = "Comment must be at least 2 characters"
    var hotelNameText by rememberSaveable { mutableStateOf("") }
    var hotelPreferredPaymentText by rememberSaveable { mutableStateOf("") }
    var hotelRoomRateText by rememberSaveable { mutableStateOf("") }
    var commentText by rememberSaveable { mutableStateOf("") }
    var onValueChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }
    var isLowError by rememberSaveable { mutableStateOf(false) }

    fun validateString(text: String) {
        isEmptyError = text.isEmpty()
        isShortError = text.length < 2
        onValueChanged = true
    }

    fun validateInt(number: Int) {
        isEmptyError = number.toString().isEmpty()
        isLowError = number < 10
        onValueChanged = !(isEmptyError || isShortError)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(
            start = 10.dp,
            end = 10.dp,
        ),
    )
    {
        // hotel name edit
        hotelNameText = hotel.hotelName
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = hotelNameText,
            onValueChange = {
                hotelNameText = it
                validateString(hotelNameText)
                hotel.hotelName = hotelNameText
            },
            maxLines = 2,
            label = { Text(text = "Hotel Name") },
            isError = isEmptyError || isShortError,
            supportingText = {
                if (isEmptyError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = inputErrorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else
                    if (isShortError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorShortComment,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
            },
            trailingIcon = {
                if (isEmptyError || isShortError)
                    Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                else
                    Icon(
                        Icons.Default.Edit, contentDescription = "add or edit",
                        tint = Color.Black
                    )
            },
            keyboardActions = KeyboardActions { validateString(hotelNameText) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            )
        )

        // preferred payment edit
        hotelPreferredPaymentText = hotel.preferredPaymentType
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = hotelPreferredPaymentText,
            onValueChange = {
                hotelPreferredPaymentText = it
                validateString(hotelPreferredPaymentText)
                hotel.preferredPaymentType = hotelPreferredPaymentText
            },
            maxLines = 2,
            label = { Text(text = "Hotel Preferred Payment type") },
            isError = isEmptyError || isShortError,
            supportingText = {
                if (isEmptyError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = inputErrorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else
                    if (isShortError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorShortComment,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
            },
            trailingIcon = {
                if (isEmptyError || isShortError)
                    Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                else
                    Icon(
                        Icons.Default.Edit, contentDescription = "add or edit",
                        tint = Color.Black
                    )
            },
            keyboardActions = KeyboardActions { validateString(hotelPreferredPaymentText) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            )
        )

        // room rate edit
        hotelRoomRateText = hotel.roomRate.toString()
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = hotelRoomRateText,
            onValueChange = {
                hotelRoomRateText = it
                validateInt(hotelRoomRateText.toInt())
                hotel.roomRate = hotelRoomRateText.toInt()
            },
            maxLines = 2,
            label = { Text(text = "Hotel Room rate") },
            isError = isEmptyError || isShortError,
            supportingText = {
                if (isEmptyError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = inputErrorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else
                    if (isShortError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorShortComment,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
            },
            trailingIcon = {
                if (isEmptyError || isShortError)
                    Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                else
                    Icon(
                        Icons.Default.Edit, contentDescription = "add or edit",
                        tint = Color.Black
                    )
            },
            keyboardActions = KeyboardActions { validateInt(hotelRoomRateText.toInt()) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            )
        )

        // date hotel added read-only
        ReadOnlyTextField(
            value = hotel.dateAddHotelAdded.toString(),
            label = "Date Hotel added"
        )

        // comment edit
        commentText = hotel.comment
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = commentText,
            onValueChange = {
                commentText = it
                validateString(commentText)
                hotel.comment = commentText
            },
            maxLines = 2,
            label = { Text(text = "Comment") },
            isError = isEmptyError || isShortError,
            supportingText = {
                if (isEmptyError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = inputErrorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else
                    if (isShortError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorShortComment,
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
            keyboardActions = KeyboardActions { validateString(commentText) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            )
        )
        Spacer(modifier.height(height = 48.dp))
        Button(
            onClick = {
                detailViewModel.updateHotel(hotel)
                onValueChanged = false
            },
            elevation = ButtonDefaults.buttonElevation(20.dp),
            enabled = onValueChanged
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

