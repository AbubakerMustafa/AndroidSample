package com.appsfactory.lastfm.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.appsfactory.lastfm.ui.base.AppExecutors
import com.appsfactory.lastfm.ui.base.Navigator
import com.appsfactory.lastfm.data.prefs.SharedPrefsDataSource
import com.appsfactory.lastfm.data.repository.ApiInterface
import com.appsfactory.lastfm.data.repository.sources.*
import com.appsfactory.lastfm.data.repository.db.AppDb
import com.appsfactory.lastfm.data.repository.repos.AlbumsRepo
import com.appsfactory.lastfm.util.GlobalConstants
import com.appsfactory.lastfm.util.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(application: Application): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(GlobalConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(GlobalConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor(application))
            .addInterceptor(interceptor).addInterceptor { chain ->
                val original = chain.request()
                val request: Request
                request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json; charset=utf-8")
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }.build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(client: OkHttpClient): ApiInterface {


        return Retrofit.Builder()
            .baseUrl(GlobalConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            .client(client)
            .build()
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        apiInterface: ApiInterface
    ): NetworkDataSource {
        return NetworkDataSource(apiInterface)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(appDatabase: AppDb): LocalDataSource {
        return LocalDataSource(appDatabase)
    }

    @Singleton
    @Provides
    fun provideSharedPrefDataSource(@AppContext context: Context): SharedPrefsDataSource {
        return SharedPrefsDataSource(context)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDb {
        return Room
            .databaseBuilder(app, AppDb::class.java, GlobalConstants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAlbumRepo(
        localDataSource: LocalDataSource,
        networkDataSource: NetworkDataSource
    ): AlbumsRepo {
        //replace network data source with mock if needed
        return AlbumsRepo(networkDataSource, localDataSource)
    }

    @Singleton
    @Provides
    @AppContext
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideAppExecutor(): AppExecutors {
        return AppExecutors()
    }


    @Provides
    fun provideNavigator(): Navigator {
        return Navigator()
    }
}