package ie.setu.hotels.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.hotels.ui.screens.about.AboutScreen
import ie.setu.hotels.ui.screens.details.DetailsScreen
import ie.setu.hotels.ui.screens.addHotel.AddHotelScreen
import ie.setu.hotels.ui.screens.home.HomeScreen
import ie.setu.hotels.ui.screens.login.LoginScreen
import ie.setu.hotels.ui.screens.map.MapScreen
import ie.setu.hotels.ui.screens.account.ProfileScreen
import ie.setu.hotels.ui.screens.register.RegisterScreen
import ie.setu.hotels.ui.screens.hotels.HotelsScreen

@Composable
fun NavHostProvider(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
    paddingValues: PaddingValues,
    permissions: Boolean,
    userName: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = AddHotel.route) {
            AddHotelScreen(
                modifier = modifier,
                userName = userName
                )
        }

        composable(route = Home.route) {
            HomeScreen(modifier = modifier)
        }
        composable(route = Hotels.route) {
            HotelsScreen(
                modifier = modifier,
                onClickHotelDetails = {
                    hotelId : String ->
                    navController.navigateToHotelDetails(hotelId)
                },
                userName = userName
            )
        }
        composable(route = About.route) {
            AboutScreen(modifier = modifier)
        }

        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
                onLogin = { navController.popBackStack() }
            )
        }

        composable(route = Register.route) {
            RegisterScreen(
                navController = navController,
                onRegister = { navController.popBackStack() }
            )
        }

        composable(
            route = Details.route,
            arguments = Details.arguments
        )
        { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString(Details.idArg)
            if (id != null) {
                DetailsScreen()
            }
        }

        composable(route = Account.route) {
            ProfileScreen(
                onSignOut = {
                    navController.popBackStack()
                    navController.navigate(Login.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                },
            )
        }

        composable(route = Map.route) {
            MapScreen(permissions = permissions)
        }
    }
}

private fun NavHostController.navigateToHotelDetails(hotelId: String) {
    this.navigate("details/$hotelId")
}

