package online.z0lk1n.android.handnotes.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import io.mockk.every
import io.mockk.mockk
import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.data.entity.User
import online.z0lk1n.android.handnotes.ui.splash.SplashViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NotesRepository>()
    private val userLiveData = MutableLiveData<User?>()

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        every { mockRepository.getCurrentUser() } returns userLiveData
        viewModel = SplashViewModel(mockRepository)
    }

    @Test
    fun `should returns current user`() {
        var result: Boolean? = false
        val testUser = User("Test")

        viewModel.getViewState().observeForever {
            result = it?.data
        }

        userLiveData.value = testUser

        assertNotNull(result)
        assertEquals(true, result)
    }
}