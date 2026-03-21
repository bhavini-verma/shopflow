package com.dailycodework.dreamshop.repository;

public interface CategoryRepository2 extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);

}
