package com.example.kothentication.tokenmanagement

/**
 * A repository to store tokens
 */
abstract class TokenRepository{
   internal abstract fun saveToken(userId: String, token : Token)

   internal abstract fun getToken(userId : String) : Token?

   internal abstract fun removeToken(userId : String)
}


/**
 * Simple implementation for the repository
 */
class MapTokenRepository : TokenRepository(){
    private val tokens : MutableMap<String, Token> = mutableMapOf<String, Token>()

    override fun saveToken(userId : String, token: Token) {
        tokens[userId] = token
    }

    override fun getToken(userId: String) : Token?{
        return tokens[userId]
    }

    override fun removeToken(userId: String) {
        tokens.remove(userId)
    }
}