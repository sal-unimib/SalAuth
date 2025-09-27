package com.example.kothentication
import com.example.kothentication.tokenmanagement.Token
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson


interface HttpClient{
    fun post(url : String, body : Map<String, String>) : Token
    fun parseResponse(responseBody: String): Token
    fun buildAuthorizationUrl(
        base : String,
        clientId : String,
        redirect : String,
        responseType : String = "code",
        scope : List<String>? = null) : String
}


/**
 * Implementation of HttpClient that allows encapsulation for the use of OkHttp library.
 */
class OkHttpAdapter(
    val client : OkHttpClient = OkHttpClient()
) : HttpClient{

    /**
     * The post method makes requests following the format specified
     * in paragraphs 4.1.1 and 4.1.3 of RFC 6749.
     * @param url string identifying the token endpoint
     * @param body parameters to add in the request url
     * @return a Token object
     */
    override fun post(url : String, body : Map<String, String>) : Token {
        var requestFormBuilder = FormBody.Builder()

        body.forEach { entry -> requestFormBuilder.add(entry.key, entry.value) }

        val requestForm = requestFormBuilder.build()

        val request = Request.Builder()
            .url(url)
            .post(requestForm)
            .build()

        client.newCall(request).execute().use { response ->
            val rawBody = response.body.string()
            return parseResponse(rawBody)
        }
    }


    /**
     * @param responseBody body of an HTTP response
     * @return a Token object, parsed from the response through use of the Gson library
     */
    override fun parseResponse(responseBody: String): Token {

        val JSONparser = Gson()
        val token : Token = JSONparser.fromJson(responseBody, Token::class.java)

        return token
    }


    /**
     * Method for building an url that follows URL standard
     * @param base first part of the url
     * @param clientId ID associated to the client for using the provider's authentication server
     * @param redirect address where user should be redirected after login
     * @param responseType specifies that the client will receive an authorization code
     * @param scope resources for which the user will be asked access permission
     */
    override fun buildAuthorizationUrl(
        base : String,
        clientId : String,
        redirect : String,
        responseType : String,
        scope : List<String>?) : String{

        val queryBuilder = base.toHttpUrl().newBuilder()
        queryBuilder.addQueryParameter("client_id", clientId)
            .addQueryParameter("responseType", responseType)

        // ensuring the redirect URI is formatted according to URL standards
        val parsedRedirect = redirect.toHttpUrl()

        queryBuilder.addQueryParameter("redirect_uri", parsedRedirect.toString())

        if(scope != null) {
            queryBuilder.addQueryParameter("scope", scope.joinToString(" "))
        }

        return queryBuilder.build().toString()
    }
}