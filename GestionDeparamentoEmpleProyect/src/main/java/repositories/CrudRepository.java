package repositories;

import java.util.List;



// Vamos a simular
// https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
public interface CrudRepository<T, ID> {
	List<T> findAll();

	T findById(ID id);

	T save(T entity);

	Boolean delete(T entity);

	

}