package com.example.kothentication.tokenmanagement


/**
 * A class to manage saved tokens, starting requests
 * for a new token when the current one expires
 */
class TokenManager{

    private val repository = MapTokenRepository()

    fun getToken(userId : String) : Token?{
        return repository.getToken(userId)
    }


    fun addToken(userId: String, token: Token){
        repository.saveToken(userId, token)
    }


    fun deleteToken(userId : String){
        repository.removeToken(userId)
    }


    fun refreshToken(userId : String){

    }
}