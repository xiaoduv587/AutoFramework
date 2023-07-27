package com.other;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WriteMultipleRecordsToCSV {
   public static void main(String[] args) {
      String csvFilename = "output.csv";
      try {
         // 创建FileWriter对象
         FileWriter fw = new FileWriter(new File(csvFilename).getAbsoluteFile(), true);
         // 创建BufferedWriter对象
         BufferedWriter bw = new BufferedWriter(fw);
         // 创建多条数据
         List<String> record1 = Arrays.asList("John", "Doe", "26");
         List<String> record2 = Arrays.asList("Jane", "Smith", "32");
         List<String> record3 = Arrays.asList("Mike", "Jones", "45");


         for (int i = 0; i < 2; i++) {
            // 向CSV文件写入数据
            bw.write(String.join(",", record1));
            bw.newLine();

         }

//         // 向CSV文件写入数据
//         bw.write(String.join(",", record1));
//         bw.newLine();
//         bw.write(String.join(",", record2));
//         bw.newLine();
//         bw.write(String.join(",", record3));
//         bw.newLine();
         // 关闭BufferedWriter和FileWriter对象
         bw.close();
         fw.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}