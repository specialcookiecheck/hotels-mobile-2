package ie.setu.hotels.ui.components.hotels

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ie.setu.hotels.R
import ie.setu.hotels.ui.theme.HotelsTheme
import ie.setu.hotels.ui.theme.endGradientColor
import ie.setu.hotels.ui.theme.startGradientColor
import java.text.DateFormat
import java.util.Date

@Composable
fun HotelCard(
    hotelName: String,
    preferredPaymentType: String,
    roomRate: Int,
    comment: String,
    latitude: Double,
    longitude: Double,
    dateCreated: String,
    dateModified: String,
    onClickDelete: () -> Unit,
    onClickHotelDetails: () -> Unit,
    imageUri: Uri
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 2.dp)
    ) {
        HotelCardContent(
            hotelName,
            preferredPaymentType,
            roomRate,
            comment,
            latitude,
            longitude,
            dateCreated,
            dateModified,
            onClickDelete,
            onClickHotelDetails,
            imageUri
        )
    }
}

@Composable
private fun HotelCardContent(
    hotelName: String,
    preferredPaymentType: String,
    roomRate: Int,
    comment: String,
    latitude: Double,
    longitude: Double,
    dateCreated: String,
    dateModified: String,
    onClickDelete: () -> Unit,
    onClickHotelDetails: () -> Unit,
    imageUri: Uri
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(2.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .background(brush = Brush.horizontalGradient(
                colors = listOf(
                    startGradientColor,
                    endGradientColor,
                )
            ))
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = hotelName, style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Text(
                text = comment, style = MaterialTheme.typography.headlineMedium
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = preferredPaymentType,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "€$roomRate",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            Text(
                text = "$latitude, $longitude", style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "AddHotelAdded $dateCreated", style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "Modified $dateModified", style = MaterialTheme.typography.labelSmall
            )
            if (expanded) {
                Text(modifier = Modifier.padding(vertical = 16.dp), text = comment)
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    FilledTonalButton(onClick = onClickHotelDetails) {
                        Text(text = "Show More")
                    }

                    FilledTonalIconButton(onClick = {
                        showDeleteConfirmDialog = true
                    }) {
                        Icon(Icons.Filled.Delete, "Delete Hotel")
                    }

                    if (showDeleteConfirmDialog) {
                        showDeleteAlert(
                            onDismiss = { showDeleteConfirmDialog = false },
                            onDelete = onClickDelete,
                        )
                    }
                }
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess
                                    else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Composable
fun showDeleteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss ,
        title = { Text(stringResource(id = R.string.confirm_delete)) },
        text = { Text(stringResource(id = R.string.confirm_delete_message)) },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
                }
            ) { Text("Yes") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("No") }
        }
    )
}


@Preview
@Composable
fun HotelCardPreview() {
    HotelsTheme {
        HotelCard(
            hotelName = "Default hotel",
            preferredPaymentType = "Cash",
            roomRate = 100,
            comment = """
                Fav hotel comment"
            """.trimIndent(),
            dateCreated = DateFormat.getDateTimeInstance().format(Date()),
            dateModified = DateFormat.getDateTimeInstance().format(Date()),
            onClickDelete = { },
            onClickHotelDetails = {},
            imageUri = Uri.EMPTY,
            latitude = 1.0,
            longitude = -1.0,
        )
    }
}