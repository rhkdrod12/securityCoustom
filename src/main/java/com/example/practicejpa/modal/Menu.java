package com.example.practicejpa.modal;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "MENU")
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Menu extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MENU_GENERATOR")
	@TableGenerator(
			name = "MENU_GENERATOR",
			table = "SEQ_GENERATOR",
			pkColumnName = "SEQNO",
			pkColumnValue = "MENU_ID",
			valueColumnName = "NEXT_VAL",
			schema = "testdb2",
			allocationSize = 1)
	@Column(name = "MENU_ID")
	Long menuId;
	@Column(name = "TYPE")
	String type;
	@Column(name = "NAME")
	String name;
	@Column(name = "URL")
	String url;
	
	@Column(name = "UPPER_MENU")
	Long upperMenu;
	@Column(name = "Depth", length = 5)
	int depth;
	@Column(name = "MENU_ORDER", length = 5)
	int order;
	
}
