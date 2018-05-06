package ru.andrey.cleandictionary.data.repository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface CrudRepository<D, I> {

	Observable<D> getAll();

	Single<D> findOneById(I id);

	Single<D> save(D item);

	Completable deleteById(I id);

	Completable delete(D item);
}
