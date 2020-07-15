package com.geekless.kotlianappless.interface_adapters.viewmodel.main

import com.geekless.kotlianappless.model.entities.Note

class MyViewState(val notes: List<Note>? = null, val error: Throwable? = null)