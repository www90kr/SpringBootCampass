package com.campass.demo.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.campass.demo.entity.Camping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//입력용DTO
public class CampingDto {
	@Data
	public class CWriteDto{
		private String cTel		;
		private String cName		;
		private String cContent		;
		private String cAddr		;
		private String cSite		;
		private MultipartFile cPhoto		;
		private String cFileName		;
		private Integer c_ctCode		;
		private Integer c_caCode		;
		private Integer c_cOptionCode		;
		
		public Camping toCEntity() {
			return Camping.builder().cTel(cTel).c_caCode(c_caCode).cAddr(cAddr).cContent(cContent)
					.cFileName(cFileName).cName(cName).c_cOptionCode(c_cOptionCode)
					.cSite(cSite).c_ctCode(c_ctCode).cTel(cTel).build();
		}
	}

	
//출력용DTO
//	private List<ReviewDto.ReadDto> reviews; 를 컬럼으로 추가시켜야함 나중에 
	// 리뷰 게시판으로 갈수도있음 그럼 추가 ㄴㄴ 리뷰 따로 
	//camping read 메소드를 readDto가 아닌 그냥 camping entity로 처리했다  이유는 dto안의 dto로  처리하려니 오류가 너무 많이뜬다; 현재는 readDTO가 쓰이는 곳이없다;;
	@Data
	public class CReadDto{
		private Integer cCode		;
		private String cTel		;
		private String cName		;
		private String cContent		;
		private String cAddr		;
		private String cSite		;
		private Integer cLikeCnt		;
		private Integer cReviewStarAvg		;
		private Integer cReviewCnt		;
		private String cPhoto		;
		private String cFileName		;
		private Integer c_ctCode		;
		private Integer c_caCode		;
		private Integer c_cOptionCode		;
		
	}
	
	//출력용DTO	
	//ListDto 얘때문에 오늘 애먹었다 ㅠ_ㅠ 교수님이 도와주셔서 겨우 List구현했다.  오류뜬이유  : static 은 안줬기때문이었음 어이없다
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CListDto{
		private Integer cCode	;
		private String cName		;
		private String cAddr		;
		private String cPhoto;
		private String caName	;
		private Integer c_caCode		;
		private Integer c_ctCode		;
		public Camping toCEntity() {
			return Camping.builder().cCode(cCode).cAddr(cAddr).cName(cName).c_caCode(c_caCode).c_ctCode(c_ctCode).build();
		}
		
	}
		//출력용DTO

		@Data
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class CSearchDto{
			private Integer cCode	;
			private String cName		;
			private String cAddr		;
			private String cPhoto;
			
			public Camping toCEntity() {
				return Camping.builder().cCode(cCode).cAddr(cAddr).cName(cName).build();
			}
			
		}
		//입력용DTO
		@Data
		public class CUpdateDto{
			private Integer cCode		;
			private String cTel		;
			private String cName		;
			private String cContent		;
			private String cAddr		;
			private String cSite		;
			private MultipartFile cPhoto		;
			private String cFileName		;
			private Integer c_ctCode		;
			private Integer c_caCode		;
			private Integer c_cOptionCode		;
			
			public Camping toCEntity() {
				return Camping.builder().cCode(cCode).cTel(cTel).c_caCode(c_caCode).cAddr(cAddr).cContent(cContent)
						.cFileName(cFileName).cName(cName).c_cOptionCode(c_cOptionCode)
						.cSite(cSite).c_ctCode(c_ctCode).build();
			}
		}
		
		//딜리트 삭제
		@Data
		public class CDeleteDto{
			private Integer cCode	;
			private String c_sId	;

			
		}
		
		
		//페이징
		@Data
		@AllArgsConstructor
		public class CPageDto{
			private Integer pageno;
			private Integer pagesize;
			private Integer totalcount;
			private Collection<CListDto> CList;
		}
		
		
		//리뷰추가한 캠핑장리스트 (최종이라고보면됨 시도중혼자ㅠ) 근데 내 컨트롤러는 이걸 써먹을일이 없을거같다 확인해보고 지우기!!! 
		@Data
		@AllArgsConstructor
		@NoArgsConstructor
		public static class CReadDtowithreviewList{
			private Integer cCode		;
			private String cTel		;
			private String cName		;
			private String cContent		;
			private String cAddr		;
			private String cSite		;
			private Integer cLikeCnt		;
			private Integer cReviewStarAvg		;
			private Integer cReviewCnt		;
			private String cPhoto		;
			private String cFileName		;
			private Integer c_ctCode		;
			private Integer c_caCode		;
			private Integer c_cOptionCode		;
			private List<ReviewDto.ListDto> reviewList ;
			
		
		}
		
		
}
