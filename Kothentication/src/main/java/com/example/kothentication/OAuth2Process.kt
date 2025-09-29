package com.example.kothentication
import com.example.kothentication.httputilities.HttpClient
import com.example.kothentication.httputilities.OkHttpAdapter
import com.example.kothentication.tokenmanagement.Token


internal class OAuth2Process(
    val client : HttpClient = OkHttpAdapter(),  // client used for all HTTP connections
    val authProvider : AuthenticationProvider   // provide used for authentications
) {

    /**
     * A method for building the URL that identifies an authorization request
     * @param clientId ID associated to the client for using the provider's authentication server
     * @param redirectURI address where user should be redirected after login
     * @param scope resources for which the user will be asked access permission
     */
    fun buildAuthorizationAccessURL(
        clientId : String,
        redirectURI : String,
        scope : List<String>? = null) : String {


        val url = client.buildAuthorizationUrl(base = authProvider.authorizationEndpoint, clientId, redirectURI, scope = scope)
        return url;
    }


    /**
     * Post a request for an access token by providing an authorization code
     * @param url token endpoint
     * @param code the authorization code
     * @return an access Token
     */
    fun exchangeCodeForToken(url : String = authProvider.tokenEndpoint, code : String, redirectUri : String, clientId : String) : Token{

        val params = mapOf(
            "grant_type" to "authorization_code",
            "code" to code,
            "redirect_uri" to redirectUri,
            "client_id" to clientId,
        )

        return client.post(url, params)
    }
} 