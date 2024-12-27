package ie.setu.hotels.ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.hotels.R
import ie.setu.hotels.ui.components.general.Centre
import ie.setu.hotels.ui.theme.HotelsTheme

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {

    Column(
            // modifier = modifier.background(MaterialTheme.colorScheme.primary),
        ) {
            Centre(Modifier
                    .fillMaxWidth()
                .padding(top = 48.dp,)
            ) {
                Image(
                    // painter = painterResource(id = R.drawable.aboutus_homer_1),
                    painter = painterResource(id = R.drawable.about_hotels),
                    contentDescription = "about image",
                    modifier = Modifier.size(350.dp)
                )
            }
            Centre(Modifier.fillMaxSize()) {
                Text(color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 34.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.about_message)
                    )
                }
        }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    HotelsTheme {
        AboutScreen( modifier = Modifier)
    }
}