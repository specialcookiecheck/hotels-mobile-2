package ie.setu.hotels.ui.components.addHotel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.hotels.R
import ie.setu.hotels.ui.theme.HotelsTheme

@Composable
fun RadioButtonGroup(modifier: Modifier = Modifier,
                     onPreferredPaymentTypeChange: (String) -> Unit) {

    val radioOptions = listOf(
        stringResource(R.string.cash),
        stringResource(R.string.credit_card),
        stringResource(R.string.hotel_credit),
        stringResource(R.string.traveller_cheques)
    )
    var preferredPaymentType by remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        radioOptions.forEach { preferredPaymentText ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (preferredPaymentText == preferredPaymentType),
                    onClick = {
                        preferredPaymentType = preferredPaymentText
                        onPreferredPaymentTypeChange(preferredPaymentType)
                    }
                )
                Text(
                    text = preferredPaymentText,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RadioButtonPreview() {
    HotelsTheme {
        RadioButtonGroup(
            Modifier,
            onPreferredPaymentTypeChange = {}
        )
    }
}
