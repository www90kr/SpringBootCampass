package com.campass.demo.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.campass.demo.dao.CZoneDao;
import com.campass.demo.dao.CampingDao;
import com.campass.demo.dto.MyReservationDto;
import com.campass.demo.dto.ReviewDto;
import com.campass.demo.dto.SearchDto;
import com.campass.demo.dto.ReservationDto.RWriteDto;
import com.campass.demo.entity.CZone;
import com.campass.demo.entity.Reservation;
import com.campass.demo.entity.Review;
import com.campass.demo.service.BBCampingService;

@Controller
@RequestMapping
public class B_CampingController {

    @Autowired(required = true)
    private BBCampingService BBcampingService;
    @Autowired(required = true)
    private CZoneDao czoneDao;
    @Autowired(required = true)
    private CampingDao campingDao;

    HttpSession session;
    HttpServletRequest request;
    // 개발용 가짜 아이디. 로그인을 붙이고 나면 지우고 principal로 변경
    // @Value("Summer")

    @Value("c:/upload")
    private String imageFolder;

    // mvc : model, view
    /*
     * public ModelAndView aaa() {
     * return new ModelAndView("aaa").addObject("list", null);
     * }
     * 
     * public String aaa(Model model) {
     * model.addAttribute("list", null);
     * return "aaa";
     * }
     */

    // ===============photo File data=====================
    // 확장자로 데이터의 MIME 타입을 리턴하는 함수
    // jpg, png, gif -> 확장자가 틀리면 브라우저가 제대로 처리못할수도????
    private MediaType getMediaType(String imagename) {
        // spring11.jpg -> .을 찾아서 .다음부터 자르면 확장자
        int position = imagename.lastIndexOf(".");
        String ext = imagename.substring(position + 1).toUpperCase();
        if (ext.equals("JPG"))
            return MediaType.IMAGE_JPEG;
        else if (ext.equals("PNG"))
            return MediaType.IMAGE_PNG;
        else
            return MediaType.IMAGE_GIF;
    }

    // entity 에 내가원하는 데이터가 없으면 dto를 따로 만들어서 Mapper에서 다른테이블과 조인쿼리를 따로만들면 된다는사실을 알았다
    // 교수님천재다 진짜 ,, 와,,, 그랜절박음 👍👍👍
    // 메뉴>캠핑리스트
    @RequestMapping(value = "/buyer/campingAll", method = RequestMethod.GET)
    public String List(@RequestParam(defaultValue = "1") Integer pageno, Model model) {
        model.addAttribute("page", BBcampingService.campingAll(pageno));
        return "BBcamping/campingAll";
    }

    // 메뉴>캠핑>캠핑리드 + 캠핑존리스트 (+캠핑리뷰 리스트 나중에)
    @RequestMapping(value = "/buyer/campingRead", method = RequestMethod.GET)
    public ModelAndView Read(Principal principal, Integer cCode) {

        //String username = principal == null ? usernameForDevelopement : principal.getName();

        // if(campingRead==null)
        // return "redire"
        // + "ct:/seller/403";
        // Camping campingRead = campingService.campingRead(cCode);

        return BBcampingService.campingRead(cCode);

    }
    @RequestMapping(value = "/buyer/403", method = RequestMethod.GET)
    public String errorView() {
        return "/BBcamping/error";
    }

    // 캠핑검색목록결과
    @RequestMapping(value = "/buyer/campingSearch", method = RequestMethod.POST)
    public ModelAndView campingList(SearchDto searchDto, Model model, HttpSession session) {

        model.addAttribute("search", searchDto);
        session.setAttribute("search", searchDto);

        return BBcampingService.CampingList(searchDto);
    }

    // 캠핑검색목록-디테일
    @RequestMapping(value ="/buyer/searchDetail", method = RequestMethod.GET)
    public ModelAndView campingDetailView(Integer cCode, SearchDto searchDto, Model model) {

        return BBcampingService.campingDetail(cCode, searchDto);
    }

    // 캠핑예약 프론트단
    @RequestMapping(value = "/buyer/reserve", method = RequestMethod.GET)
    public String ReserveView(Integer cCode, Integer czCode, Model model) {
        String cName = campingDao.getcNameById(cCode);
        CZone cZoneModel = czoneDao.findbyCzCode(czCode);

        model.addAttribute("cCode", cCode);
        model.addAttribute("czCode", czCode);
        model.addAttribute("cName", cName);
        model.addAttribute("cZone", cZoneModel);

        return "/reserve/addForm";

    }

    // 캠핑예약 백단
    @RequestMapping(value = "/buyer/reserve", method = RequestMethod.POST)
    public String Reserve(RWriteDto dto, Integer cCode, Integer rPrice, Integer czCode, Principal principal,
            Model model) {
        Reservation result = BBcampingService.insertReservation(dto, principal.getName(), cCode, czCode, rPrice);
        String cname = campingDao.getcNameById(cCode);
        String czname = campingDao.getczNameById(czCode);
        model.addAttribute("cname", cname);
        model.addAttribute("czname", czname);
        model.addAttribute("reservation", result);
        return "/reserve/commitPage";
    }

    // 예약금액은 만들긴했는데 자바가 아니라 자바스크립트로 써야할것같다 예약입력페이지에서 사용자가 날짜를 바꿀수도있기때문이다. 천상 자바스크립트로
    // 바뀌어야할듯
    // 캠핑검색예약(데이터받아서예약페이지띄우기)
    @RequestMapping(value = "/buyer/searchreserve", method = RequestMethod.GET)
    public String RReserveView(Model model, HttpSession session, Integer cCode, Integer czCode) {
        SearchDto searchDto = (SearchDto) session.getAttribute("search");
        LocalDate rCheckIn = searchDto.getCh_checkIn();
        LocalDate rCheckOut = searchDto.getCh_checkOut();
        Period period = Period.between(rCheckIn, rCheckOut);
        Integer rPeriod = period.getDays();

        String cName = campingDao.getcNameById(cCode);
        CZone cZoneModel = czoneDao.findbyCzCode(czCode);

        Integer rPrice = cZoneModel.getCzPrice() * rPeriod;

        model.addAttribute("rPrice", rPrice);
        model.addAttribute("rPeriod", rPeriod);
        model.addAttribute("rCheckIn", rCheckIn);
        model.addAttribute("rCheckOut", rCheckOut);
        model.addAttribute("cCode", cCode);
        model.addAttribute("czCode", czCode);
        model.addAttribute("cName", cName);
        model.addAttribute("cZone", cZoneModel);
        model.addAttribute("search", searchDto);

        return "/reserve/searchAddForm";

    }

    // 캠핑검색예약 백단
    @RequestMapping(value = "/buyer/searchreserve", method = RequestMethod.POST)
    public String RReserve(RWriteDto dto, Integer cCode, Integer rPrice, Integer czCode, Principal principal,
            Model model) {
        Reservation result = BBcampingService.insertReservation(dto, principal.getName(), czCode, cCode, rPrice);
        model.addAttribute("reservation", result);

        return "/reserve/commitPage";
    }

    // 한 버튼에 두개이상의 조건이 붙을때 버튼에 th:if 먹여주고 그 상위 태그에 또 th:if 먹여주면 된다 진짜 너무 신기하다
    // ✨✨✨✨✨✨✨ 완전최고다 교수님만만세
    // 예약내역페이지는 당연히 buy seller 따로 경로 따로 주려고 생각하고있었는데 페이지 한화면에 로그인권한 설정을 다르게 해서 판매자도
    // 구매자도 접근 가능하게 할 수 있다는 사실을 알았다
    // --판매자는 판매자 아이디와 캠핑코드를 파라미터로 받아서 캠핑장에 들어온 예약내역을 볼수있게끔처리
    // --구매자는 구매자 아이디와 예약번호를 받아서 나의예약내역을 볼수 있게끔 처리
    // 정말이지 너무신기한 일이당 점말점말 너무신기하당 >_< 타임리프 for 반복문 돌기 하루종일헤맸는데
    // for each를 같은 요소인 td에 주는게 아니라 tr에 먹이는 거였다. 별짓을 다해도 안되던게 이젠된다 너무너무 신기방기하당ㅋㅎㅋㅎ
    // 예약내역
    @RequestMapping(value = "/buyer/reHistory", method = RequestMethod.GET)
    public String MyReserve(Reservation reservation, Principal principal, Model model) {
        List<MyReservationDto> result = BBcampingService.mymyReservation(reservation, principal.getName());
        model.addAttribute("reservation", result);
        return "/reserve/reHistory";
    }

    // 예약취소 (24시간 이내에만 가능하게끔 조건을 걸어야한다 이건 교수님께 말씀드려야함!) ✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨
    @RequestMapping(value = "/buyer/reserveDelete", method = RequestMethod.DELETE)
    public String DeleteReserve(Integer rCode, Principal principal) {
        String loginId = principal.getName();
        BBcampingService.reserveDelete(rCode, loginId);

        return "redirect:/buyer/reHistory";
    }

    @RequestMapping(value = "/buyer/cReviewAdd", method = RequestMethod.POST)
    public String creviewAdd(ReviewDto.WriteDto dto, Integer rCode, Principal principal, Integer cCode, Model model) {

        String loginId = principal.getName();
        Review reviewModel = BBcampingService.creviewAdd(dto, loginId, rCode, cCode);

        model.addAttribute("review", reviewModel);
        return "redirect:/buyer/reHistory";
    }

    @RequestMapping(value = "/buyer/creviewDelete", method = RequestMethod.GET)
    public String DeleteReview(Integer cReviewNo, Integer cCode, Principal principal) {
        String loginId = principal.getName();
        BBcampingService.creviewDelete(cReviewNo, loginId);
        
        return "redirect:/buyer/campingRead?cCode=" + cCode;
    }

    
    
}
