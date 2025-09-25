package com.example.kothentication

/**
 * Class containing data made available by an authentication provider to developers
 */
class AuthenticationProvider(
    val name : String,  // name used to easily identify the provider
    val authorizationEndpoint : String, // representing the provider's Authorization Endpoint
    val tokenEndpoint : String  // representing the provider's Token Endpoint
) {

}