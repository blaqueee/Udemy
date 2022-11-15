package edu.kairat_tobokelov.pet_project.repository;

import edu.kairat_tobokelov.pet_project.entity.ResetCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetCodeRepository extends JpaRepository<ResetCode, Long> {
    Optional<ResetCode> findByCode(String code);
}
