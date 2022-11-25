package com.campass.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.campass.demo.dto.BuyerDto;
import com.campass.demo.entity.Buyer;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuyerDto {
	@Data
	public static class IdCheck {
		@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
	}
	
	@Data
	public static class NicknameCheck {
		@NotEmpty(message="닉네임은 필수입력입니다")
		private String bnickname;
	}
	
	@Data
	public static class EmailCheck {
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String bemail;
	}
	
	@Data
	public static class TelCheck {
		@Email
		@NotEmpty(message="연락처은 필수입력입니다")
		private Integer btel;
	}
	
	@Data
	@Builder
	public static class Join {
		@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
		
		@NotEmpty(message="이름은 필수입력입니다")
		private String bname;
		
		@NotEmpty(message="비밀번호는 필수입력입니다")
		private String bpassword;
		
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String bemail;
		
		@Size(min= 2, max = 10, message = "닉네임은 몇글자")
		@NotEmpty(message="닉네임은 필수입력입니다")
		private String bnickname;
		
		@NotEmpty(message="연락처는 필수입력입니다")
		private Integer btel;
		
		@NotEmpty(message="주소는 필수입력입니다")
		private String badress;
		
		private Integer enabled;
		private String role;
		
		
		public Buyer toEntity() {
			return Buyer.builder().username(username).bpassword(bpassword).bname(bname)
					.bemail(bemail).bnickname(bnickname).badress(badress).btel(btel).build();
		}
	}
	
	@Data
	public class FindBybId {
		@NotEmpty(message="이메일는 필수입력입니다")
		@Email(message="잘못된 이메일 형식입니다")
		private String bemail;
		private String bname;
	}
	
	@Data
	public class ResetPassword {
		//@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
		
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String bemail;
	}

	@Data
	public class ChangePassword {	
		@NotEmpty(message="비밀번호는 필수입력입니다")
		private String bpassword;
		
		@NotEmpty(message="새 비밀번호는 필수입력입니다")
		private String newpassword;
	}

	@Data
	@Builder
	public static class Read {
		private String username;
		private String bname;
		private String bemail;
		private String bnickname;
		private Integer btel;
		private String badress;
		
	}

	@Data
	@Builder
	public static class Update {
		private String bemail;
		private Integer btel;
		private String bpassword;
		private String badress;
		private String bnickname;
		
		public Buyer toEntity() {
			return Buyer.builder().bemail(bemail).badress(badress).bnickname(bnickname).bpassword(bpassword).btel(btel).build();		}
	}
	
	   //주문시 회원정보 가져오기
	   @Data
	   public class buyerInfo{
	      private String username;   //회원코드
	      private String badress;      //배송주소
	      private String bname;      
	      private Integer btel;
	      private String bemail;
	   }
}
