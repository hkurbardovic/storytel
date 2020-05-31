package app.storytel.haris.com.network.handlers

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import app.storytel.haris.com.di.scopes.ActivityScoped
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkConnectivityChangeHandler @Inject constructor(private val context: Context) {

    enum class ConnectionState {
        CONNECTED,
        DISCONNECTED,
    }

    private var wifiConnection = ConnectionState.DISCONNECTED
    private var cellularConnection = ConnectionState.DISCONNECTED

    private var networkConnectivityChangeListener: NetworkConnectivityChangeListener? = null

    private var networkCallbackWifi = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network?) {
            Timber.d("Network is lost")

            wifiConnection = ConnectionState.DISCONNECTED

            if (cellularConnection == ConnectionState.DISCONNECTED) {
                networkConnectivityChangeListener?.onLost()
            }
        }

        override fun onAvailable(network: Network?) {
            Timber.d("Network is available")

            wifiConnection = ConnectionState.CONNECTED

            if (cellularConnection == ConnectionState.DISCONNECTED) {
                networkConnectivityChangeListener?.onAvailable()
            }
        }
    }

    private var networkCallbackCellular = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network?) {
            Timber.d("Network is lost")

            cellularConnection = ConnectionState.DISCONNECTED

            if (wifiConnection == ConnectionState.DISCONNECTED) {
                networkConnectivityChangeListener?.onLost()
            }
        }

        override fun onAvailable(network: Network?) {
            Timber.d("Network is available")

            cellularConnection = ConnectionState.CONNECTED

            if (wifiConnection == ConnectionState.DISCONNECTED) {
                networkConnectivityChangeListener?.onAvailable()
            }
        }
    }

    fun setCallback(networkConnectivityChangeListener: NetworkConnectivityChangeListener) {
        this.networkConnectivityChangeListener = networkConnectivityChangeListener
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequestWifi = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        val networkRequestCellular = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequestWifi, networkCallbackWifi)
        connectivityManager.registerNetworkCallback(networkRequestCellular, networkCallbackCellular)
    }

    fun removeCallback() {
        this.networkConnectivityChangeListener = null
    }
}