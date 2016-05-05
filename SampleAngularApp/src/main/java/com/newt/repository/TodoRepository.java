package com.newt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.newt.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, String> {
	@Query("select c from Todo c where lower(c.id) like :keyword% "
            + "or lower(c.name) like :keyword% "           
            + "order by c.id")
    List<Todo> searchTodos(@Param("keyword") String keyword, Pageable pageable);
    
    @Modifying
    @Query("delete from Todo where id in (:ids)")
    void deleteTodos(@Param("ids") String... ids);
}
