package org.yearup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yearup.models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{
    List<Product> findByCategoryId(int categoryId);

    // 🚀 This handles Bug 1 perfectly by checking if parameters are NULL or if they match!
    @Query("""
       SELECT p FROM Product p
       WHERE (:cat IS NULL OR p.categoryId = :cat)
         AND (:minPrice IS NULL OR p.price >= :minPrice)
         AND (:maxPrice IS NULL OR p.price <= :maxPrice)
         AND (:subCat IS NULL OR LOWER(p.subCategory) = LOWER(:subCat))
       """)
    List<Product> search(
            @Param("cat") Integer categoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("subCat") String subCategory
    );
}