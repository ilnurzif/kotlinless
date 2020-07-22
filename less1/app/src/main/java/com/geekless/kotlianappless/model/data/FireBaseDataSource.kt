package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import com.geekless.kotlianappless.model.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

object FireBaseDataSource : IDataSource {
    private const val NOTES_COLLECTION = "notes"
    private const val USERS_COLLECTION = "users"

    private val store = FirebaseFirestore.getInstance()

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser


    var noteResultListBehaviorSubject = BehaviorSubject.create<NoteResult>()
    var currentNoteBehaviorSubject = BehaviorSubject.create<NoteResult>()
    var currentUserBehaviorSubject = BehaviorSubject.create<User>()

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    }

    override fun getData(): BehaviorSubject<NoteResult> {
        getUserNotesCollection()?.addSnapshotListener { snapshot, e ->
            var noteResult: NoteResult? = null;
            e?.let {
                noteResult = NoteResult.Error(e)
            } ?: snapshot?.let {
                val notes = snapshot.documents.map { doc -> doc.toObject(Note::class.java) }
                noteResult = NoteResult.Success(notes)
            }
            noteResult?.let { noteResultListBehaviorSubject.onNext(it) }
        }
        return noteResultListBehaviorSubject
    }


    override fun saveNote(note: Note) {
        getUserNotesCollection()?.let{it.document(note.id).set(note)
                .addOnSuccessListener { snapshot ->
                    NoteResult.Success(note)
                }.addOnFailureListener {
                    NoteResult.Error(it)
                }
        }
    }

    override fun getCurrentNodeBehaviorSubject(): BehaviorSubject<NoteResult> {
        return currentNoteBehaviorSubject;
    }

    override fun loadNote(noteId: String) {
        getUserNotesCollection()?.let {
            it.document(noteId).get()
                    .addOnSuccessListener { snapshot ->
                        currentNoteBehaviorSubject.onNext(NoteResult.Success(snapshot.toObject(Note::class.java)))
                    }.addOnFailureListener {
                        currentNoteBehaviorSubject.onNext(NoteResult.Error(it))
                    }
        }
    }


    override fun getDefaultUser(): BehaviorSubject<User> {
        return currentUserBehaviorSubject
    }
}