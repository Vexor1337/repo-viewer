package com.atipera.app.infrastructure.responses

data class GetUserRepositoriesResponse(
    val name: String,
    val owner: Owner

)
data class Owner(
    val login: String
)
