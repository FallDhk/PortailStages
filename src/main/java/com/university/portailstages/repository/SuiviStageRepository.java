package com.university.portailstages.repository;

import com.university.portailstages.entity.SuiviStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuiviStageRepository extends JpaRepository<SuiviStage, Long> {
    List<SuiviStage> findByConventionId(Long id);

    @Query("""
        select coalesce(sum(s.progression),0)
        from SuiviStage s
        where s.convention.id = :id
    """)
    int sumProgression(@Param("id") Long id);
}
