package online.z0lk1n.android.handnotes

import android.app.Application
import org.koin.android.ext.android.startKoin

class TestApp : Application() {
    //fixme FIX THIS!!
    override fun onCreate() {
        super.onCreate()
        startKoin(this, emptyList())
    }
}