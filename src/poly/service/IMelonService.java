package poly.service;

import poly.dto.MelonDTO;

import java.util.List;

public interface IMelonService {

    /**
     * 멜론 Top100 순위 수집하기
     */
    public int collectMelonRank() throws Exception;

    /**
     * MongoDB 멜론 데이터 가져오기
     */
    public List<MelonDTO> getRank() throws Exception;
}
