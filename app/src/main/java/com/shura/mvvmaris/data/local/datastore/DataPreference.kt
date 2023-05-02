package com.shura.mvvmaris.data.local.datastore

import kotlinx.coroutines.flow.Flow


interface DataPreference<T> {
    val data: Flow<T>
    suspend fun saveData(data: T)
}