package ru.samarin.kotlinapp.data.di


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.samarin.kotlinapp.data.NotesRepository
import ru.samarin.kotlinapp.data.provider.DataProvider
import ru.samarin.kotlinapp.data.provider.FirestoreDataProvider
import ru.samarin.kotlinapp.ui.main.MainViewModel
import ru.samarin.kotlinapp.ui.note.NoteViewModel
import ru.samarin.kotlinapp.ui.splash.SplashViewModel

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirestoreDataProvider(get(), get()) } bind DataProvider::class
    single { NotesRepository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}