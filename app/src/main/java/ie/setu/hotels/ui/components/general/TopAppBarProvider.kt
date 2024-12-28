package ie.setu.hotels.ui.components.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ie.setu.hotels.navigation.AppDestination
import ie.setu.hotels.navigation.AddHotel
import ie.setu.hotels.ui.theme.HotelsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarProvider(
    navController: NavController,
    currentScreen: AppDestination,
    canNavigateBack: Boolean,
    email: String,
    name: String,
    navigateUp: () -> Unit = {}
)
{
    TopAppBar(
        title = {
            Column {
                Row {
                    if (name.isNotEmpty())
                        Text(
                            text = "$name's ",
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    Text(
                        text = currentScreen.label,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row {
                    if (email.isNotEmpty())
                        Text(
                            text = " ($email)",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            else
                IconButton(onClick = {},
                    content = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = Color.White
                    )
                })

        },
        actions = { DropDownMenu(navController = navController) }
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    HotelsTheme {
        TopAppBarProvider(
            navController = rememberNavController(),
            AddHotel,
            true,
            email = "vinc@gmail.com",
            name = "vincdefault!!"
        )
    }
}