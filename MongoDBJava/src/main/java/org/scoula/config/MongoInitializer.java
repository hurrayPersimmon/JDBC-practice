package org.scoula.config;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

public class MongoInitializer {

    // MongoDB collection과 index를 미리 준비하는 메서드
    public static void init(MongoDatabase db) {
        createCollectionIfNotExists(db, "members");
        createCollectionIfNotExists(db, "tickets");

        createTicketUniqueIndex(db);

        System.out.println("MongoDB 초기 설정 완료");
    }

    // collection이 없으면 생성

    private static void createCollectionIfNotExists(MongoDatabase db, String collectionName) {
        boolean exists = false;

        for (String name : db.listCollectionNames()) {
            if (name.equals(collectionName)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            db.createCollection(collectionName);
            System.out.println(collectionName + " collection 생성 완료");
        }
    }

    // 같은 노선, 같은 날짜, 같은 좌석은 중복 예매되지 않도록 unique index 생성
    private static void createTicketUniqueIndex(MongoDatabase db) {
        db.getCollection("tickets").createIndex(
            new Document("route", 1)
                .append("date", 1)
                .append("seat", 1),
            new IndexOptions().unique(true)
        );
    }
}