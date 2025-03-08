package com.telusko.ecomproj.repo;

import com.telusko.ecomproj.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productRepo extends JpaRepository<product,Integer>{
}
// no need to create any method inside repo as it has inbuilt methods from JpaRepository
// this interacts with database