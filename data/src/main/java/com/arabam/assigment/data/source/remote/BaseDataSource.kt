package com.arabam.assigment.data.source.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.arabam.assigment.domain.model.error.BaseError
import com.arabam.assigment.domain.model.response.DataSourceResponse
import retrofit2.Response

abstract class BaseDataSource(private val context: Context) {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): DataSourceResponse<T> {

        return if (context.isOnline()) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return DataSourceResponse.Success(body)
                }
                return error(" ${response.code()} ${response.message()}")
            } catch (e: Exception) {
                return error(e.localizedMessage ?: e.toString())
            }
        } else {
            DataSourceResponse.Error(BaseError("Error internet connection"))
        }
    }

    private fun <T> error(message: String): DataSourceResponse<T> {
        return DataSourceResponse.Error(BaseError("Error: $message"))
    }

    private fun Context.isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }
}
