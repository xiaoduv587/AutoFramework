package com.xiaodu.utils;

import com.xiaodu.FrameWorkConfig;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 文件操作辅助类
 *
 * @author hy
 */
@Log4j2
public class FileHelper {
//    private static final Logger logger = new Logger(MethodHandles.lookup().lookupClass().getName());
    private static String os = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux() {
        return os.indexOf("linux") >= 0;
    }

    public static boolean isWindows() {
        return os.indexOf("windows") >= 0;
    }

//    /**
//     * 获取test-output路径下'PROJECT_NAME'下路径
//     * @return String
//     */
//    public static String getOutputDirectory(){
//        return  ITestContextHelper.getBeforeSuiteItestContext().getOutputDirectory();
//    }

    /**
     * 拼接jenkins服务器上的存放路径
     *
     * @param jenkinsJobName
     * @param jenkinsBuildNumber
     * @return
     */
    public static String getJenkinsJobFileName(String jenkinsJobName, String jenkinsBuildNumber) {
        String[] jName = jenkinsJobName.split("/");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < jName.length - 1; i++) {
            stringBuffer.append(jName[ i ] + "_");
        }
        stringBuffer.append("#" + jenkinsBuildNumber);
        return stringBuffer.toString();
    }

    /**
     * 获取根目录
     *
     * @return root dir
     */
    public static String getUsersProjectRootDirectory() {
        String envRootDir = System.getProperty("user.dir");
        Path rootDIr = Paths.get(".").normalize().toAbsolutePath();
        if (rootDIr.startsWith(envRootDir)) {
            return rootDIr.toString();
        } else {
            throw new RuntimeException("Root dir not found in user directory.");
        }
    }

    /**
     * 用在gradle运行时去掉module名
     * 获取根目录的Parent路径(根目录的上级目录)
     *
     * @return root dir
     */
    public static String getTestngReportsRootDir() {
        String envRootDir = System.getProperty("user.dir");
        Path rootDIr = Paths.get(".").normalize().toAbsolutePath();
        if (rootDIr.startsWith(envRootDir)) {
            String parentPath = new File(envRootDir).getAbsolutePath();
            String runType = FrameWorkConfig.instance().RUN_TYPE.getValue();
            if (runType.equalsIgnoreCase("gradle")) {//如果是gradle,需要去掉项目名
                parentPath = parentPath.substring(0, parentPath.lastIndexOf(File.separator));
            }
            parentPath = parentPath + File.separator + "runShellRetry-output";
            return parentPath;
        } else {
            throw new RuntimeException("Root dir not found in user directory.");
        }
    }



    /**
     * Through InputStream access to Resources the file content
     *
     * @param fileName
     * @return
     */
    public static InputStream getResourcesFile(String fileName) {
        InputStream fileInputStream = FileHelper.class.getClassLoader().getResourceAsStream(fileName);
        return fileInputStream;
    }

    /**
     * 项目build文件夹下resources下的文件路径
     *
     * @param fileName 文件名称
     * @return file path
     */
    public static String getResourcesFilePath(String fileName) {
        if (StringHelper.isEmpty(fileName)) {
            throw new RuntimeException("check fileName ,fileName is null , this is a necessary parameter");
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(fileName);

        if (url == null) {
            return null;
        }
        String filePath = url.getPath();
        if (isWindows() && filePath.startsWith("/")) {
            filePath = filePath.substring(1).trim().replaceAll("/", "\\\\");
        }
        return filePath;
    }


    /**
     * 指定入口路径下resources下的文件路径
     *
     * @param fileName       文件名称
     * @param entranceFolder 入口文件夹 例: main,test文件夹
     * @return file path
     */
    public static String getResourcesFilePath(String fileName, String entranceFolder) {
        if (StringHelper.isEmpty(entranceFolder)) {
            throw new RuntimeException("check entrance folder , path is null , this is a necessary parameter");
        }
//        String projectName = ITestContextHelper.getProjectName().toLowerCase();
        String projectPath = getUsersProjectRootDirectory();
      String projectName="";


        String separator = "/|\\\\";
        List<String> list = Arrays.asList(projectPath.split(separator));
        if (list != null && list.size() > 0 && !list.get(list.size() - 1).equalsIgnoreCase(projectName)) {
            projectPath = projectPath + File.separator + projectName;
        }
        String filePath = File.separator + "src" + File.separator + entranceFolder + File.separator + "resources" + File.separator;
        StringBuilder builder = new StringBuilder();
        builder.append(projectPath).append(filePath).append(fileName);
        File file = new File(builder.toString());
        return file.getPath();


    }

    /**
     * 创建临时文件夹
     *
     * @return 临时文件夹路径
     */
    public static String createtempFolder(String projectName) {
        String tempFolderPath = getUsersProjectRootDirectory() + File.separator + projectName + File.separator + "temp";
        log.info("tempFolderPath= " + tempFolderPath);
        File tempFolde = new File(tempFolderPath);
        if (!tempFolde.exists()) {
            tempFolde.mkdirs();
        }
        return tempFolderPath;
    }

    /**
     * 创建文件
     *
     * @param fileName
     * @return boolean
     */
    @SuppressWarnings("unused")
    public static boolean createFile(File fileName) {
        boolean flag = false;
        try {
            if (!fileName.exists()) {
                fileName.createNewFile();
                flag = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 删除文件夹并删除里面所有文件
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        File file = new File(folderPath);
        try {
            delAllFile(folderPath);

            file.delete();
            log.info("del folder success , folder path= " + file.getAbsolutePath());
        } catch (Exception e) {
            log.error(e.getMessage() + " del folder fail ,folder path= " + file.getAbsolutePath());
            e.printStackTrace();
        }

    }

    /**
     * 删除文件夹内所有文件
     *
     * @param folderPath
     */
    public static void delAllFile(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            log.error("check the folder exists");
            return;
        }
        if (!file.isDirectory()) {
            log.error("check the folder is directory");
            return;
        }
        String[] fileList = file.list();
        File temp = null;
        for (int iterator = 0; iterator < fileList.length; iterator++) {
            if (folderPath.endsWith(File.separator)) {
                temp = new File(folderPath + fileList[ iterator ]);
            } else {
                temp = new File(folderPath + File.separator + fileList[ iterator ]);
            }
            if (temp.isFile()) {
                temp.delete();
//                fileHelperLog.info("[Delete]: " + temp.getAbsolutePath());
            }
            if (temp.isDirectory()) {
                delAllFile(temp.getAbsolutePath());
                delFolder(temp.getAbsolutePath());
            }
        }
    }


    /**
     * 写 propperty
     *
     * @param filepath filepath
     * @param key      要写入key值
     * @param value    要写入value值
     * @throws Exception
     * @author zhangyuehua
     */

    public static void writeProperty(String filepath, String key, String value) throws Exception {
        File file = new File(filepath);
        if (!file.getParentFile().exists()) {
            log.info("property file mkdirs = " + file.getAbsolutePath());
            file.mkdirs();
        }
        if (!file.isFile()) {

            if (file.createNewFile()) {
                log.info("property file create success = " + file.getAbsolutePath());
            } else {
                throw new Exception("property file create fail  " + file.getAbsolutePath());
            }
        }
        filepath = file.getAbsolutePath();
        Properties prop = new Properties();
        FileInputStream inputStream = new FileInputStream(filepath);
        prop.load(inputStream);
        FileOutputStream outStream = new FileOutputStream(filepath);
        prop.setProperty(key, value);
        prop.store(outStream, "Update Value Success. The last written--> " + key + "=" + value);
        if (outStream != null) {
            outStream.close();
        }
    }

    /**
     * 读propperty返回单个值
     *
     * @param filepath filepath
     * @param key      key
     * @return String value
     * @throws IOException
     * @author zhangyuehua
     */
    public static String readProperty(String filepath, String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream inputStream = new FileInputStream(filepath);
        prop.load(inputStream);
        String value = prop.getProperty(key);
        log.info("Read Property Key[" + key + "]=[" + value + "] From[" + filepath + "]");
        if (inputStream != null) {
            inputStream.close();
        }
        return value;
    }

    /**
     * 读propperty返回map
     *
     * @param fileName
     * @return Map
     * @throws IOException
     */
    public static Map<String, String> readPropertys(String fileName) throws IOException {
        Properties prop = new Properties();
        InputStream inputStream = FileHelper.getResourcesFile(fileName);
        prop.load(inputStream);
        Enumeration<?> names = prop.propertyNames();
        Map<String, String> arguments = new HashMap<String, String>();
        while (names.hasMoreElements()) {
            String key = (String) names.nextElement();
            String value = prop.getProperty(key);

            arguments.put(key, value);
        }
        if (inputStream != null) {
            inputStream.close();
        }
        return arguments;
    }

    /**
     * 利用url下载文件
     *
     * @param url
     * @param dir
     * @param fileName
     * @throws IOException
     */
    public static void downloadFileByURL(String url, String dir, String fileName) throws IOException {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(3 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            byte[] buffer = new byte[ 1024 ];
            int len = 0;
            bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
                log.info("read stream len:" + len);
            }
            bos.close();
            File saveDir = new File(dir);
            File file = new File(saveDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            fos.write(bos.toByteArray());
            if (fos != null) {
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {

            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
