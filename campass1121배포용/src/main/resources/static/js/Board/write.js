// 썸머노트 띄우기 
 $(document).ready(function () {
 
        $('#summernote').summernote({
  		  height:400,                 	// 에디터 높이
		  focus: true,                 	// 에디터 로딩후 포커스를 맞출지 여부
		  lang: "ko-KR",				// 한글 설정
		  placeholder: '내용',
          disableResizeEditor: true, 	// 크기 조절 기능 삭제
            toolbar: [
            // 글꼴 설정
            ['fontname', ['fontname']],
            // 글자 크기 설정
            ['fontsize', ['fontsize']],
            // 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            // 글자색
            ['color', ['forecolor','color']],
            // 표만들기
            ['table', ['table']],
            // 글머리 기호, 번호매기기, 문단정렬
            ['para', ['ul', 'ol', 'paragraph']],
            // 줄간격
            ['height', ['height']],
            // 코드보기, 확대해서보기, 도움말
            ['view', ['codeview','fullscreen', 'help']]
        ],
        // 추가한 글꼴
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
        // 추가한 폰트사이즈
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
          
             })



// 글작성
	$(document).ready(function() {
	
		$('#write_btn').click(async function() {
			
			// #frm안에 같은 name을 가지고있는 값들을 각각 담아서 전송 
			
			const formData = new FormData(document.querySelector('#frm'));
			
			for (let key of formData.keys()) {
				console.log(key, ":", formData.get(key));
			}


			$.ajax({
				url:'/board/save',
				method:'post',
				data:formData,
				contentType:false,
				processData:false
			})
			.done((result, text, response)=>{ alert("작성이 완료됐습니다"); location.href = "/board/read?bno="+result.result.bno; })
			.fail((response, text, message)=>{})
		});
	})
})