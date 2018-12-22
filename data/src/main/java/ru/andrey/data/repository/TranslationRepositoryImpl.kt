package ru.andrey.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.andrey.data.db.TranslationDatabase
import ru.andrey.data.db.entity.TranslationData
import ru.andrey.domain.model.Language
import ru.andrey.domain.model.Translation
import ru.andrey.domain.repository.TranslationRepository

class TranslationRepositoryImpl(dataBase: TranslationDatabase) : TranslationRepository {

    private val dao = dataBase.translationDao()
    private val languageDao = dataBase.languageDao()

    override fun getAll(): Single<List<Translation>> {
        return Observable.fromIterable(dao.getAll())
                .map(::toModel)
                .toList()
    }

    override fun findById(id: Int): Single<Translation> {
        return Single.just(dao.findById(id))
                .map(::toModel)
    }

    override fun save(item: Translation): Single<Translation> {
        return insert(item) {
            dao.save(it).toInt()
        }
    }

    override fun update(item: Translation): Single<Translation> {
        return insert(item) {
            dao.update(it)
            it.id
        }
    }

    private fun insert(item: Translation,
                       saver: (item: TranslationData) -> Int): Single<Translation> {
        return Single.just(item)
                .map(::toDto)
                .map(saver)
                .map { dao.findById(it) }
                .map(::toModel)
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
                id = model.id ?: -1,
                word = model.word,
                translation = model.translation,
                from = languageDao.findByCode(model.languageFrom.languageCode)!!.id,
                to = languageDao.findByCode(model.languageTo.languageCode)!!.id,
                favorite = model.favorite
        )
    }
}
