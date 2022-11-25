// 웹소켓 연결, 메시지를 보내고, 수신하는 자바스크립트를 작성하겠다
// 관리자에게 메시지를 보낸다고 하자 -> 관리자가 어떤 페이지를 보고 있지??? 모른다
// 관리자의 모든 html이 웹 소켓 연결을 해야 한다

$(function() {
	// 웹 소켓을 저장할 변수
	let wsocket;
	
	// 웹 소켓 연결 함수
	function webSocketConnect() {
		if(wsocket==undefined) {
			wsocket = new WebSocket("ws://localhost:8087/websocket");
			console.log(wsocket);
			
			wsocket.onmessage = function(evt) {
				// 이벤트 객체 evt의 data 속성에 서버 메시지가 담겨있다
				const obj = JSON.parse(evt.data);
				$("#target").parent().attr("class","alert alert-warning")
				$("#target").text(obj.content);
			}
		}
		
		$("#send").click(function() {
			// 보낼때는 받는 사람 아이디를 앞에 붙여서 보낸다
			wsocket.send("SYSTEM:" + $("#message").val());
		})
	}
	
	webSocketConnect();	
});
