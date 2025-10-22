package com.cristiancogollo.unabstore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.auth
import com.google.firebase.Firebase




@Composable
fun NavigationApp (){
    val navController = rememberNavController()
    var startDestination: String = "login"

    val auth = Firebase.auth
    val currentUser =auth.currentUser


    if (currentUser!=null){
        startDestination="home"
    }else{
        startDestination="login"
    }


    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable( "login") {
            LoginScreen(onClickRegister = {
                navController.navigate("register")
            },onSuccessfullLogin ={
                navController.navigate("home"){
                    popUpTo("login"){inclusive=true}
                }
            })
        }
        composable( "register") {
            RegisterScreen(onClickBack ={
                navController.popBackStack()
            }, onSuccessfullRegister = {
                navController.navigate("home"){
                    popUpTo(0)
                }
            })
        }
        composable( "home") {
            HomeScreen(onClickLogout = {
                navController.navigate("login"){
                    popUpTo(0)
                }
            })
        }
    }
}