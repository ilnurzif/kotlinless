package com.geekless.kotlianappless.dataprovider

import com.geekless.kotlianappless.frameworks.firebase.FireBaseDataSource
import com.geekless.kotlianappless.interface_adapters.viewmodel.note.NoteViewState
import com.geekless.kotlianappless.model.entities.Note
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.ClassRule
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

    }

    @Test
    fun `should throw NoAuthException if no auth`(){
        var result: Any? = null
        every { mockAuth.currentUser } returns null
        val noteResult = NoteResult.Success(testNotes)
        noteResultListBehaviorSubject.onNext(noteResult)
        dataSource.getData().subscribe{
           result = (it as? NoteResult.Error)?.error
        }
        Assert.assertTrue(result is NoAuthException)
    }

     @Test
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
    }

    @Test
    fun `subscribeToAllNotes returns error`(){
        var result: Throwable? = null
        val testError = mockk<FirebaseFirestoreException>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()
        dataSource.getData().subscribe {
            result = (it as? NoteResult.Error)?.error
        }
        slot.captured.onEvent(null, testError)
        Assert.assertEquals(testError, result)
    }

    @Test
    fun `saveNote calls set`(){
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        dataSource.saveNote(testNotes[0]).subscribe(
           {},{})
        verify(exactly = 1) { mockDocumentReference.set(testNotes[0]) }
    }

    @Test
    fun `LoadNote return Note`() {
        var result: Note? = null
        val mockSnaphot=mockk<DocumentSnapshot>()
        val slot = slot<OnSuccessListener<in DocumentSnapshot>>()
        every {mockSnaphot.toObject(Note::class.java)} returns testNotes[0]
        every { mockResultCollection.document(testNotes[0].id).get().addOnSuccessListener(capture(slot)).addOnFailureListener(any()) } returns mockk()
        dataSource.loadNote(testNotes[0].id).subscribe{noteResult->
            result=(noteResult as? NoteResult.Success<Note>)?.data
        }
        slot.captured.onSuccess(mockSnaphot)
        assertEquals(result,testNotes[0])
        }

    @Test
    fun `SavedNote return Completable`() {
        var result: Boolean? = false
        val mockSnaphot=mockk<Void>()
        val slot = slot<OnSuccessListener<in Void>>()
        every { mockResultCollection.document(testNotes[0].id).set(testNotes[0]).addOnSuccessListener(capture(slot)).addOnFailureListener(any()) } returns mockk()
        dataSource.saveNote(testNotes[0]).subscribe({result=true},{})
        slot.captured.onSuccess(mockSnaphot)
        assertEquals(result,true)
    }

    @Test
    fun `deleteNote return Completable`() {
        var result: Boolean? = false
        val mockSnaphot=mockk<Void>()
        val slot = slot<OnSuccessListener<in Void>>()
        every { mockResultCollection.document(testNotes[0].id).delete().addOnSuccessListener(capture(slot)).addOnFailureListener(any()) } returns mockk()
        dataSource.deleteNote(testNotes[0]).subscribe({result=true},{})
        slot.captured.onSuccess(mockSnaphot)
        assertEquals(result,true)
    }
}