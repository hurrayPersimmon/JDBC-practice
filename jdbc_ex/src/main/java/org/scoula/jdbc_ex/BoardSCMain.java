package org.scoula.jdbc_ex;

import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.dao.BoardDao;
import org.scoula.jdbc_ex.dao.BoardDaoImpl;
import org.scoula.jdbc_ex.domain.BoardVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BoardSCMain {

    private static final Scanner sc = new Scanner(System.in);
    private static final BoardDao dao = new BoardDaoImpl();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            printMenu();

            System.out.print("메뉴 선택: ");
            int menu = inputInt();

            try {
                switch (menu) {
                    case 1:
                        createBoard();
                        break;

                    case 2:
                        printBoardList();
                        break;

                    case 3:
                        printBoardDetail();
                        break;

                    case 4:
                        updateBoard();
                        break;

                    case 5:
                        deleteBoard();
                        break;

                    case 0:
                        running = false;
                        System.out.println("프로그램을 종료합니다.");
                        break;

                    default:
                        System.out.println("잘못된 메뉴입니다. 다시 선택하세요.");
                }

            } catch (SQLException e) {
                System.out.println("DB 작업 중 오류가 발생했습니다.");
                e.printStackTrace();
            }
        }

        sc.close();
        JDBCUtil.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========== 게시판 관리 ==========");
        System.out.println("1. 게시글 등록");
        System.out.println("2. 게시글 전체 조회");
        System.out.println("3. 게시글 1건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("0. 종료");
        System.out.println("================================");
    }

    private static void createBoard() throws SQLException {
        System.out.println();
        System.out.println("[게시글 등록]");

        System.out.print("제목: ");
        String title = sc.nextLine();

        System.out.print("내용: ");
        String content = sc.nextLine();

        System.out.print("작성자: ");
        String writer = sc.nextLine();

        BoardVO board = new BoardVO();
        board.setTitle(title);
        board.setContent(content);
        board.setWriter(writer);

        int count = dao.create(board);

        if (count == 1) {
            System.out.println("게시글이 등록되었습니다.");
        } else {
            System.out.println("게시글 등록에 실패했습니다.");
        }
    }

    private static void printBoardList() throws SQLException {
        System.out.println();
        System.out.println("[게시글 전체 조회]");

        List<BoardVO> boardList = dao.getList();

        if (boardList.isEmpty()) {
            System.out.println("등록된 게시글이 없습니다.");
            return;
        }

        for (BoardVO board : boardList) {
            System.out.println("--------------------------------");
            System.out.println("번호: " + board.getNo());
            System.out.println("제목: " + board.getTitle());
            System.out.println("작성자: " + board.getWriter());
            System.out.println("작성일: " + board.getRegDate());
            System.out.println("수정일: " + board.getUpdateDate());
        }

        System.out.println("--------------------------------");
    }

    private static void printBoardDetail() throws SQLException {
        System.out.println();
        System.out.println("[게시글 1건 조회]");

        System.out.print("조회할 게시글 번호: ");
        int no = inputInt();

        Optional<BoardVO> optionalBoard = dao.get(no);

        if (optionalBoard.isPresent()) {
            BoardVO board = optionalBoard.get();

            System.out.println("--------------------------------");
            System.out.println("번호: " + board.getNo());
            System.out.println("제목: " + board.getTitle());
            System.out.println("내용: " + board.getContent());
            System.out.println("작성자: " + board.getWriter());
            System.out.println("작성일: " + board.getRegDate());
            System.out.println("수정일: " + board.getUpdateDate());
            System.out.println("--------------------------------");
        } else {
            System.out.println("해당 번호의 게시글이 없습니다.");
        }
    }

    private static void updateBoard() throws SQLException {
        System.out.println();
        System.out.println("[게시글 수정]");

        System.out.print("수정할 게시글 번호: ");
        int no = inputInt();

        Optional<BoardVO> optionalBoard = dao.get(no);

        if (optionalBoard.isEmpty()) {
            System.out.println("해당 번호의 게시글이 없습니다.");
            return;
        }

        BoardVO board = optionalBoard.get();

        System.out.println("현재 제목: " + board.getTitle());
        System.out.print("새 제목: ");
        String title = sc.nextLine();

        System.out.println("현재 내용: " + board.getContent());
        System.out.print("새 내용: ");
        String content = sc.nextLine();

        System.out.println("현재 작성자: " + board.getWriter());
        System.out.print("새 작성자: ");
        String writer = sc.nextLine();

        board.setTitle(title);
        board.setContent(content);
        board.setWriter(writer);

        int count = dao.update(board);

        if (count == 1) {
            System.out.println("게시글이 수정되었습니다.");
        } else {
            System.out.println("게시글 수정에 실패했습니다.");
        }
    }

    private static void deleteBoard() throws SQLException {
        System.out.println();
        System.out.println("[게시글 삭제]");

        System.out.print("삭제할 게시글 번호: ");
        int no = inputInt();

        Optional<BoardVO> optionalBoard = dao.get(no);

        if (optionalBoard.isEmpty()) {
            System.out.println("해당 번호의 게시글이 없습니다.");
            return;
        }

        BoardVO board = optionalBoard.get();

        System.out.println("삭제 대상 게시글");
        System.out.println("번호: " + board.getNo());
        System.out.println("제목: " + board.getTitle());
        System.out.println("작성자: " + board.getWriter());

        System.out.print("정말 삭제하시겠습니까? y/n: ");
        String answer = sc.nextLine();

        if (!answer.equalsIgnoreCase("y")) {
            System.out.println("삭제를 취소했습니다.");
            return;
        }

        int count = dao.delete(no);

        if (count == 1) {
            System.out.println("게시글이 삭제되었습니다.");
        } else {
            System.out.println("게시글 삭제에 실패했습니다.");
        }
    }

    private static int inputInt() {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력하세요: ");
            }
        }
    }
}