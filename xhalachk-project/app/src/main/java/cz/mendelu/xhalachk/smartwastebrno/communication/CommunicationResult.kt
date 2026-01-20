package cz.mendelu.xhalachk.smartwastebrno.communication

sealed class CommunicationResult<out T: Any> {
    class Success<out T: Any>(val data: T) : CommunicationResult<T>()
    class Error(val error: CommunicationError) : CommunicationResult<Nothing>()
    class ConnectionError() : CommunicationResult<Nothing>()
    class Exception(val exception: Throwable) : CommunicationResult<Nothing>()
}