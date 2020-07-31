package com.geekless.kotlianappless.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.geekless.kotliana.MainViewModel
import com.geekless.kotlianappless.frameworks.firebase.FireBaseDataSource
import com.geekless.kotlianappless.interface_adapters.viewmodel.main.MyViewState
import com.geekless.kotlianappless.model.data.BaseRepositoryImpl
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.interactors.main.MainModelImpl
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.subjects.BehaviorSubject
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class MainViewModelTest {
/*
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val dataSource=mockk<FireBaseDataSource>()
    private val repository= BaseRepositoryImpl(dataSource)
    private val mainModel = MainModelImpl(repository)
    private val viewStateData = MutableLiveData<MyViewState>()
    private lateinit var mainViewModel: MainViewModel
    var noteResultListBehaviorSubject = BehaviorSubject.create<NoteResult>()

    @Before
    fun setup() {
        clearAllMocks()
        val testNotes = listOf(Note("1"), Note("2"))
        val noteResult = NoteResult.Success(testNotes)
        noteResultListBehaviorSubject.onNext(noteResult)
        every { dataSource.getData() } returns noteResultListBehaviorSubject
        mainViewModel = MainViewModel(mainModel)
    }

     @Test
    fun `should call getMainModelOnce`() {
        verify(exactly = 1) { mainModel.getData()}
    }

    @Test
    fun `should call getRepoOnce`() {
        verify(exactly = 1) { repository.getData()}
    }

    @Test
    fun `should return Notes`() {
        var result: List<Note>? = null
        val testData = listOf(Note("1"), Note("2"))
        mainViewModel.viewState().observeForever {
            result = it?.notes
        }
        viewStateData.value = MyViewState(notes = testData)
        Assert.assertEquals(testData, result)
    }
*/

}