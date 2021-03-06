package online.z0lk1n.android.handnotes.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.data.provider.FireStoreProvider
import online.z0lk1n.android.handnotes.data.provider.RemoteDataProvider
import online.z0lk1n.android.handnotes.ui.main.MainViewModel
import online.z0lk1n.android.handnotes.ui.note.NoteViewModel
import online.z0lk1n.android.handnotes.util.ScreenConfiguration
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<RemoteDataProvider> { FireStoreProvider(get(), get()) }
    single { NotesRepository(get()) }
    factory { ScreenConfiguration(get()) }
}

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
val mainModule = module {
    viewModel { MainViewModel(get()) }
}

@ExperimentalCoroutinesApi
val noteModule = module {
    viewModel { NoteViewModel(get()) }
}