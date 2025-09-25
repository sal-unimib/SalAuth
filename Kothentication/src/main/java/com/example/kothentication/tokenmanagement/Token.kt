package com.example.kothentication.tokenmanagement

/**
 * A class representing a token
 */
class Token(
    val accessToken : String,
    val tokenType : String,
    val refreshToken : String,
    var timeLeft : Int
){

}