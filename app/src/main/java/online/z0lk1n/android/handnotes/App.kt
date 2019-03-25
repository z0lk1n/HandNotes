package online.z0lk1n.android.handnotes

import android.app.Application
import com.github.ajalt.timberkt.Timber
import online.z0lk1n.android.handnotes.di.appModule
import online.z0lk1n.android.handnotes.di.mainModule
import online.z0lk1n.android.handnotes.di.noteModule
import online.z0lk1n.android.handnotes.di.splashModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(timber.log.Timber.DebugTree())

        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}