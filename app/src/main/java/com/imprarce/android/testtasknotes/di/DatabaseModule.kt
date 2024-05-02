package com.imprarce.android.testtasknotes.di

import android.app.Application
import com.imprarce.android.local.AppDatabase
import com.imprarce.android.local.note.NoteRepositoryImpl
import com.imprarce.android.local.note.room.NoteDao
import com.imprarce.android.local.note.room.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule{

    @Provides
    fun getAppDataBase(app: Application): AppDatabase {
        return AppDatabase.getInstance(app.applicationContext)
    }

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }
}