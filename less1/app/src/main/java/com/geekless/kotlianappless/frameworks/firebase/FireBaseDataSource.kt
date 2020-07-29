package com.geekless.kotlianappless.frameworks.firebase

import com.geekless.kotlianappless.interface_adapters.viewmodel.splash.SplashViewState
import com.geekless.kotlianappless.model.data.IDataSource
import com.geekless.kotlianappless.model.entities.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.error.NoAuthException
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class FireBaseDataSource(val store: FirebaseFirestore, val auth: FirebaseAuth) : IDataSource {
    private val NOTES_COLLECTION = "notes"
    private val USERS_COLLECTION = "users"

    var noteResultListBehaviorSubject = BehaviorSubject.create<NoteResult>()
    var currentUserBehaviorSubject = BehaviorSubject.create<SplashViewState>()

    private val currentUser
        get() = auth.currentUser

    val unsubscribe =
            try {
                auth.addAuthStateListener { fb ->
                    fb.currentUser?.let {
                        currentUserBehaviorSubject?.onNext(SplashViewState(authenticated = true))
                    }
                            ?: let {
                                currentUserBehaviorSubject?.onNext(SplashViewState(error = NoAuthException()))
                            }
                }
            } catch (t: Throwable) {
                currentUserBehaviorSubject?.let { it.onError(t) }
            }


    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun getData(): BehaviorSubject<NoteResult> {
        try {
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
        } catch (e: Throwable) {
            noteResultListBehaviorSubject.onNext(NoteResult.Error(e))
        }
        return noteResultListBehaviorSubject
    }

    override fun saveNote(note: Note): Completable {
        return Completable.create { emitter ->
            getUserNotesCollection()?.let {
                it.document(note.id).set(note)
                        .addOnSuccessListener { snapshot ->
                            emitter.onComplete()
                        }.addOnFailureListener {
                            emitter.onError(it)
                        }
            }
        }
    }

    override fun loadNote(noteId: String): Single<NoteResult> {
        return Single.create { emitter ->
            getUserNotesCollection()?.let {
                it.document(noteId).get()
                        .addOnSuccessListener { snapshot ->
                            emitter.onSuccess(NoteResult.Success(snapshot.toObject(Note::class.java)))
                        }.addOnFailureListener {
                            emitter.onSuccess(NoteResult.Error(it))
                        }
            }
        }
    }

    override fun getDefaultUser(): BehaviorSubject<SplashViewState> {
        return currentUserBehaviorSubject
    }

    override fun deleteNote(note: Note): Completable {
        return Completable.create { emitter ->
            getUserNotesCollection()?.document(note.id)?.delete()
                    ?.addOnSuccessListener { snapshot ->
                        emitter.onComplete()
                    }?.addOnFailureListener {
                        emitter.onError(it)
                    }
        }
    }
}