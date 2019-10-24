package com.gabia.internproject.data.repository;

import com.gabia.internproject.data.entity.SocialLoginEmail;
import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SocialLoginEmailRepository extends JpaRepository<SocialLoginEmail, Long> {

    @Transactional
    @Query("select DISTINCT sl from SocialLoginEmail sl join fetch sl.employee where sl.emailAddress=:emailAddress and sl.provider=:provider")
    Optional<SocialLoginEmail> findByEmailAddressAndProvider(@Param("emailAddress") String emailAddress,@Param("provider") OAuthAPIProvider provider);

    @Transactional
    Long countByEmailAddressAndProvider(String emailAddress,OAuthAPIProvider provider);

}
