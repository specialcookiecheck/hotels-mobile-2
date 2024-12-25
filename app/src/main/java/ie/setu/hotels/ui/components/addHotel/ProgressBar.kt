package ie.setu.hotels.ui.components.addHotel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.hotels.ui.theme.HotelsTheme

@Composable
fun ProgressBar(modifier: Modifier = Modifier,
                totalAddHotelAdded: Int,) {
    var currentProgress by remember { mutableFloatStateOf(0f) }

    currentProgress = totalAddHotelAdded / 10000f

    LinearProgressIndicator(
        progress = { currentProgress },
        modifier = modifier.padding(top = 80.dp,bottom = 24.dp)
            .height(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = ProgressIndicatorDefaults.linearTrackColor,
        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
    )

}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview(){
    HotelsTheme {
        ProgressBar(Modifier, totalAddHotelAdded = 1000)
    }
}