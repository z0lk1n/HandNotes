package online.z0lk1n.android.handnotes

import android.app.Application
import com.github.ajalt.timberkt.Timber
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import online.z0lk1n.android.handnotes.di.appModule
import online.z0lk1n.android.handnotes.di.mainModule
import online.z0lk1n.android.handnotes.di.noteModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        Timber.plant(timber.log.Timber.DebugTree())

        startKoin(this, listOf(appModule, mainModule, noteModule))
    }
}