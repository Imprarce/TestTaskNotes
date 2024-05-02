package com.imprarce.android.feature_notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.imprarce.android.feature_notes.utils.Converter
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.note.room.NoteDbEntity
import com.imprarce.android.local.note.room.NoteRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
internal class NoteViewModelTest {

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteRepository: NoteRepository
    private lateinit var testDispatcher: TestCoroutineDispatcher

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        noteRepository = Mockito.mock(NoteRepository::class.java)
        testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = NoteViewModel(noteRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getNoteList() = runBlockingTest {
        val mockNoteList = listOf(
            NoteDbEntity(1, "Title 1", "Description 1", 1, "2024-05-10"),
            NoteDbEntity(2, "Title 2", "Description 2", 2, "2024-05-11"),
            NoteDbEntity(3, "Title 3", "Description 3", 3, "2024-05-12")
        )
        val mockResponse = ResponseRoom.Success(mockNoteList)
        Mockito.`when`(noteRepository.getAllNotes()).thenReturn(mockResponse)
        viewModel.getNotesList()

        val convertNoteList = Converter.convertToNotesItemList(mockNoteList)

        assertEquals(convertNoteList, viewModel.noteList.value)
    }

    @Test
    fun getNoteItem() = runBlockingTest {
        val mockNoteItem = NoteDbEntity(1, "Title 1", "Description 1", 1, "2024-05-10")
        val noteId = 1
        val mockResponse = ResponseRoom.Success(mockNoteItem)
        Mockito.`when`(noteRepository.getNoteById(noteId)).thenReturn(mockResponse)
        viewModel.getNoteItem(noteId)

        val convertNoteItem = Converter.convertToNoteItem(mockNoteItem)

        assertEquals(convertNoteItem, viewModel.noteItem.value)
    }


    @Test
    fun addNewNote() = runBlockingTest {
        val mockTitle = "Title 1"
        val mockDescription = "Description 1"
        val mockPriority = 1
        val mockCurrentTime = "2024-05-10"

        val note = NoteDbEntity(
            title = mockTitle,
            description = mockDescription,
            priority = mockPriority,
            date = mockCurrentTime
        )

        val mockResponse = ResponseRoom.Success(Unit)
        Mockito.`when`(noteRepository.insertNote(note)).thenReturn(mockResponse)

        viewModel.addNewNote(mockTitle, mockDescription, mockPriority, mockCurrentTime)

    }

    @Test
    fun deleteNote() = runBlockingTest {
        val mockId = 1
        val mockTitle = "Title 1"
        val mockDescription = "Description 1"
        val mockPriority = 1
        val mockCurrentTime = "2024-05-10"

        val note = NoteDbEntity(
            noteId = mockId,
            title = mockTitle,
            description = mockDescription,
            priority = mockPriority,
            date = mockCurrentTime
        )

        val mockResponse = ResponseRoom.Success(Unit)
        Mockito.`when`(noteRepository.deleteNote(note)).thenReturn(mockResponse)

        val noteItem = Converter.convertToNoteItem(note)

        viewModel.deleteNote(noteItem)



    }

    @Test
    fun updateNote() = runBlockingTest {
        val mockId = 1
        val mockTitle = "Title 1"
        val mockDescription = "Description 1"
        val mockPriority = 1
        val mockCurrentTime = "2024-05-10"

        val note = NoteDbEntity(
            noteId = mockId,
            title = mockTitle,
            description = mockDescription,
            priority = mockPriority,
            date = mockCurrentTime
        )

        val mockResponse = ResponseRoom.Success(Unit)
        Mockito.`when`(noteRepository.updateNote(note)).thenReturn(mockResponse)

        val noteItem = Converter.convertToNoteItem(note)

        viewModel.updateNote(noteItem)
    }

}