package org.scoula.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.scoula.config.MongoConfig;
import org.scoula.model.Member;

public class MemberDao {

    // members collection을 Member 타입으로 사용
    private final MongoCollection<Member> collection;

    public MemberDao(MongoDatabase db) {
        this.collection = db.getCollection("members", Member.class)
            .withCodecRegistry(MongoConfig.createPojoCodecRegistry());
    }

    // 회원가입
    public boolean register(Member member) {
        // 같은 ID가 있는지 먼저 확인
        Member found = collection.find(new Document("_id", member.getId())).first();

        if (found != null) {
            return false;
        }

        // 회원 정보 저장
        collection.insertOne(member);
        return true;
    }

    // 로그인
    public Member login(String id, String password) {
        // ID로 회원 조회
        Member found = collection.find(new Document("_id", id)).first();

        // 회원이 없으면 로그인 실패
        if (found == null) {
            return null;
        }

        // 비밀번호가 일치하면 로그인 성공
        if (found.getPassword().equals(password)) {
            return found;
        }

        // 비밀번호가 다르면 로그인 실패
        return null;
    }
}