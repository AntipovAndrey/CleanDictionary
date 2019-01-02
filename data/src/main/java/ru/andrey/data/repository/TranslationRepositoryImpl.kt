package ru.andrey.data.repository

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.andrey.data.db.TranslationDatabase
import ru.andrey.data.db.entity.LanguageData
import ru.andrey.data.db.entity.TranslationData
import ru.andrey.domain.model.Language
import ru.andrey.domain.model.Translation
import ru.andrey.domain.repository.TranslationRepository

@SuppressLint(value = ["CheckResult"])
class TranslationRepositoryImpl(dataBase: TranslationDatabase) : TranslationRepository {

    private val dao = dataBase.translationDao()
    private val languageDao = dataBase.languageDao()

    override fun getAll(): Observable<List<Translation>> {
        return dao.getAll()
                .map { it.map(::toModel) }
                .toObservable()
    }

    override fun findById(id: Int): Single<Translation> {
        return dao.findById(id)
                .map(::toModel)
    }

    override fun save(item: Translation): Completable {
        return Completable.fromCallable {
            dao.save(toDto(item))
        }
    }

    override fun update(item: Translation): Completable {
        return Completable.fromCallable {
            dao.update(toDto(item))
        }
    }

    override fun delete(id: Int): Completable {
        return Completable.fromCallable {
            dao.delete(id)
        }
    }

    override fun restore(id: Int): Completable {
        return Completable.fromCallable {
            dao.restore(id)
        }
    }

    override fun initLanguages(vararg langs: String) {
        langs.forEach {
            languageDao.save(LanguageData(0, it))
        }
    }

    private fun toModel(data: TranslationData): Translation {
        return Translation(
                id = data.id,
                word = data.word,
                translation = data.translation,
                languageFrom = Language.byCode(languageDao.findById(data.from)!!.code),
                languageTo = Language.byCode(languageDao.findById(data.to)!!.code),
                favorite = data.favorite
        )
    }

    private fun toDto(model: Translation): TranslationData {
        return TranslationData(
                id = model.id ?: 0,
                word = model.word,
                translation = model.translation,
                from = languageDao.findByCode(model.languageFrom.languageCode)!!.id,
                to = languageDao.findByCode(model.languageTo.languageCode)!!.id,
                deleted = false,
                favorite = model.favorite
        )
    }
}
