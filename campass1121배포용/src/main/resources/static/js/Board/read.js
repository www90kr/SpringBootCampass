	let username = null;

	function getBno() {
  		const params = new URLSearchParams(location.search);
  		const bno = params.get("bno")
  		
		if(isNaN(bno)) {
			alert("게시물을 찾을 수 없습니다");
			location.href = "/";
		}
		return bno;
	}
	
	function printBoard(board) {
		console.log(board)
		
		$("#bno").text(board.bno);
		$("#writeday").text(board.bWriteTime);
		$("#writer").text(board.username);
		$("#title").val(board.bTitle);
		$("#content").html(board.bcontent);
		$("#read_cnt").text(board.readCnt);
		$("#good_cnt").text(board.goodCnt);
		$("#bad_cnt").text(board.badCnt);
		
		if(username!=null) {
			$("#comment_textarea").prop("readonly",false);
			$("#commentBtnArea").css("display", "block");
			if(username===board.writer) {
				$("#btnArea").css("display", "block");
			} else {
				$("#good_btn").prop("disabled", false);
				$("#bad_btn").prop("disabled", false);
			}
		}
	}
	
	function printComments(comments) {
		$("#comments").empty();
		const template = $('#comment').clone().html();
		$.each(comments, function(idx, comment) {
			const html = template.replace("{writer}",comment.username).replace("{content}", comment.cContent)
				.replace("{writeTime}", comment.cWriteTime).replace("{cno}", comment.cno);
			const $result = $(html);
			if(comment.writer!==username)
				$result.find('.delete_comment').css('display','none');
			$('#comments').append($result);
		});
	}
	
	
	$(document).ready(async function() {
		let boardNo = 0; 
		
		/* 로그인한 사용자 아이디를 저장. 비로그인이면 null
		if(loginInfo!=="anonymousUser") {
			username = loginInfo.username;
		} */
		
		const bno = getBno();
		console.log(bno)
		const api = '/board/read?bno=' + bno;
		const user ='spring';
	
		try {
	  		const response = await $.ajax({
					url : "/board/read/rest",
					data : {bno : bno, username:user},
					success : function(data){
						console.log(data)
					}
				});
	  		console.log(response.result)
	  		printBoard(response.result);
	  		
	  		if(response.result.comments!==null) 
	  			printComments(response.result.comments);
	  		boardNo = response.bno;
	  		$('section').css('visibility','visible');
	  		
		} catch(err) {
			
			console.log(err);
		}
		
		// 댓글 작성
		$("#commentBtnArea").on("click", "#write", function() {
			const param = {
				bno: boardNo,
				content : $("#comment_textarea").val()
			};
			$.ajax({url: "/comments/new", method:"post", data:param}).done(comments=>{
				$("#comment_textarea").val("");
				printComments(comments);
			});
		});
		
		$("#comments").on("click", ".delete_comment", function() {
			const param = {cno : $(this).attr("data-cno"), bno : boardNo};
			$.ajax({url: "/comments", method:"delete", data:param}).done(comments=>	printComments(comments));
		});
	})
	