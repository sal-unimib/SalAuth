package com.salauth.koffe


/**
 * Facade which ensures the user can use the library's functionalities in a protected way.
 * Upon creation the user provides the CLIENT_ID used by the app to access the API
 * and the redirect_uri used in authorization and access token request
 */
class Authenticator(
    private val clientId : String,
    private val redirect : String
) {

    private lateinit var authenticationProcess : OAuth2Process


    fun registerAuthenticationService(providerName : String, authEndpoint : String, tokenEndpoint : String){
        val provider = AuthenticationProvider(providerName, authEndpoint, tokenEndpoint)
        authenticationProcess = OAuth2Process(authProvider = provider)
    }


    /**
     * @return the URL that the user will use for authentication
     */
    fun getAuthenticationUrl(scope : List<String>, state : String) : String{

        return authenticationProcess.buildAuthorizationAccessURL(clientId, redirect, scope)
    }


    /**
     * @param code an authorization code
     */
    fun obtainToken(authCode : String){

        val token = authenticationProcess.exchangeCodeForToken(code = authCode, redirectUri = redirect, clientId = clientId)
        // TODO store the token. Revaluate link between the Facade and TokenManager
    }
}