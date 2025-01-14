package my.socpms.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.socpms.api.model.Paperwork;

@Repository
public interface PaperworkRepository extends JpaRepository<Paperwork, Long> {
    List<Paperwork> findByUser_Department(String department);
    List<Paperwork> findByCurrentStage(String stage);
    List<Paperwork> findByUserEmail(String email);
}