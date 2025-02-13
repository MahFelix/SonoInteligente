package com.Sonus.SonoInteligente.Repository;

import com.Sonus.SonoInteligente.Model.SleepPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepPlanRepository extends JpaRepository<SleepPlan, Long> {
}