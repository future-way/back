package com.team.futureway.consult.repository;


import com.team.futureway.consult.entity.Consult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultRepository extends JpaRepository<Consult, Long> {

}
