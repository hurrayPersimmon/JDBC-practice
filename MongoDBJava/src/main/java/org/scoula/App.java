package org.scoula;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.scoula.config.MongoConfig;
import org.scoula.config.MongoInitializer;
import org.scoula.dao.MemberDao;
import org.scoula.dao.TicketDao;
import org.scoula.model.Member;
import org.scoula.model.Ticket;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class App {

    // 사용자 입력을 받기 위한 Scanner
    private static final Scanner sc = new Scanner(System.in);

    // 현재 로그인한 회원 정보
    private static Member loginUser = null;

    public static void main(String[] args) {

        // MongoDB 연결
        try (MongoClient mongoClient = MongoConfig.createMongoClient()) {

            // reservationdb 데이터베이스 사용
            MongoDatabase db = mongoClient.getDatabase("reservationdb");

            // collection과 index 초기 설정
            MongoInitializer.init(db);

            // DAO 객체 생성
            MemberDao memberDao = new MemberDao(db);
            TicketDao ticketDao = new TicketDao(db);

            // 프로그램 시작
            run(memberDao, ticketDao);
        }
    }

    // 전체 프로그램 흐름
    private static void run(MemberDao memberDao, TicketDao ticketDao) {
        while (true) {
            if (loginUser == null) {
                showGuestMenu(memberDao);
            } else {
                showMemberMenu(ticketDao);
            }
        }
    }

    // 로그인 전 메뉴
    private static void showGuestMenu(MemberDao memberDao) {
        System.out.println();
        System.out.println("===== 예매 시스템 =====");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("0. 종료");
        System.out.print("선택: ");

        String menu = sc.nextLine();

        switch (menu) {
            case "1" -> register(memberDao);
            case "2" -> login(memberDao);
            case "0" -> {
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
            }
            default -> System.out.println("잘못된 메뉴입니다.");
        }
    }

    // 로그인 후 메뉴
    private static void showMemberMenu(TicketDao ticketDao) {
        System.out.println();
        System.out.println("[" + loginUser.getName() + "님 로그인 중]");
        System.out.println("1. 예매");
        System.out.println("2. 예매현황");
        System.out.println("3. 로그아웃");
        System.out.print("선택: ");

        String menu = sc.nextLine();

        switch (menu) {
            case "1" -> reserve(ticketDao);
            case "2" -> showTickets(ticketDao);
            case "3" -> logout();
            default -> System.out.println("잘못된 메뉴입니다.");
        }
    }

    // 회원가입
    private static void register(MemberDao memberDao) {
        System.out.println();
        System.out.println("[회원가입]");

        System.out.print("ID: ");
        String id = sc.nextLine();

        System.out.print("PW: ");
        String password = sc.nextLine();

        System.out.print("이름: ");
        String name = sc.nextLine();

        Member member = Member.builder()
            .id(id)
            .password(password)
            .name(name)
            .build();

        boolean result = memberDao.register(member);

        if (result) {
            System.out.println("회원가입 성공!");
        } else {
            System.out.println("이미 존재하는 ID입니다.");
        }
    }

    // 로그인
    private static void login(MemberDao memberDao) {
        System.out.println();
        System.out.println("[로그인]");

        System.out.print("ID: ");
        String id = sc.nextLine();

        System.out.print("PW: ");
        String password = sc.nextLine();

        loginUser = memberDao.login(id, password);

        if (loginUser == null) {
            System.out.println("로그인 실패");
        } else {
            System.out.println("로그인 성공!");
        }
    }

    // 예매
    private static void reserve(TicketDao ticketDao) {
        System.out.println();
        System.out.println("[예매]");

        System.out.print("노선 예: 서울-부산: ");
        String route = sc.nextLine();

        System.out.print("날짜 예: 2026-06-10: ");
        String date = sc.nextLine();

        System.out.print("좌석번호: ");

        int seat;

        try {
            seat = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("좌석번호는 숫자로 입력해야 합니다.");
            return;
        }

        if (seat < 1 || seat > 45) {
            System.out.println("좌석번호는 1번부터 45번까지 입력 가능합니다.");
            return;
        }

        if (!ticketDao.isDateValid(date)) {
            System.out.println("이미 지난 날짜이거나 날짜 형식이 올바르지 않습니다.");
            return;
        }

        if (!ticketDao.isSeatAvailable(route, date, seat)) {
            System.out.println("해당 좌석은 이미 예매되었습니다.");
            return;
        }

        Ticket ticket = Ticket.builder()
            .id(UUID.randomUUID().toString())
            .memberId(loginUser.getId())
            .route(route)
            .date(date)
            .seat(seat)
            .build();

        boolean result = ticketDao.reserve(ticket);

        if (result) {
            System.out.println("예매 완료!");
        } else {
            System.out.println("예매 실패: 해당 좌석은 이미 예매되었습니다.");
        }
    }

    // 예매현황 조회
    private static void showTickets(TicketDao ticketDao) {
        System.out.println();
        System.out.println("[예매현황]");

        List<Ticket> ticketList = ticketDao.findByMemberId(loginUser.getId());

        if (ticketList.isEmpty()) {
            System.out.println("예매 기록이 없습니다.");
            return;
        }

        for (Ticket ticket : ticketList) {
            System.out.println("-------------------------");
            System.out.println("예매번호: " + ticket.getId());
            System.out.println("회원ID: " + ticket.getMemberId());
            System.out.println("노선: " + ticket.getRoute());
            System.out.println("날짜: " + ticket.getDate());
            System.out.println("좌석: " + ticket.getSeat());
        }

        System.out.println("-------------------------");
    }

    // 로그아웃
    private static void logout() {
        loginUser = null;
        System.out.println("로그아웃되었습니다.");
    }
}