package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import preprocessingmining.com.example.preprocessingmining.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,String> {
    @Query(value = "SELECT * FROM public.users WHERE mail = ?;", nativeQuery = true)
    User findByMail(String eMail);
    @Query(value = "SELECT * FROM public.users WHERE name = ?;", nativeQuery = true)
    User findByUsername(String name);
}
