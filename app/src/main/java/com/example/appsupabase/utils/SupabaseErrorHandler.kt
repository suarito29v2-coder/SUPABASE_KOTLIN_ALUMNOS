package com.example.appsupabase.utils

    import android.content.Context
    import com.google.android.material.dialog.MaterialAlertDialogBuilder
    object SupabaseErrorHandler {
        fun show(context: Context, error: Throwable) {
            val detail = error.localizedMessage
                ?: "No se pudo completar la consulta."
            MaterialAlertDialogBuilder(context)
                .setTitle("Error de conexión")
                .setMessage(detail)
                .setPositiveButton("Aceptar", null)
                .show()
        }
    }

