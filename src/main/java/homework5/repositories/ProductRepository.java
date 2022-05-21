package homework5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import homework5.entites.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("update Product p set p.title = ?1 where p.id = ?2")
//    @Modifying
//    Integer updateProductTitleById(String title, Long id);
}
