package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import exercise.model.Person;
@Repository
// BEGIN
public interface PersonRepository extends JpaRepository<Person, Long>{

}
// END
