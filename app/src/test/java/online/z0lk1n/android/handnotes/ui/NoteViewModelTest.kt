package online.z0lk1n.android.handnotes.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import online.z0lk1n.android.handnotes.data.NotesRepository
import online.z0lk1n.android.handnotes.model.NoteResult
import online.z0lk1n.android.handnotes.ui.note.NoteViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NotesRepository>()
    private val notesLiveData = MutableLiveData<NoteResult>()

    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup() {
        every { mockRepository.getNotes() } returns notesLiveData
        viewModel = NoteViewModel(mockRepository)
    }

    @Test
    fun `should call getNotes once`() {
        verify(exactly = 1) { mockRepository.getNotes() }
    }

//    @Test
//    fun `should return Notes`() {
//        var result: List<Note>? = null
//        val testData = listOf(Note("1"), Note("2"))
//        viewModel.getViewState().observeForever {
//            result = it?.data
//        }
//        notesLiveData.value = NoteResult.Success(testData)
//        Assert.assertEquals(testData, result)
//    }

    @Test
    fun `should return error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.getViewState().observeForever {
            result = it?.error
        }
        notesLiveData.value = NoteResult.Error(error = testData)
        Assert.assertEquals(testData, result)
    }

    @Test
    fun `should remove observer`() {
        viewModel.onCleared()
        Assert.assertFalse(notesLiveData.hasObservers())
    }
}