package com.campass.demo.controller;

import java.security.Principal;

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

import com.campass.demo.dto.SearchDto;
import com.campass.demo.service.ComCampingService;

@Controller
public class ComCampingController {
    @Autowired(required = true)
    private ComCampingService comCampingService;

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

    // 홈
    @RequestMapping(value = "/com", method = RequestMethod.GET)
    public String comcampingList() {

        return "/comm/index";
    }

    // entity 에 내가원하는 데이터가 없으면 dto를 따로 만들어서 Mapper에서 다른테이블과 조인쿼리를 따로만들면 된다는사실을 알았다
    // 교수님천재다 진짜 ,, 와,,, 그랜절박음 👍👍👍
    // 메뉴>캠핑리스트
    @RequestMapping(value = "/com/campingAll", method = RequestMethod.GET)
    public String List(@RequestParam(defaultValue = "1") Integer pageno, Model model) {
        model.addAttribute("page", comCampingService.campingAll(pageno));
        return "/comm/campingAll";
    }

    // 메뉴>캠핑>캠핑리드 + 캠핑존리스트 
    @RequestMapping(value = "/com/campingRead", method = RequestMethod.GET)
    public ModelAndView Read(Principal principal, Integer cCode) {

        return comCampingService.campingRead(cCode);

    }

    @RequestMapping(value = "/com/403", method = RequestMethod.GET)
    public String errorView() {
        return "/comm/error";
    }

    // 캠핑검색목록결과
    @RequestMapping(value = "/com/campingSearch", method = RequestMethod.POST)
    public ModelAndView compingList(SearchDto searchDto, Model model, HttpSession session) {
        model.addAttribute("search", searchDto);
        session.setAttribute("search", searchDto);

        return comCampingService.CampingList(searchDto);
    }

    // 캠핑검색목록-디테일
    @RequestMapping(value = "/com/searchDetail", method = RequestMethod.GET)
    public ModelAndView campingDetailView(Integer cCode, SearchDto searchDto, Model model) {

        return comCampingService.campingDetail(cCode, searchDto);
    }

}
