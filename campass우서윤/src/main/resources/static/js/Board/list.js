
$(document).ready(async function() {

	// 리스트 db연결
	  const apiUrl = '/board/all';
      const pageno = getPageno();
      const params = {pageno}
      $.ajax({url: apiUrl, data: params})
      		  .done((response)=>{
				 	printBoardList(response.result.boardList);
				    const pagination = getPagination(response.result);
				    printPagination(pagination);
			}).fail((error)=>{
				console.log(error);
			});			
})		

// 1. 주소에서 pageno 잘라내는 함수
function getPageno() {
	const params = new URLSearchParams(location.search);
	const pageno = params.get("pageno");
	
		if(pageno==null)
			return 1;
		if(isNaN(pageno))
			return 1;
		if(pageno<1)
			return 1;
			return pageno;
		}
		
// 글출력 - <template> 사용
function printBoardList(boardList) {
	const $list = $('#boardList');
	const $t = $('#tr_template').html();
		for (const b of boardList) {
			$list.append($t.replace('{bno1}',b.bcateName).replace('{bno2}',b.bno).replace('{title}',b.btitle)
				 .replace('{username}',b.username).replace('{bWriteTime}',b.bwriteTime).replace('{readCnt}',b.readCnt));
		}
		console.log(boardList)
	}
	
// prev, start, end, next 계산
		function getPagination({pageno, pagesize, totalcount, blocksize = 5}) {
			const countOfPage = Math.ceil(totalcount / pagesize);
			const prev = Math.floor((pageno - 1) / blocksize) * blocksize;
			const start = prev + 1;
			let end = prev + blocksize;
			let next = end + 1;
			if (end >= countOfPage) {
				end = countOfPage;
				next = 0;
			}
			return {pageno, prev, start, end, next};
		}

// pagination 출력
		function printPagination({pageno, prev, start, end, next}) {
			if (prev > 0)
				$('#prev').css('display', 'block').children('a').attr("href", "/board/list?pageno=" + prev);
			if (next > 0)
				$('#next').css('display', 'block').children('a').attr("href", "/board/list?pageno=" + next);

			const pagination_li = $('.pagination li');
			for (let i = start; i <= end; i++) {
				const $li = $(pagination_li[i]);
				$li.css('display', 'block').children('a').attr("href", "/board/list?pageno=" + i).text(i);
				if (i === pageno)
					$li.addClass('active');
			}
		}
		