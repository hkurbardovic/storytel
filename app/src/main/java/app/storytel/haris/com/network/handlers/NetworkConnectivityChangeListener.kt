package app.storytel.haris.com.network.handlers

interface NetworkConnectivityChangeListener {

    fun onAvailable()

    fun onLost()
}