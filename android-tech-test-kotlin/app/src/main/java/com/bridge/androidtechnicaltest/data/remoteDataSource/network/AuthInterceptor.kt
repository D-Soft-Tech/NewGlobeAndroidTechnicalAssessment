package com.bridge.androidtechnicaltest.data.remoteDataSource.network

import com.bridge.androidtechnicaltest.di.qualifiers.AppAgentIdScope
import com.bridge.androidtechnicaltest.di.qualifiers.AppRequestIdScope
import com.bridge.androidtechnicaltest.utils.AppConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @AppRequestIdScope private val requestId: String,
    @AppAgentIdScope private val agentId: String,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder = chain.request().newBuilder().apply {
            addHeader(AppConstants.REQUEST_ID_TAG, requestId)
            addHeader(AppConstants.AGENT_ID_TAG, agentId)
        }
        return chain.proceed(requestBuilder.build())
    }
}