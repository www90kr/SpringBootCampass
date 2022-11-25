package com.campass.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.campass.demo.dao.CZoneDao;
import com.campass.demo.dao.CampingDao;
import com.campass.demo.dao.ReservationDao;
import com.campass.demo.dao.ReviewDao;
import com.campass.demo.dao.SearchDao;
import com.campass.demo.dto.CZoneDto;
import com.campass.demo.dto.CampingDto;
import com.campass.demo.dto.MyReservationDto;
import com.campass.demo.dto.PageDto;
import com.campass.demo.dto.ReviewDto;
import com.campass.demo.dto.SearchDto;
import com.campass.demo.dto.CZoneDto.CZListDto;
import com.campass.demo.dto.ReservationDto.RWriteDto;
import com.campass.demo.entity.Camping;
import com.campass.demo.entity.Reservation;
import com.campass.demo.entity.Review;
import com.campass.demo.exception.BoardNotFoundException;
import com.campass.demo.exception.JobFailException;

@Service
public class BBCampingService {

    @Autowired
    private CampingDao campingDao;
    @Autowired
    private CZoneDao cZoneDao;
    @Autowired
    private SearchDao searchDao;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReviewDao reviewDao;

    @Value("6")
    private Integer pagesize;
    @Value("5")
    private Integer blocksize;

    // private PageDto PageDto;

    // 일단 변수 선언 해주는데 파라미터값으로 넣어야할지도? 세션정보 받아와야되는데 session.getattribute 가 안먹혀서 가져왔다
    // 가져오니까 된다 올ㅋ
    // 근데 로그인아이디는 principal 객체써도되는것같다 둘다 이용해보기로함
    HttpSession session;
    HttpServletRequest request;

    @Value("c:/upload")
    private String imageFolder;

    @Value("http://localhost:8087/images/")
    private String imagePath;

    public PageDto campingAll(Integer pageno) {
        // 1-> 1,10. 2->11,20, 3->21, 30
        Integer startRownum = (pageno - 1) * pagesize + 1;
        Integer endRownum = startRownum + pagesize - 1;
        Map<String, Object> map = new HashMap<>();
        map.put("startRownum", startRownum);
        map.put("endRownum", endRownum);

        List<CampingDto.CListDto> list = campingDao.BBfindAll(startRownum, endRownum);

        // pageno prev start end next
        // 1-5 0 1 5 6
        // 6-10 5 6 10 11

        Integer start = (pageno - 1) / blocksize * blocksize + 1;
        Integer prev = start - 1;
        Integer end = start + blocksize - 1;
        Integer next = end - 1;

        // 만약 글의 개수가 123개라면
        // pageno prev start end next
        // 11-13 10 11 15->13 16->0
        Integer count = campingDao.count();
        Integer countOfPage = (count - 1) / pagesize + 1;

        if (end >= countOfPage) {
            end = countOfPage;
            next = 0;
        }

        List<Integer> pagenos = new ArrayList<>();
        for (Integer i = start; i <= end; i++)
            pagenos.add(i);

        return new PageDto(prev, pagenos, next, pageno, list);

    }

    // 캠핑리드에 캠핑데이터와 czList를 넣어줌
    public ModelAndView campingRead(Integer cCode) {
        ModelAndView mav = new ModelAndView();
        Camping read = campingDao.BBfindById(cCode);

        List<CZListDto> list = cZoneDao.czFindAll(cCode);
        List<ReviewDto.ListDto> reviewList = reviewDao.listReviewBy(cCode);
        
        Integer sum = read.getCReviewStarSum();
        double cnt= read.getCReviewCnt();
        double avg = sum/ (double)cnt;
        mav.addObject("avg", avg);
        mav.addObject("reviewList", reviewList);
        mav.addObject("read", read);
        mav.addObject("czlist", list);
        mav.setViewName("BBcamping/campingRead");

        return mav;
    }

    public ModelAndView CampingList(SearchDto searchDto) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SearchDto", searchDto);
        // map.put("pageDto", PageDto); //나중에 자세히 알아보기 여긴페이징처리생략
        Integer caCode = searchDto.getCh_caCode();
        Integer ctCode = searchDto.getCh_ctCode();

        String caName = searchDao.getCaName(caCode);
        String ctName = searchDao.getCtName(ctCode);
        List<CampingDto.CListDto> CampingList = campingDao.campingList(map);
        // ArrayList<Heart> heartList = campingDao.getHeartList(loginId);

        // mav.addObject("heartList",heartList);

        mav.addObject("caName", caName);
        mav.addObject("ctName", ctName);
        mav.addObject("searchDto", searchDto);
        mav.addObject("CampingList", CampingList);
        // mav.addObject("pageDto",PageDto);
        mav.setViewName("/BBcamping/campingSearch");
        return mav;
    }

    // searchDto 이걸어떻게갖고오지 방법을 찾았다 교수님이 세션이용하라고 알려주심
    // 해답 :저장할때 - HttpSession session 을 파라미터로 불러옴 > session.setAttribute("이름",객체)
    // 꺼내올때 - HttpSession session 을 파라미터로 다시 불러옴>객체타입 지정할
    // 변수명=(객체타입)session.getAttribute("이름")
    public ModelAndView campingDetail(Integer cCode, SearchDto searchDto) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SearchDto", searchDto);
        map.put("cCode", cCode);
        Camping campingDetail = campingDao.findById(cCode);
        // 검색결과에 따른 가능한 캠핑존리스트만을 불러온다
        List<CZoneDto.CZListDto> cZoneSearch = cZoneDao.czDetail(map);
        //cCode에 맞는 리뷰리스트를 불러온다
        List<ReviewDto.ListDto> reviewList = reviewDao.listReviewBy(cCode);
        
        Integer sum = campingDetail.getCReviewStarSum();
        double cnt= campingDetail.getCReviewCnt();
        double avg = sum/ (double)cnt;
        mav.addObject("avg", avg);
        mav.addObject("reviewList", reviewList);
        mav.addObject("cZoneSearch", cZoneSearch);
        mav.addObject("campingDetail", campingDetail);
        mav.setViewName("/BBcamping/searchDetail");
        return mav;
    }

    // 예약 넣기
    public Reservation insertReservation(RWriteDto dto, String loginId, Integer cCode, Integer czCode, Integer rPrice) {
        Reservation reservation = dto.toRentity().rAdd(loginId, cCode, czCode, rPrice);
        reservationDao.insertReservation(reservation);

        return reservation;

    }

    // 나의예약내역 (일반회원) myReviewDto 안에 한번더 체크하기 엄청 난게 들었다. 난이도 최상! 상관서브쿼리 챙기기 + th:if문 챙기기 
    // dto를 따로만들어서 데이터항목(컬럼)을 하나 추가해서 화면에 t or f 역할로 뿌려준다 0이면 나오고 1이면 나오지 못하게하는 항목
    public List<MyReservationDto> mymyReservation(Reservation reservation, String loginId) {
        return reservationDao.RRmyReservation(loginId);
    }

    // 리뷰쓰기
    public Review creviewAdd(ReviewDto.WriteDto dto, String loginId, Integer rCode, Integer cCode) {

        Review review = dto.toEntity().addInfo(loginId, rCode, cCode);
        reviewDao.insertReview(review);
        
        
       //  제품(Product)의 countOfReview 1 증가, sumOfStar 증가, countOfStar 1 증가
       Camping camping = campingDao.findById(review.getCReview_cCode());
        Camping param = Camping.builder()
                .cReviewCnt(camping.getCReviewCnt()+1).cReviewStarSum(camping.getCReviewStarSum()+review.getCReviewStar())
                .cCode(review.getCReview_cCode()).build();
        campingDao.update(param); 

        
        return review;
    }

    // 리뷰삭제 구매자
    public Integer creviewDelete(Integer cReviewNo, String loginId) {
        String writer = reviewDao.getUserbyId(cReviewNo);
        if (writer.equals(loginId) == false)
            throw new JobFailException("삭제 권한이 없습니다");
        
        Review review = reviewDao.getReviewbyId(cReviewNo);
        
        //  제품(Product)의 countOfReview 1 감소, sumOfStar 감소, countOfStar 1 감소
        Camping camping = campingDao.findById(review.getCReview_cCode());
         Camping param = Camping.builder()
                 .cReviewCnt(camping.getCReviewCnt()-1).cReviewStarSum(camping.getCReviewStarSum()-review.getCReviewStar())
                 .cCode(review.getCReview_cCode()).build();
         campingDao.updateDel(param);
        

        return reviewDao.deleteReview(cReviewNo);
    }

    //예약취소 구매자(예약대기상태일때만)
    public Integer reserveDelete(Integer rCode, String loginId) {
        String writer = reservationDao.RRfindWriterById(rCode).orElseThrow(() -> new BoardNotFoundException());
        if (writer.equals(loginId) == false)
            throw new JobFailException("삭제 권한이 없습니다");
        return reservationDao.cancelReservation(rCode);

    }

}
