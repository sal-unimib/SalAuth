package com.example.kothentication.tokenmanagement

import com.google.gson.annotations.SerializedName

/**
 * A class representing a token
 */
class Token(
    @SerializedName("access_token")
    val accessToken : String,

    @SerializedName("token_type")
    val tokenType : String,

    @SerializedName("refresh_token")
    val refreshToken : String,

    @SerializedName("expires_in")
    var timeLeft : Int
){

}