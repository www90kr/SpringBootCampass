package com.campass.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class COption {
	private Integer cOptionCode	;
	private String cOptionName	;
}
