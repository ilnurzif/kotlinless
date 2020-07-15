package com.geekless.kotlianappless.model.data

import com.geekless.kotlianappless.model.entities.Note
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.subjects.BehaviorSubject
import ru.geekbrains.gb_kotlin.data.model.NoteResult

object FireBaseDataSource : IDataSource {
    private const val NOTES_COLLECTION = "mynotes"

    private val store = FirebaseFirestore.getInstance()
    private val notesReference = store.collection(NOTES_COLLECTION)

    var noteResultListBehaviorSubject = BehaviorSubject.create<NoteResult>()
    var currentNoteBehaviorSubject = BehaviorSubject.create<NoteResult>()

    override fun getData(): BehaviorSubject<NoteResult> {
        notesReference.addSnapshotListener { snapshot, e ->
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
        notesReference.document(note.id).set(note)
                .addOnSuccessListener { snapshot ->
                    NoteResult.Success(note)
                }.addOnFailureListener {
                    NoteResult.Error(it)
                }
    }

    override fun getCurrentNodeBehaviorSubject(): BehaviorSubject<NoteResult> {
        return currentNoteBehaviorSubject;
    }

    override fun loadNote(noteId: String) {
        notesReference.document(noteId).get()
                .addOnSuccessListener { snapshot ->
                    currentNoteBehaviorSubject.onNext(NoteResult.Success(snapshot.toObject(Note::class.java)))
                }.addOnFailureListener {
                    currentNoteBehaviorSubject.onNext(NoteResult.Error(it))
                }
    }
}