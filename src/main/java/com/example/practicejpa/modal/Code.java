package com.example.practicejpa.modal;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity(name = "CODE")
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Code extends BaseEntity{
	// 코드
	@Id
	@Column(name = "CODE", nullable = false, unique = true)
	String code;
	// 코드명
	@Column(name = "CODE_NAME", nullable = false, unique = true)
	String codeName;
	// 코드 수준
	@Column(name = "CODE_DEPTH")
	int codeDepth = 0;
	// 하위코드
	@OneToMany(mappedBy = "upperCode", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	Set<Code> childCodes;
	// 상위코드
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPPER_CODE", referencedColumnName = "CODE")
	Code upperCode;
	
}
