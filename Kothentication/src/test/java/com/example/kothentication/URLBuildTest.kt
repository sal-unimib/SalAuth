package com.example.kothentication

import com.example.kothentication.httputilities.OkHttpAdapter
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Test
import org.junit.Assert.*

class URLBuildTest {

    private val fauxProvider = AuthenticationProvider(
        name = "JustAProvider",
        authorizationEndpoint = "https://JustAProvider.com/login",
        tokenEndpoint = "https://JustAProvider.com/token"
    )

    private val testProcess = OAuth2Process(OkHttpAdapter(), fauxProvider)

    @Test fun URLBuild_resultConformToStandards(){

        val fauxLink = testProcess.buildAuthorizationAccessURL(
            clientId = "just a client",
            scope = listOf("email", "username"),
            redirectURI = "https://justaprovider.com/redirectTo destination"
        )

        val fauxUrl = fauxLink.toHttpUrl()
        assertEquals("JustAProvider.com".lowercase(), fauxUrl.host)
        assertEquals("/login", fauxUrl.encodedPath)
        assertEquals("just a client", fauxUrl.queryParameter("client_id"))
        assertTrue(fauxLink.toString().contains("client_id=just%20a%20client"))
        assertEquals("email username", fauxUrl.queryParameter("scope"))
        assertEquals("https://justaprovider.com/redirectTo%20destination", fauxUrl.queryParameter("redirect_uri"))
    }
}