package app.storytel.candidate.com.network.handlers

interface NetworkConnectivityChangeListener {

    fun onAvailable()

    fun onLost()
}