package org.scoula.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Member {

    // MongoDB의 _id 필드로 저장됨
    @BsonId
    private String id;

    // 회원 비밀번호
    private String password;

    // 회원 이름
    private String name;
}