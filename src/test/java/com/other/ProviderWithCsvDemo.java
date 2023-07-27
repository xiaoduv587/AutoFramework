package com.other;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProviderWithCsvDemo {
    // 定义一个数据提供器方法
    @DataProvider(name = "userData")
    public Object[][] provideData() {
        String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "group_token.csv"; // 指定文件路径

        // 读取 CSV 文件并转换为对象数组
        List<UserBean> data = readCSV(filePath);

        // 转换对象数组为数据提供器所需的格式
        Object[][] dataArray = new Object[ data.size() ][ 1 ];
        for (int i = 0; i < data.size(); i++) {
            dataArray[ i ][ 0 ] = data.get(i);
        }

        return dataArray;
    }

    // 使用数据提供器进行测试
    @Test(dataProvider = "userData")
    public void myTest(UserBean userBean) {
        // 执行测试逻辑，使用 myBean 对象进行测试
        System.out.println("myBean = " + userBean.userName);
        // ...
    }

    // 读取 CSV 文件并转换为对象列表
    private List<UserBean> readCSV(String filePath) {
        List<UserBean> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // 假设 CSV 文件的格式为：name,phoneNumber,token,id
                String userOwner = values[ 0 ].trim();
                String phoneNumber = values[ 1 ].trim();
                String userName = values[ 2 ].trim();
                String uid = values[ 3 ].trim();
                String token = values[ 4 ].trim();

                // 创建 MyBean 对象并填充属性
                UserBean myBean = new UserBean(userOwner, phoneNumber, userName, uid, token);

                dataList.add(myBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

}
