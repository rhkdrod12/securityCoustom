package com.example.practicejpa.model;

import com.example.practicejpa.utils.AuthConverter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTH")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Auth{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AUTH_TABLE_GENERATOR")
    @TableGenerator(
            name = "AUTH_TABLE_GENERATOR",
            table = "SEQ_GENERATOR",
            pkColumnName = "SEQNO",
            pkColumnValue = "AUTH_ID",
            valueColumnName = "NEXT_VAL",
            schema = "testdb3",
            allocationSize = 50)
    @Column(name = "auth_id")
    private BigDecimal id;
    
    @Convert(converter = AuthConverter.class)
    @Column(name = "auth")
    private GrantedAuthority grantedAuthority;
    
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
}
