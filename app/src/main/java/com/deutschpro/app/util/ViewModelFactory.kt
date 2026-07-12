package com.deutschpro.app.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.deutschpro.app.DeutschProApplication

/**
 * Minimal factory that creates any ViewModel via a supplied lambda,
 * closing over the [DeutschProApplication]'s already-built repositories.
 * Avoids pulling in a DI framework while keeping constructor injection.
 */
class ViewModelFactory<T : ViewModel>(
    private val create: (DeutschProApplication) -> T
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>, extras: CreationExtras): VM {
        val app = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DeutschProApplication
        return create(app) as VM
    }
}
