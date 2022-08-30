package com.example.practicejpa.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Getter
@Setter
@Entity(name = "MENU_MGM")
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MenuMgm extends BaseEntity{
	// 메뉴PK
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MENU_GENERATOR")
	@TableGenerator(
			name = "MENU_GENERATOR",
			table = "SEQ_GENERATOR",
			pkColumnName = "SEQNO",
			pkColumnValue = "MENU_ID",
			valueColumnName = "NEXT_VAL",
			schema = "testdb3",
			allocationSize = 1)
	@Column(name = "MENU_ID", nullable = false)
	Long menuId;
	// 메뉴타입(어디 메뉴인지)
	@Column(name = "TYPE", nullable = false)
	String type;
	// 메뉴 범주(사실 이런건 코드화해야겠지만 당장은 안되니)
	@Column(name = "CATEGORY", nullable = true)
	String category;
	// 메뉴명
	@Column(name = "NAME", nullable = false)
	String name;
	// 접근 URL
	@Column(name = "URL", nullable = false)
	String url;
	// 상위 메뉴PK
	@Column(name = "UPPER_MENU", nullable = true)
	Long upperMenu;
	// 메뉴 깊이
	@Column(name = "MENU_DEPTH", length = 5)
	int menuDepth = 0;
	// 메뉴 순서
	@Column(name = "MENU_ORDER", length = 5, nullable = false)
	int menuOrder;

}
