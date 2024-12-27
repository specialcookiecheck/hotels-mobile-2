package ie.setu.hotels.ui.components.general

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowHotelImagePicker(
    onImageUriChanged: (Uri) -> Unit,
) {
    val context = LocalContext.current
    var imageUri: Uri? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            val name = context.packageName
            context.grantUriPermission(name, uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION)
            onImageUriChanged(uri)
        }
        imageUri = uri
    }

    Column {
        Button(
            onClick = {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts
                            .PickVisualMedia.ImageOnly
                    )
                )
            }
        ) {
            Text("Choose Hotel image")
        }
    }
}
