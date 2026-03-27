package com.rainyday.momentapp.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rainyday.momentapp.features.auth.ui.screens.register.RegisterScreen
import com.rainyday.momentapp.features.auth.ui.screens.register.SignInScreen

@Composable
fun AuthGraph(
    navHostController: NavHostController,
    showSnackBar: (String) -> Unit,
    onSuccess: () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = AuthRoute.SignIn,
        builder = {
            composable(route = AuthRoute.SignIn.route) {
                SignInScreen(
                    showSnackBar = showSnackBar,
                    onSuccess = onSuccess,
                )
            }

            composable(route = AuthRoute.Register.route) {
                RegisterScreen(
                    showSnackBar = showSnackBar,
                    onSuccess = onSuccess,
                )
            }
        }
    )
}

sealed class AuthRoute(val route: String) {
    data object SignIn: AuthRoute(SIGN_IN_ROUTE)
    data object Register: AuthRoute(REGISTER_ROUTE)
}

const val SIGN_IN_ROUTE = "sign_in"
const val REGISTER_ROUTE = "register"