package poly.persistance.mongo;

import poly.dto.MelonDTO;

import java.util.List;

public interface IMelonMapper {

    /**
     * MongoDB 컬렉션 생성
     *
     * @Oaram colNM 생성하는 컬렉션 이름
     */
    public boolean createCollection(String colNm) throws Exception;

    /**
     * MongoDB 데이터 저장하기
     *
     * @param pDTO 저장될 정보
     */
    public int insertRank(List<MelonDTO> pList, String colNm) throws Exception;

    /**
     * MongoDB 멜론 데이터 가져오기
     *
     * @parma colNm 가져올 컬렉션 이름
     */

    public List<MelonDTO> getRank(String colNm) throws Exception;
}
