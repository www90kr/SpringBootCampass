package com.campass.demo.dto;

import java.util.Collection;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.campass.demo.entity.CZone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class CZoneDto {

//	입력용
	@Data
	public class CZWriteDto{	
		private String czName		;
		private Integer czPrice		;
		private MultipartFile czPhoto		;
		private String czFileName		;
		public CZone toCZentity() {
			return CZone.builder().czName(czName).czPrice(czPrice).czFileName(czFileName).build();
		}
		}
//	입력용	
	@Data
	public class CZUpdateDto{
		private Integer czCode		;
		private String czName		;
		private Integer czPrice		;
		private String czFileName		;
		public CZone toCZentity() {
			return CZone.builder().czCode(czCode).czName(czName).czPrice(czPrice).czFileName(czFileName).build();
		}
		}
	
// 출력용	
	@Data
	public  static class CZListDto{	
		private Integer czCode		;
		private Integer cz_cCode		;
		private String czName		;
		private Integer czPrice		;
		private String czPhoto		;
		private String czFileName		;
		}
	



}
