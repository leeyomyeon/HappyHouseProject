package com.ssafy.happyhouse.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CItoDBInsert {
    public static void main(String[] args) throws Exception {
        String path = System.getProperty("user.dir");
        String projectPath = File.separator + "happyhouse" +
                             File.separator + "src" +
                             File.separator + "main" +
                             File.separator + "java" +
                             File.separator + "com" +
                             File.separator + "ssafy" +
                             File.separator + "happyhouse" +
                             File.separator + "util" +
                             File.separator + "datas";

        File dir = new File(path + projectPath);
        File[] fileList = dir.listFiles();
        BufferedReader br;


        Connection conn = DBUtil.getUtil().getConnection();
        PreparedStatement pstmt = null;

        for(File f : fileList) {
            br = new BufferedReader(new FileReader(f));

            String line;
            int row = 0;
            while((line = br.readLine()) != null) {
                if(row == 0) {
                    row += 1;
                    continue;
                }
                String[] array = line.split("\\|");
                boolean isCaffee = array[1].equals("스타벅스") || array[1].equals("할리스커피") || array[1].equals("투썸플레이스") || array[1].equals("다이소");
                boolean isMart = array[1].equals("이마트") || array[1].equals("롯데마트") || array[1].equals("홈플러스");
                boolean isCategory = array[8].equals("종합소매") || array[8].equals("할인점");
                if(isCaffee || (isMart && isCategory)) {
                    String sql = "INSERT INTO Commercial_Information VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(sql);

                    pstmt.setString(1, array[1]);
                    pstmt.setString(2, array[4]);
                    pstmt.setString(3, array[6]);
                    pstmt.setString(4, array[8]);
                    pstmt.setString(5, array[12]);
                    pstmt.setString(6, array[13]);
                    pstmt.setString(7, array[14]);
                    pstmt.setString(8, array[17]);
                    pstmt.setString(9, array[18]);
                    pstmt.setString(10, array[24]);
                    pstmt.setString(11, array[37]);
                    pstmt.setString(12, array[38]);

                    pstmt.executeUpdate();
                    System.out.println(f + " : " + row + "번 째 작업 중");
                    row++;
                }
            }
        }
        System.out.println("작업 끝");
        DBUtil.getUtil().close(conn, pstmt);
    }
}