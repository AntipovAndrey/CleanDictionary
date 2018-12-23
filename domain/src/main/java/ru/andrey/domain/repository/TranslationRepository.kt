package ru.andrey.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.andrey.domain.model.Translation

interface TranslationRepository {

    fun getAll(): Single<List<Translation>>

    fun findById(id: Int): Single<Translation>

    fun save(item: Translation): Single<Translation>

    fun update(item: Translation): Single<Translation>

    fun delete(id: Int): Completable

    fun initLanguages(vararg langs: String)
}