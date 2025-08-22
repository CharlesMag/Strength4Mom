package com.example.strength4mom.di

import android.util.Log
import androidx.room.Room
import com.example.strength4mom.BuildConfig
import com.example.strength4mom.data.local.notes.NotesDao
import com.example.strength4mom.data.local.notes.NotesDatabase
import com.example.strength4mom.data.local.notes.NotesRepository
import com.example.strength4mom.data.local.notes.OfflineNotesRepository
import com.example.strength4mom.data.repository.ExerciseRepository
import com.example.strength4mom.data.repository.ExerciseRepositoryImpl
import com.example.strength4mom.domain.ExerciseService
import com.example.strength4mom.ui.viewmodels.NotesViewModel
import com.example.strength4mom.ui.viewmodels.SearchViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun exerciseService(retrofit: Retrofit): ExerciseService =
    retrofit.create(ExerciseService::class.java)

val appModule = module {

    single { exerciseService(get()) } //Creates one instance of exerciseService feeding it a Retrofit instance and uses it when I call Koin for ExerciseService
    factory<ExerciseRepository> { ExerciseRepositoryImpl(get()) } //When ExerciseRepository is needed, Koin created a new instance of  ExerciseRepositoryImpl() providing the parameter with get()
    viewModel { SearchViewModel(get(), get()) }
    factory<NotesRepository> { OfflineNotesRepository(get()) }
    viewModel { NotesViewModel(get()) }

    single { Dispatchers.IO }

    single {
        // Configure OkHttp client with logging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val parameterLoggingInterceptor = Interceptor { chain ->
            val request = chain.request()
            val url = request.url
            Log.d("RetrofitParams", "Request URL: $url")
            chain.proceed(request)
        }

        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(parameterLoggingInterceptor)
            .build()
    }


    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get()) // Add the custom OkHttp client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        Room.databaseBuilder(
            get(), // application context
            NotesDatabase::class.java,
            "notes_database"
        ).build()
    }

    single<NotesDao> { get<NotesDatabase>().notesDao() }

}
