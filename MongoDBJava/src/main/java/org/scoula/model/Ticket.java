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
public class Ticket {

    // MongoDB의 _id 필드로 저장됨
    @BsonId
    private String id;

    // 예매한 회원 ID
    private String memberId;

    // 노선 정보, 예: 서울-부산
    private String route;

    // 예매 날짜, 예: 2026-06-10
    private String date;

    // 좌석 번호
    private int seat;
}