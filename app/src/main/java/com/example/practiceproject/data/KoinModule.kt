package com.example.practiceproject.data

import android.content.Context
import android.net.ConnectivityManager
import com.example.practiceproject.domain.CountryRepo
import com.example.practiceproject.ui.screens.LoginScreenViewModel
import com.example.practiceproject.core.data.utilities.NetworkMonitor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private fun provideNetworkMonitor(connectivityManager: ConnectivityManager) =
    NetworkMonitor(connectivityManager)

private fun provideHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS)
        .connectTimeout(3, TimeUnit.SECONDS).build()
}

private fun provideGsonConversion(): GsonConverterFactory = GsonConverterFactory.create()

private fun provideRetrofit(httpClient: OkHttpClient, gson: GsonConverterFactory): Retrofit {
    return Retrofit.Builder().baseUrl("https://datausa.io/api/").client(httpClient)
        .addConverterFactory(gson).build()
}

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

val networkModule = module {
    single { provideNetworkMonitor(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) }
    single { provideHttpClient() }
    single { provideGsonConversion() }
    single { provideRetrofit(get(), get()) }
    single { provideApiService(get()) }
}

private fun provideCountryRepo(apiService: ApiService): CountryRepo = CountryRepoImpl(apiService)

val repoModule = module {
    factory { provideCountryRepo(get()) }
}

private fun provideLoginScreenViewModel(repo: CountryRepo) = LoginScreenViewModel(repo)

val loginViewModelModule = module { viewModel { provideLoginScreenViewModel(get()) } }


// To inject instance in high level functions
inline fun <reified T> getKoinInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}