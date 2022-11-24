package com.campass.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
public class Heart {
private Integer heartNo;
private Integer h_cCode;
private String h_username; //일반회원아이디

}
