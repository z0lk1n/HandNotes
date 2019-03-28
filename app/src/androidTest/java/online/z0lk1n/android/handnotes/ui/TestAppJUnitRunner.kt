package online.z0lk1n.android.handnotes.ui

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import online.z0lk1n.android.handnotes.TestApp

class TestAppJUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}