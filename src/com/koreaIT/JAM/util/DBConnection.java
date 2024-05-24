//package com.koreaIT.JAM.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//	private static Connection dbConn;
//	 
//    // 메소드 정의
//    public static Connection getConnection() throws ClassNotFoundException, SQLException {
//        // 한 번 연결된 객체를 계속 사용
//        // 즉, 연결되지 않은 경우에만 연결을 시도하겠다는 의미
//        // → 싱글톤(디자인 패턴)
//        if (dbConn == null) {
//            String URL ="jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
//            //-- 『localhost』 는 오라클 서버의 ip 주소를 기재하는 부분(로컬아니면 ip번호 입력)
//            //	 『1521』 은 오라클 리스너 Port Number(다른 포트번호 할 수 도 있다)
//            //	  『xe』 는 오라클 SID(Express Edition 의 SID는 xe)
//            String USER = "root";
//            //-- 오라클 사용자 계정 이름
//            String PASSWORD = "";
//            //-- 오라클 사용자 계정 암호
// 
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            //-- OrcleDriver 클래스에 대한 객체 생성
//            dbConn = DriverManager.getConnection(URL, USER, PASSWORD);
//            //-- 오라클 서버 실제 연결
//            //	 인자값(매개변수)은 오라클주소, 계정명, 패스워드
// 
//        }
//        return dbConn;
//        //-- 구성된 연결 객체 반환
//    }
// 
//    // getConnection() 메소드의 오버로딩
//    public static Connection getConnection(String url, String user, String pwd) throws ClassNotFoundException, SQLException {
//        if (dbConn == null) {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            dbConn = DriverManager.getConnection(url, user, pwd);
//        }
// 
//        return dbConn;
//    }
// 
//    // 메소드 정의 → 연결 종료
//    public static void close() throws SQLException {
//        // dbConn 변수(멤버 변수)는
//        // Database 가 연결된 상태일 경우 Connection을 갖는다.
//        // 연결되지 않은 상태라면 null 인 상태
//        if (dbConn != null) {
//            // 연결 객체(dbConn)의 isClosed() 메소드를 통해 연결 상태 확인
//            //-- 연결이 닫혀있는 경우 true 반환
//            //	 연결이 닫혀있지 않은 경우 false 반환
//            if (!dbConn.isClosed()) {
//                dbConn.close();
//                //-- 연결 객체의 close() 메소드 호출을 통해 연결 종료~!!!
//            }
//        }
// 
//        dbConn = null;
//        //-- 연결 객체 초기화
//    }
//}
