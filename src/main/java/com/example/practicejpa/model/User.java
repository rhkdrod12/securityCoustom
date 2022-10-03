package com.example.practicejpa.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "USER")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User{
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_TABLE_GENERATOR")
    @TableGenerator(
            name = "USER_TABLE_GENERATOR",
            table = "SEQ_GENERATOR",
            pkColumnName = "SEQNO",
            pkColumnValue = "USER_ID",
            valueColumnName = "NEXT_VAL",
            schema = "testdb3",
            allocationSize = 50)
    //    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigDecimal id;
    
    @Column(name = "user_id", nullable = false, length = 30, unique = true)
    private String userId;
    @Column(name = "user_pw", nullable = false, length = 100)
    private String userPw;
    @Column(name="name", nullable = false, length = 10)
    private String name;
    @Column(name = "age", length = 4)
    private int age;
    @Column(name = "zip")
    private String zip;
    
    @Column(name = "REFRESH_ID", nullable = true, unique = true)
    private String refrehId;
    
    @Column(name="REFRESH_TOKEN", nullable = true)
    private String refreshToken;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Auth> auths;
    
    
}
