package cz.mendelu.xhalachk.smartwastebrno.communication

data class CommunicationError(
    val code: Int,
    val message: String? = null
)
