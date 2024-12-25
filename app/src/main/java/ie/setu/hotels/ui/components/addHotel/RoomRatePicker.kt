package ie.setu.hotels.ui.components.addHotel

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import ie.setu.hotels.ui.theme.HotelsTheme

@Composable
fun RoomRatePicker(
    onRoomRateAmountChange: (Int) -> Unit
) {
    val possibleRates = listOf("10", "20", "30", "40", "50", "75", "100", "250", "500", "750", "1000")
    var pickerValue by remember { mutableStateOf(possibleRates[0]) }

    ListItemPicker(
        label = { it },
        dividersColor = MaterialTheme.colorScheme.primary,
        textStyle = TextStyle(Color.Black,20.sp),
        value = pickerValue,
        onValueChange = {
            pickerValue = it
            onRoomRateAmountChange(pickerValue.toInt())
        },
        list = possibleRates
    )
}

@Preview(showBackground = true)
@Composable
fun PickerPreview() {
    HotelsTheme {
        RoomRatePicker(onRoomRateAmountChange = {})
    }
}