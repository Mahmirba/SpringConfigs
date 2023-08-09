package org.example.repo;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long>, JpaSpecificationExecutor<StudentEntity> {

    Optional<List<StudentEntity>> findByName(@Param("name") String name);

    @Query(value = "Select s FROM StudentEntity s WHERE 1=1 and s.name = :name")
    List<StudentEntity> find(@Param("name") String name);

    @Query(value = "Select s FROM StudentEntity s WHERE 1=1 " +
            "and (:id is null or s.id = :id)" +
            "and (:name is null or s.name = :name)" +
            "and (:age is null or s.age = :age)" +
            "and (:updatedAt is null or s.updatedAt = :updatedAt)")
    Optional<List<StudentEntity>> search(@Param("id") Long id, @Param("name") String name, @Param("age") Integer age,  @Param("updatedAt") LocalDateTime updatedAt);

    @Query(value = "Select s FROM StudentEntity s WHERE 1=1 " +
            "and (:id is null or s.id = :id)" +
            "and (:name is null or s.name = :name)" +
            "and (:age is null or s.age = :age)" +
            "and (:updatedAt is null or s.updatedAt = :updatedAt)")
    Page<StudentEntity> search(@Param("id") Long id, @Param("name") String name, @Param("age") Integer age, @Param("updatedAt") LocalDateTime updatedAt, Pageable pageable);



}
