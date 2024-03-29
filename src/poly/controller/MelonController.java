package poly.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.MelonDTO;
import poly.service.IMelonService;

/*
 * Controller 선언해야만 Spring 프레임워크에서 Controller인지 인식 가능
 * 자바 서블릿 역할 수행
 * */
@Controller
public class MelonController {
    private Logger log = Logger.getLogger(this.getClass());

    /*
     * 비즈니스 로직(중요 로직을 수행하기 위해 사용되는 서비스를 메모리에 적재(싱글톤패턴 적용됨)
     */
    @Resource(name = "MelonService")
    private IMelonService melonService;

    /**
     * 멜론 Top100 수집하기
     */
    @RequestMapping(value = "melon/collectMelonRank")
    @ResponseBody
    public String collectMelonRank(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info(this.getClass().getName() + ".collectMelonRank Start!");

        melonService.collectMelonRank();

        log.info(this.getClass().getName() + ".collectMelonRank End!");

        return "success";
    }

    /**
     * 멜론 데이터 가져오는 일반 화면
     */
    @RequestMapping(value = "melon/melonTop100")
    public String melonTop100(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info(this.getClass().getName() + ".melonTop100 Start!");

        log.info(this.getClass().getName() + ".melonTop100 End!");

        return "/melon/melonTop100";
    }

    /**
     * 멜론 데이터 가져오기
     */
    @RequestMapping(value = "melon/getRank")
    @ResponseBody
    public List<MelonDTO> getRank(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info(this.getClass().getName() + ".getRank Start!");

        List<MelonDTO> rList = melonService.getRank();

        if (rList == null) {
            rList = new ArrayList<MelonDTO>();
        }

        log.info(this.getClass().getName() + ".getRank End!");

        return rList;
    }



}
