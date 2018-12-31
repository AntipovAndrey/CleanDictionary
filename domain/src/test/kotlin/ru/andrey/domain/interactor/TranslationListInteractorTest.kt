package ru.andrey.domain.interactor

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import ru.andrey.domain.model.Language
import ru.andrey.domain.model.Translation
import ru.andrey.domain.repository.TranslationRepository

class TranslationListInteractorTest {

    @Mock
    lateinit var translationRepository: TranslationRepository

    lateinit var interactor: TranslationListInteractor

    val translationNoId = Translation("word", "translation", Language.ENGLISH, Language.RUSSIAN)
    val translationWithId = Translation(1, "word", "translation", Language.ENGLISH, Language.RUSSIAN, false)


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = TranslationListInteractor(Schedulers.trampoline(), translationRepository)
    }

    @Test
    fun `when saving with null id then create entity`() {
        `when`(translationRepository.save(translationNoId)).thenReturn(Single.just(translationWithId))

        interactor.saveWord(translationNoId)
                .subscribe()
        verify(translationRepository).save(translationNoId)
        verify(translationRepository, never()).update(translationNoId)
    }

    @Test
    fun `when saving with non null id then update existing entity`() {
        `when`(translationRepository.update(translationWithId)).thenReturn(Single.just(translationWithId))

        interactor.saveWord(translationWithId)
                .subscribe()
        verify(translationRepository, never()).save(translationWithId)
        verify(translationRepository).update(translationWithId)
    }
}
