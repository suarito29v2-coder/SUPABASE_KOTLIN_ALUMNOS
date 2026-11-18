package com.example.appsupabase.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
object SupabaseManager {
    val client = createSupabaseClient(
        supabaseUrl = "https://kgociktsmonwcjuqefrl.supabase.co",
        supabaseKey = "sb_publishable_i8kvRPRqIDEfPREKLnn-Yg_wBvVpi31"
    ) {
        install(Postgrest)
    }
}

