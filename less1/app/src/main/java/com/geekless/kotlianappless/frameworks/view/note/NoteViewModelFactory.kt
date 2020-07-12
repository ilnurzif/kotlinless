package com.geekless.kotlianappless.frameworks.view.note

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geekless.kotlia.NoteViewModel
import com.geekless.kotlianappless.model.data.BaseDataSource
import com.geekless.kotlianappless.model.data.BaseRepositoryImpl
import com.geekless.kotlianappless.model.interactors.note.NodeModelImpl
import com.geekless.kotlianappless.model.interactors.utility.Utility

class NoteViewModelFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val myRepository=BaseRepositoryImpl(BaseDataSource)
        val noteModel= NodeModelImpl(myRepository)
        val utility=Utility(context)
        return NoteViewModel(noteModel,utility) as T
    }
}