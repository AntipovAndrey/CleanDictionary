package ru.andrey.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.andrey.domain.model.Translation

interface TranslationRepository {

    fun getAll(): Observable<List<Translation>>

    fun findById(id: Int): Single<Translation>

    fun save(item: Translation): Completable

    fun update(item: Translation): Completable

    fun delete(id: Int): Completable

    fun restore(id: Int): Completable

    fun initLanguages(vararg langs: String)
}