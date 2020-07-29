package com.geekless.kotlianappless.dataprovider

import com.geekless.kotlianappless.frameworks.firebase.FireBaseDataSource
import com.geekless.kotlianappless.interface_adapters.viewmodel.note.NoteViewState
import com.geekless.kotlianappless.model.entities.Note
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import ru.geekbrains.gb_kotlin.data.error.NoAuthException
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class FireBaseDataSourceTest {
  //  @get:Rule
   // val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()

    private val mockUsersCollection = mockk<CollectionReference>()
    private val mockUserDocument = mockk<DocumentReference>()
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    private val dataSource = FireBaseDataSource(mockDb, mockAuth)
    var noteResultListBehaviorSubject = BehaviorSubject.create<NoteResult>()

    @Before
    fun setUp() {
        clearAllMocks()
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every { mockUser.getUid() } returns ""
        every { mockDb.collection(any()).document(any()).collection(any()) } returns mockResultCollection

        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]

        val noteResult = NoteResult.Success(testNotes)
        noteResultListBehaviorSubject.onNext(noteResult)
        every { dataSource.getData() } returns noteResultListBehaviorSubject
    }

    @Test
    fun `should throw NoAuthException if no auth`(){
        var result: Any? = null
        every { mockAuth.currentUser } returns null
        val noteResult = NoteResult.Success(testNotes)
        noteResultListBehaviorSubject.onNext(noteResult)
        val testsubscriber = TestSubscriber.create<Boolean>()
        dataSource.getData().subscribe{
           result = (it as? NoteResult.Error)?.error
        }
        Assert.assertTrue(result is NoAuthException)
    }

/*     @Test
    fun `subscribeToAllNotes returns notes`(){
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        var result: List<Note>? = null
        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockSnapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()
        dataSource.getData().subscribe {
            result = (it as? NoteResult.Success<List<Note>>)?.data
        }
        slot.captured.onEvent(mockSnapshot, null)
        Assert.assertEquals(testNotes, result)
    }*/

/*    @Test
    fun `saveNote calls set`(){
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
         val testsubscriber = TestSubscriber.create<Boolean>()
        dataSource.saveNote(testNotes[0]).subscribe(//{testsubscriber}
           {
           val changedNote = null
        },
            {
                error ->
            })
        verify(exactly = 1) { mockDocumentReference.set(testNotes[0]) }
    }*/

    @Test
    fun `saveNote returns note`(){
        var result: Boolean? = null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<in Void>>()
      //  val slot = slot<EventListener<QuerySnapshot>>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.set(testNotes[0]).addOnSuccessListener(capture(slot)) } returns mockk()

    //    every { mockUsersCollection.addSnapshotListener(capture(slot)) } returns MockK()
      //  slot.captured.onEvent(mockSnapshot, null)


        dataSource.saveNote(testNotes[0]).subscribe(
                {result = true},
                {result = false}
          )
        slot.captured.onSuccess(null)
        //assertEquals(testNotes[0], result)
        assertEquals(result, true)
    }
}