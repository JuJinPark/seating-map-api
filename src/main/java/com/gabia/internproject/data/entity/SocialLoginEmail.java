package com.gabia.internproject.data.entity;



import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
@Table(name = "social_login_email")
public class SocialLoginEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;

    @Setter
    @Column(nullable = false,length = 320)
    private String emailAddress;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,foreignKey=@ForeignKey(name="fk_social_login_email_employee_id"))
    private Employee employee;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length =30)
    private OAuthAPIProvider provider;

}
