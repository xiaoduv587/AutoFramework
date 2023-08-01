//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mte.wdd.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.imageio.ImageIO;

public class MteSenseFileIO {
    public MteSenseFileIO() {
    }

    public static void append(String filename, String data) {
        append_or_overwrite(filename, data, true);
    }

    public static boolean copy(String src_filename, String dst_filename) {
        try {
            File src_file = new File(src_filename);
            FileChannel srcChannel = (new FileInputStream(src_file)).getChannel();
            new File(dst_filename);
            FileChannel dstChannel = (new FileOutputStream(dst_filename, false)).getChannel();
            dstChannel.transferFrom(srcChannel, 0L, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } catch (IOException var6) {
        } catch (Exception var7) {
        }

        return true;
    }

    public static boolean appendImage(String filename, BufferedImage image) {
        try {
            File file = new File(filename);
            String format = "JPEG";
            ImageIO.write(image, format, file);
        } catch (IOException var4) {
            System.out.println(var4.getMessage());
        }

        return true;
    }

    public static boolean exists(String filename) {
        return (new File(filename)).exists();
    }

    public static void overwrite(String filename, String data) {
        append_or_overwrite(filename, data, false);
    }

    public static String read(String filename) {
        String content = null;

        try {
            boolean copy = false;
            File file = new File(filename);
            FileInputStream input = new FileInputStream(file);
            byte[] result = new byte[(int)file.length()];
            int length = result.length;
            int offset = 0;
            long byte_read = 0L;

            while(byte_read != -1L && (long)offset < file.length()) {
                try {
                    byte_read = (long)input.read(result, offset, length - offset);
                    if (byte_read >= 0L) {
                        offset = (int)((long)offset + byte_read);
                    }
                } catch (IOException var13) {
                    var13.printStackTrace();
                }
            }

            content = new String(result);
            input.close();
            return content;
        } catch (Exception var14) {
            return var14.getMessage();
        }
    }

    private static void append_or_overwrite(String filename, String data, boolean append) {
        try {
            File file = new File(filename);
            FileChannel channel = (new FileOutputStream(file, append)).getChannel();
            FileLock lock = channel.lock();

            try {
                ByteBuffer bb = ByteBuffer.wrap(data.getBytes());
                channel.write(bb);
                channel.close();
            } finally {
                lock.release();
            }
        } catch (Exception var12) {
            System.out.println(var12.getMessage());
        }

    }
}
