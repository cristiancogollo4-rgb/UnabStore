package com.cristiancogollo.unabstore

import android.util.Patterns
import kotlin.io.path.Path


fun validationEmail(email: String): Pair<Boolean, String>{
    return when{

        email.isEmpty()-> Pair(false, "El correo es requerido")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(false,"El correo es invalido")
        !email.endsWith("@unab.edu.co") -> Pair(false, "El correo no es coorporativo")
        else -> Pair(true,"")
    }

}

fun validationPassword(password:String): Pair<Boolean, String>{
    return when{
        password.isEmpty() -> Pair(false,"La contraseña es requerida")
        password.length<6 -> Pair(false,"La contraseña debe tener minimo 6 caracteres")
        !password.any {it.isDigit()} -> Pair(false,"La contraseña debe tener almenos un numero")
        else -> Pair(true,"")

    }
}
fun validationName (name: String): Pair<Boolean, String>{
    return when {
        name.isEmpty() -> Pair(false, "El nombre es requerido")
        else -> Pair(true, "")

    }
}
fun validationPassword2 (Password: String,Password2: String ): Pair<Boolean, String>{
    return when{
        Password2.isEmpty() -> Pair(false, "Digite la contraseña")
        Password2 != Password-> Pair(false, "La contraseña no coincide")
        else -> Pair(true, "")
    }

}