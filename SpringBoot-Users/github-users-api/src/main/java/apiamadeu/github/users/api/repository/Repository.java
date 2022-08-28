package apiamadeu.github.users.api.repository;

import apiamadeu.github.users.api.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<UserEntity, String> {
}
