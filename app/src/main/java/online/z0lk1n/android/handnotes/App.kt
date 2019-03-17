package online.z0lk1n.android.handnotes

import android.app.Application
import com.github.ajalt.timberkt.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(timber.log.Timber.DebugTree())
    }
}