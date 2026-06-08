package org.scoula.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoConfig {

    // MongoDB 접속 주소
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";

    // MongoClient 생성
    public static MongoClient createMongoClient() {
        return MongoClients.create(CONNECTION_STRING);
    }

    // Java 객체를 MongoDB Document로 자동 변환하기 위한 설정
    public static CodecRegistry createPojoCodecRegistry() {
        return fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(
                PojoCodecProvider.builder()
                    .automatic(true)
                    .build()
            )
        );
    }
}