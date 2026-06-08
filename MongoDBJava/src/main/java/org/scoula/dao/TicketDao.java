package org.scoula.dao;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.scoula.config.MongoConfig;
import org.scoula.model.Ticket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {

    // tickets collection을 Ticket 타입으로 사용
    private final MongoCollection<Ticket> collection;

    public TicketDao(MongoDatabase db) {
        this.collection = db.getCollection("tickets", Ticket.class)
            .withCodecRegistry(MongoConfig.createPojoCodecRegistry());
    }

    // 날짜가 오늘 또는 미래인지 확인
    public boolean isDateValid(String date) {
        try {
            LocalDate inputDate = LocalDate.parse(date);
            LocalDate today = LocalDate.now();

            return !inputDate.isBefore(today);
        } catch (Exception e) {
            return false;
        }
    }

    // 해당 좌석이 비어 있는지 확인
    public boolean isSeatAvailable(String route, String date, int seat) {
        Document query = new Document("route", route)
            .append("date", date)
            .append("seat", seat);

        Ticket found = collection.find(query).first();

        return found == null;
    }

    // 예매 등록
    public boolean reserve(Ticket ticket) {
        try {
            collection.insertOne(ticket);
            return true;
        } catch (MongoWriteException e) {
            // unique index 때문에 중복 예매가 발생하면 예외 처리
            return false;
        }
    }

    // 특정 회원의 예매 목록 조회
    public List<Ticket> findByMemberId(String memberId) {
        List<Ticket> ticketList = new ArrayList<>();

        Document query = new Document("memberId", memberId);

        for (Ticket ticket : collection.find(query)) {
            ticketList.add(ticket);
        }

        return ticketList;
    }
}