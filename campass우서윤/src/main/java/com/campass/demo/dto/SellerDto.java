package com.campass.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.campass.demo.entity.Seller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SellerDto {
	@Data
	public static class IdCheck {
		@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
	}
	
	@Data
	public static class BusinessNoCheck {
		@Email
		@NotEmpty(message="닉네임은 필수입력입니다")
		private Integer businessNo;
	}
	
	@Data
	public static class EmailCheck {
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String semail;
	}
	
	@Data
	public static class TelCheck {
		@Email
		@NotEmpty(message="연락처은 필수입력입니다")
		private Integer stel;
	}
	
	@Data
	@Builder
	public static class Join {
//		@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
		
		@NotEmpty(message="이름은 필수입력입니다")
		private String sname;
		
		@NotEmpty(message="비밀번호는 필수입력입니다")
		private String spassword;
		
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String semail;
		
		
		
		private Integer stel;
		private String businessName;
		private Integer businessNo;
		
		private Integer enabled;
		private String role;
		
		
		public Seller toEntity() {
			return Seller.builder().username(username).spassword(spassword).sname(sname)
					.semail(semail).businessNo(businessNo).businessName(businessName).stel(stel).build();
		}
	}
	
	@Data
	public class FindBysId {
		@NotEmpty(message="이메일는 필수입력입니다")
		@Email(message="잘못된 이메일 형식입니다")
		private String semail;
		private String sname;
	}
	
	@Data
	public class ResetPassword {
		//@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
		
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String semail;
	}

	@Data
	public class ChangePassword {	
		@NotEmpty(message="비밀번호는 필수입력입니다")
		private String spassword;
		
		@NotEmpty(message="새 비밀번호는 필수입력입니다")
		private String newpassword;
	}

	@Data
	@Builder
	public static class Read {
		private String username;
		private String sname;
		private String semail;
		private Integer stel;
		private String businessName;
		private Integer businessNo;
		
	}

	@Data
	@Builder
	public static class Update {
		private String semail;
		private Integer stel;
		private String spassword;
		
		public Seller toEntity() {
			return Seller.builder().semail(semail).spassword(spassword).stel(stel).build();		}
	}
}
