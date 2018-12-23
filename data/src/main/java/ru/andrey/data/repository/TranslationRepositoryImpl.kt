package ru.andrey.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.andrey.data.db.TranslationDatabase
import ru.andrey.data.db.entity.LanguageData
import ru.andrey.data.db.entity.TranslationData
import ru.andrey.domain.model.Language
import ru.andrey.domain.model.Translation
import ru.andrey.domain.repository.TranslationRepository
import java.util.*

class TranslationRepositoryImpl(dataBase: TranslationDatabase) : TranslationRepository {

    private val dao = dataBase.translationDao()
    private val languageDao = dataBase.languageDao()

    private val cache: MutableList<TranslationData> by lazy {
        val cache = LinkedList(dao.getAll())
        cache.forEachIndexed { index, data -> idToIndex[data.id] = index }
        cache
    }

    private val idToIndex = HashMap<Int, Int>()
    private var totalInserted = 0

    override fun getAll(): Single<List<Translation>> {
        return Observable.defer { Observable.fromIterable(cache) }
                .map(::toModel).toList()
    }

    override fun findById(id: Int): Single<Translation> {
        return Single.fromCallable {
            idToIndex[id]?.let { cache[it + totalInserted] }
        }.map(::toModel)
    }

    override fun save(item: Translation): Single<Translation> {
        return insert(item) {
            it.id = 0
            it.id = dao.save(it).toInt()
            cache.add(0, it)
            totalInserted++
            idToIndex[it.id] = 0 - totalInserted
            it
        }
    }

    override fun update(item: Translation): Single<Translation> {
        return insert(item) {
            dao.update(it)
            val index = idToIndex[it.id]
            if (index != null) {
                cache[index + totalInserted] = it
            }
            it
        }
    }

    override fun initLanguages(vararg langs: String) {
        langs.forEach {
            languageDao.save(LanguageData(0, it))
        }
    }

    private fun insert(item: Translation,
                       saver: (item: TranslationData) -> TranslationData): Single<Translation> {
        return Single.just(item)
                .map(::toDto)
                .map(saver)
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
                id = model.id ?: 0,
                word = model.word,
                translation = model.translation,
                from = languageDao.findByCode(model.languageFrom.languageCode)!!.id,
                to = languageDao.findByCode(model.languageTo.languageCode)!!.id,
                favorite = model.favorite
        )
    }
}
