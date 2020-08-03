package com.geekless.kotlianappless.frameworks.firebase

import com.geekless.kotlianappless.model.data.IDataSource
import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.entities.User
import com.geekless.kotlianappless.model.error.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.gb_kotlin.data.model.NoteResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireBaseDataSource(val store: FirebaseFirestore, val auth: FirebaseAuth) : IDataSource {
    private val NOTES_COLLECTION = "notes"
    private val USERS_COLLECTION = "users"


    private val currentUser
        get() = auth.currentUser

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun getData(): ReceiveChannel<NoteResult> = Channel<NoteResult>(Channel.CONFLATED).apply {
        var registration: ListenerRegistration? = null

        try {
            registration = getUserNotesCollection().addSnapshotListener { snapshot, e ->
                val value = e?.let {
                    NoteResult.Error(it)
                } ?: snapshot?.let {
                    val notes = snapshot.documents.map { doc -> doc.toObject(Note::class.java) }
                    NoteResult.Success(notes)
                }
                value?.let { offer(it) }
            }

        } catch (e: Throwable) {
            offer(NoteResult.Error(e))
        }
        invokeOnClose { registration?.remove() }
    }

    override suspend fun saveNote(note: Note): Note = suspendCoroutine { continuation ->
        try {
            getUserNotesCollection().document(note.id).set(note)
                    .addOnSuccessListener {
                        continuation.resume(note)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun loadNote(noteId: String): Note = suspendCoroutine { continuation ->
        try {
            getUserNotesCollection().document(noteId).get()
                    .addOnSuccessListener { snapshot ->
                        continuation.resume(snapshot.toObject(Note::class.java)!!)
                    }.addOnFailureListener {
                    }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun getDefaultUser(): User? = suspendCoroutine { continuation ->
        continuation.resume(currentUser?.let { User(it.displayName ?: "", it.email ?: "") })
    }

    override suspend fun deleteNote(note: Note): Unit = suspendCoroutine { continuation ->
        try {
            getUserNotesCollection().document(note.id).delete()
                    .addOnSuccessListener { snapshot ->
                        continuation.resume(Unit)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }
}