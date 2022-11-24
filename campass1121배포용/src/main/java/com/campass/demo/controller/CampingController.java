package com.campass.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.campass.demo.dto.CZoneDto;
import com.campass.demo.dto.CampingDto;
import com.campass.demo.dto.MyCampingDto;
import com.campass.demo.dto.CZoneDto.CZUpdateDto;
import com.campass.demo.dto.CampingDto.CUpdateDto;
import com.campass.demo.entity.CZone;
import com.campass.demo.entity.Camping;
import com.campass.demo.entity.Reservation;
import com.campass.demo.service.CampingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
public class CampingController {

    @Autowired(required = true)
    private CampingService campingService;

    @Value("Spring")
    private String usernameForDevelopement;

    HttpSession session;
    HttpServletRequest request;
    // // 개발용 가짜 아이디. 로그인을 붙이고 나면 지우고 principal로 변경
    // @Value("Spring")
    // private String usernameForDevelopement;

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

    //
    @GetMapping(path = "com/images/{imagename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> showProfile(@PathVariable String imagename) {
        File file = new File(imageFolder, imagename);
        if (file.exists() == false)
            return null;
        // 요청, 응답은 헤더와 바디로 구성
        // 헤더는 바디의 종류, 취급 방법등의 메타 데이터가 들어간다
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getMediaType(imagename));

        // inline을 주면 브라우저 처리, attachment를 주면 다운로드
        headers.add("Content-Disposition", "inline;filename=" + imagename);
        try {
            // 파일은 byte[]로 내보내면 된다
            // 파일을 byte의 배열로 바꾸는 방법들이 몇가지가 있는데 그 중
            // 한 줄짜리
            return ResponseEntity.ok().headers(headers)
                    .body(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 캠핑전체리스트(메뉴 >캠핑)
    @RequestMapping(value = "seller/campingAll", method = RequestMethod.GET)
    public String List(@RequestParam(defaultValue = "1") Integer pageno, Model model, Principal principal) {
        String loginId = principal.getName();
        model.addAttribute("page", campingService.campingAll(pageno, loginId));
        return "camping/campingAll";
    }

    // 캠핑추가
    @GetMapping("seller/cAdd")
    public String campingAddView() {
        return "camping/write";
    }

    @PostMapping("seller/cAdd")
    public String campingAdd(CampingDto.CWriteDto dto, Principal principal) {
        String loginId = principal.getName();
        Camping campingModel = campingService.campingWrite(dto, loginId);
        return "redirect:/seller/campingAll";
    }

    // 캠핑리드 + 캠핑존리스트 (+캠핑리뷰 리스트 나중에)
    @RequestMapping(value = "seller/campingRead", method = RequestMethod.GET)
    public ModelAndView Read(Principal principal, Integer cCode) {

        String username = principal.getName();

        // if(campingRead==null)
        // return "redire"
        // + "ct:/seller/403";
        // Camping campingRead = campingService.campingRead(cCode);

        return campingService.campingRead(cCode, username);

    }

    @GetMapping("seller/403")
    public String errorView() {
        return "camping/error";
    }

    @RequestMapping(value = "seller/campingUpdate", method = RequestMethod.GET)
    public String UpdateView(Integer cCode, Model model, Principal principal) {
        String loginId = principal.getName();
        model.addAttribute("update", campingService.campingRead(cCode, loginId));
        model.addAttribute("cCode", cCode);
        return "camping/update";
    }

    @RequestMapping(value = "seller/campingUpdate", method = RequestMethod.POST)
    public String Update(CUpdateDto dto, Model model, Principal principal) {
        String loginId = principal.getName();
        Camping campingUpdate = campingService.campingUpdate(dto, loginId);
        model.addAttribute("update", campingUpdate);
        return "redirect:/seller/campingRead?cCode=" + dto.getCCode();
    }

    @RequestMapping(value = "seller/campingDelete", method = RequestMethod.DELETE)
    public String Delete(Integer cCode, Principal principal) {
        String loginId = principal.getName();
        Integer campingDelete = campingService.campingDelete(cCode, loginId);

        return "redirect:/seller/campingAll";
    }

    // 예약내역 (판매자) 마이캠핑장 리스트 띄우기
    @RequestMapping(value = "seller/myCampingHistory", method = RequestMethod.GET)
    public String MyCamping(Reservation reservation, Principal principal, Model model) {
        List<MyCampingDto> result = campingService.mymyCamping(reservation, principal.getName()); // 리스트 띄우는 기능은
                                                                                                  // reservation 에서
                                                                                                  // 가져오지만 구매회원과 다른
                                                                                                  // 변수받아와야해서 같은 로직으로
                                                                                                  // 짤수없다
        model.addAttribute("reservation", result);
        return "reserve/myCamping";
    }

    // 예약내역 (판매자) 상태 변경하기 마이캠핑장 업데이트
    @RequestMapping(value = "seller/myCampingReserve", method = RequestMethod.POST)
    public String MyCampingReserve(Integer rCode, Principal principal, Model model) {
        String loginId = principal.getName();
        campingService.myCampingReserve(rCode, loginId);

        return "redirect:/seller/myCampingHistory";
    }

    // 환불
    @RequestMapping(value = "seller/yourReserveDelete", method = RequestMethod.DELETE)
    public String yourReserveDelete(Integer rCode, Principal principal) {
        String loginId = principal.getName();
        campingService.yourReserveDelete(rCode, loginId);

        return "redirect:/seller/myCampingHistory";
    }
    // ==============================================================

    @GetMapping("seller/czAdd")
    public String cZoneAdd(Model model, Integer cCode) {
        model.addAttribute("czAdd", cCode);
        return "czone/write";

    }

    @PostMapping("seller/czAdd")
    public String cZoneAdd(CZoneDto.CZWriteDto dto, Model model, Principal principal, Integer cCode) {

        CZone czoneModel = campingService.cZoneWrite(dto, usernameForDevelopement, cCode);
        model.addAttribute(czoneModel);
        return "redirect:/seller/campingRead?cCode=" + czoneModel.getCz_cCode();

    }

    // ======update 부터 delete 까지헷갈림

    @RequestMapping(value = "seller/czoneUpdate", method = RequestMethod.GET)
    public String czUpdateView(Integer cCode, Integer czCode, Model model, Principal principal) {
        model.addAttribute("update", campingService.campingRead(cCode, usernameForDevelopement));
        model.addAttribute("cCode", cCode);
        model.addAttribute("czCode", czCode);
        return "czone/update";
    }

    @RequestMapping(value = "seller/czoneUpdate", method = RequestMethod.POST)
    public String czUpdate(CZUpdateDto dto, Model model, Principal principal, Integer cCode, Integer czCode) {

        campingService.czoneUpdate(dto, usernameForDevelopement, cCode, czCode);
        return "redirect:/seller/campingRead?cCode=" + cCode;
    }

    @DeleteMapping("seller/czDelete")
    public String Delete(Integer czCode, Principal principal, Integer cCode) {

        Integer cZoneDelete = campingService.czDelete(czCode, usernameForDevelopement);

        return "redirect:/seller/campingRead?cCode=" + cCode;
    }

}
