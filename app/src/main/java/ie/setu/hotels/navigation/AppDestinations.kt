package ie.setu.hotels.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object Hotels : AppDestination {
    override val icon = Icons.Filled.Checklist
    override val label = "Hotels"
    override val route = "hotels"
}

object AddHotel : AppDestination {
    override val icon = Icons.Filled.Hotel
    override val label = "Add Hotel"
    override val route = "addHotel"
}

object About : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.HelpCenter
    override val label = "About"
    override val route = "about"
}

object Details : AppDestination {
    override val icon = Icons.Filled.Details
    override val label = "Hotel details"
    const val idArg = "id"
    override val route = "details/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.StringType }
    )
}

object Home : AppDestination {
    override val icon = Icons.Filled.Home
    override val label = "Home"
    override val route = "home"
}

object Account : AppDestination {
    override val icon = Icons.Default.ManageAccounts
    override val label = "Account"
    override val route = "account"
}

object Login : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.Login
    override val label = "Login"
    override val route = "login"
}

object Register : AppDestination {
    override val icon = Icons.Default.AppRegistration
    override val label = "Register"
    override val route = "register"
}

object Map : AppDestination {
    override val icon = Icons.Filled.GpsFixed
    override val label = "Map"
    override val route = "map"
}


val bottomAppBarDestinations = listOf(Hotels, Map, AddHotel, Account, About)
val userSignedOutDestinations = listOf(Login, Register)
val allDestinations = listOf(Hotels, AddHotel, About, Details,
                                      Home, Account, Login, Register, Map)


