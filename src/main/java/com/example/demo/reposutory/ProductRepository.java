package com.example.demo.reposutory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	// List Product
	@Query(value = "SELECT * FROM Product p", nativeQuery = true)
	List<Product> findAllProduct();

	// Delete Product
	@Modifying
	@Query("DELETE FROM Product where product_id = :product_id")
	void deleteByIdPro(@Param("product_id") Long product_id);
}
