package ru.andrey.cleandictionary.data.repository;

import java.util.List;

/**
 * Created by antipov_an on 26.03.2018.
 */

public interface CrudRepository<D, I> {

	List<D> getAll();

	D findOneById(I id);

	boolean save(D item);

	boolean deleteById(I id);

	boolean delete(D item);
}
