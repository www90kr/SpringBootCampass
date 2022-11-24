$(function() {
	function requestMessageState() {
		$.ajax("/buyer/memos/state").done(result=>{
			if(result>0) 
				$("#target").text("읽지 않은 메시지가 " + result +"개 있습니다");
			else 
				$("#target").text("새로운 메시지는 없습니다");
		})
	}
	requestMessageState();	
});

$(function() {
	function requestMessageState() {
		$.ajax("/seller/memos/state").done(result=>{
			if(result>0) 
				$("#target").text("읽지 않은 메시지가 " + result +"개 있습니다");
			else 
				$("#target").text("새로운 메시지는 없습니다");
		})
	}
	requestMessageState();	
});
