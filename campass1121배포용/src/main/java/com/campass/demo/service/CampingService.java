package com.campass.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.campass.demo.dao.CZoneDao;
import com.campass.demo.dao.CampingDao;
import com.campass.demo.dao.ReservationDao;
import com.campass.demo.dto.CZoneDto;
import com.campass.demo.dto.CampingDto;
import com.campass.demo.dto.MyCampingDto;
import com.campass.demo.dto.PageDto;
import com.campass.demo.dto.CZoneDto.CZListDto;
import com.campass.demo.entity.CZone;
import com.campass.demo.entity.Camping;
import com.campass.demo.entity.Reservation;
import com.campass.demo.exception.BoardNotFoundException;
import com.campass.demo.exception.JobFailException;

@Service
public class CampingService {

    @Autowired
    private CampingDao campingDao;
    @Autowired
    private CZoneDao cZoneDao;
    @Autowired
    private ReservationDao reservationDao;;

    @Value("6")
    private Integer pagesize;
    @Value("5")
    private Integer blocksize;

    // private PageDto PageDto;

    HttpSession session;
    HttpServletRequest request;

    @Value("c:/upload")
    private String imageFolder;

    @Value("http://localhost:8087/com/images/")
    private String imagePath;

    public PageDto campingAll(Integer pageno, String loginId) {
        // 1-> 1,10. 2->11,20, 3->21, 30
        Integer startRownum = (pageno - 1) * pagesize + 1;
        Integer endRownum = startRownum + pagesize - 1;
        Map<String, Object> map = new HashMap<>();
        map.put("startRownum", startRownum);
        map.put("endRownum", endRownum);
        map.put("loginId", loginId);

        List<CampingDto.CListDto> list = campingDao.findAll(map);

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

    // upload photo 🔽🔽🔽🔽🔽 path name
    public Camping campingWrite(CampingDto.CWriteDto dto, String loginId) {
        Camping camping = dto.toCEntity().cAdd(loginId);
        MultipartFile photo = dto.getCPhoto();
        if (photo != null & photo.isEmpty() == false) {
            // 업로드한 MultipartFile을 c:/upload에 이동하자
            File file = new File(imageFolder, photo.getOriginalFilename());
            try {
                photo.transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            camping.setCPhoto(imagePath + photo.getOriginalFilename());
        }
        System.out.println(camping);

        campingDao.save(camping);
        return camping;
    }

    // 캠핑리드에 캠핑데이터와 czList를 넣어줌
    public ModelAndView campingRead(Integer cCode, String loginId) {
        ModelAndView mav = new ModelAndView();
        Camping read = campingDao.findById(cCode);
        List<CZListDto> list = cZoneDao.czFindAll(cCode);
        if (read.getC_username().equals(loginId) == false)
            return null;
        mav.addObject("read", read);
        mav.addObject("czlist", list);
        mav.setViewName("camping/read");
        return mav;
    }

    public Camping campingUpdate(CampingDto.CUpdateDto dto, String loginId) {
        String writer = campingDao.findWriterById(dto.getCCode()).orElseThrow(() -> new BoardNotFoundException());
        if (writer.equals(loginId) == false)
            throw new JobFailException("변경 권한이 없습니다");
        campingDao.update(dto.toCEntity());
        Camping camping = dto.toCEntity().cAdd(loginId);
        return camping;
    }

    // 캠핑장 삭제
    public Integer campingDelete(Integer cCode, String loginId) {

        String writer = campingDao.findWriterById(cCode).orElseThrow(() -> new BoardNotFoundException());
        if (writer.equals(loginId) == false)
            throw new JobFailException("삭제 권한이 없습니다");

        return campingDao.deleteById(cCode);

    }

    // 나의예약내역 (판매회원) myReviewDto 안에 한번더 체크하기 엄청 난게 들었다. 난이도 최상! 상관서브쿼리 챙기기 + th:if문
    // 챙기기 + dto를 따로만들어서 데이터항목(컬럼)을 하나 추가해서 화면에 t or f 역할로 뿌려준다 0이면 나오고 1이면 나오지
    // 못하게하는 항목
    public List<MyCampingDto> mymyCamping(Reservation reservation, String loginId) {

        return reservationDao.RRmyCamping(loginId);
    }

    public Integer myCampingReserve(Integer rCode, String loginId) {
        String writer = reservationDao.RRfindHostById(rCode);
        if (writer.equals(loginId) == false)
            throw new JobFailException("삭제 권한이 없습니다");

        return reservationDao.updateRStatus(rCode);

    }

    // 환불
    public Integer yourReserveDelete(Integer rCode, String loginId) {
        String Seller = reservationDao.RRfindHostById(rCode);
        if (Seller.equals(loginId) == false)
            throw new JobFailException("삭제 권한이 없습니다");
        return reservationDao.cancelReservation(rCode);

    }

    // ========================================================================
    public CZone cZoneWrite(CZoneDto.CZWriteDto dto, String loginId, Integer cCode) {

        CZone cZone = dto.toCZentity().czAdd(loginId, cCode);
        MultipartFile photo = dto.getCzPhoto();
        if (photo != null & photo.isEmpty() == false) {
            // 업로드한 MultipartFile을 c:/upload에 이동하자
            File file = new File(imageFolder, photo.getOriginalFilename());
            try {
                photo.transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            cZone.setCzPhoto(imagePath + photo.getOriginalFilename());
        }
        System.out.println(cZone);

        cZoneDao.cZoneSave(cZone);
        return cZone;
    }

    public Integer czoneUpdate(CZoneDto.CZUpdateDto dto, String loginId, Integer cCode, Integer czCode) {

        String writer = cZoneDao.czFindWriterById(dto.getCzCode()).orElseThrow(() -> new BoardNotFoundException());
        if (writer.equals(loginId) == false)
            throw new JobFailException("변경 권한이 없습니다");

        return cZoneDao.cZoneUpdate(dto);
    }

    public Integer czDelete(Integer czCode, String loginId) {
        String writer = cZoneDao.czFindWriterById(czCode).orElseThrow(() -> new BoardNotFoundException());
        if (writer.equals(loginId) == false)
            throw new JobFailException("삭제 권한이 없습니다");
        return cZoneDao.cZoneDel(czCode);

    }

}
