package poly.persistance.mongo.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import config.Mapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import poly.dto.MelonDTO;
import poly.persistance.mongo.IMelonMapper;
import poly.util.CmmUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mapper("MelonMapper")
public class MelonMapper implements IMelonMapper {
    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private MongoTemplate mongodb;

    @Override
    public boolean createCollection(String colNm) throws Exception {

        log.info(this.getClass().getName() + ".createCollection Start!");

        boolean res = false;

        // 기존에 등록된 컬렉션 이름이 존재하는지 체크하고, 존재하면 기존 컬렉션 삭제함
        if (mongodb.collectionExists(colNm)) {
            mongodb.dropCollection(colNm);
        }

        // 컬렉션 생성 및 인덱스 생성, MongoDB에서 데이터 가져오는 방식에 맞게 인덱스는 반드시 생성하자!
        // 데이터 양이 많지 않으면 문제되지 않으나, 최소 10만건 이상 데이터 저장시 속도가 약 10배 이상 발생함
        mongodb.createCollection(colNm).createIndex(new BasicDBObject("collection_time", 1).append("rank", 1), "rankIdx");

        res = true;

        log.info(this.getClass().getName() + ".createCollection End!");

        return res;
    }

    @Override
    public int insertRank(List<MelonDTO> pList, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".insertRank Start!");

        int res = 0;

        if (pList == null) {
            pList = new ArrayList<MelonDTO>();
        }

        Iterator<MelonDTO> it = pList.iterator();

        while (it.hasNext()) {
            MelonDTO pDTO = (MelonDTO) it.next();

            if (pDTO == null) {
                pDTO = new MelonDTO();
            }

            mongodb.insert(pDTO, colNm);

        }

        res = 1;

        log.info(this.getClass().getName() + ".insertRank End!");

        return res;
    }

    @Override
    public List<MelonDTO> getRank(String colNm) throws Exception {

        log.info(this.getClass().getName() + "getRank Start!");

        //데이터를 가져올 컬렉션 선택
        DBCollection rCol = mongodb.getCollection(colNm);

        //컬렉션으로부터 전체 데이터 가져오기
        Iterator<DBObject> cursor = rCol.find();

        // 컬렉션으로부터 전체 데이터 가져온 것을 List 형태로 저장하기 위한 변수 선언
        List<MelonDTO> rList = new ArrayList<MelonDTO>();

        //퀴즈별 정답을 일자별 저장하기

        MelonDTO rDTO = null;

        while (cursor.hasNext()) {
            rDTO = new MelonDTO();

            final DBObject current = cursor.next();

            String collect_time = CmmUtil.nvl((String) current.get("collect_time")); // 수집시간
            String rank = CmmUtil.nvl((String) current.get("rank")); // 순위
            String song = CmmUtil.nvl((String) current.get("song")); // 노래제목
            String singer = CmmUtil.nvl((String) current.get("singer")); // 가수
            String album = CmmUtil.nvl((String) current.get("album")); // 엘범

            rDTO.setCollect_time(collect_time);
            rDTO.setRank(rank);
            rDTO.setSong(song);
            rDTO.setSinger(singer);
            rDTO.setAlbum(album);

            rList.add(rDTO); // List에 데이터 저장

            rDTO = null;
        }

        log.info(this.getClass().getName() + ".getRank End!");

        return rList;
    }

}
