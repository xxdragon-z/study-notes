package collection;

import sun.misc.Regexp;

import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by XXDragon on 2022/4/12 16:22
 */
public class TestByteArray {
    private static String path = "E:\\xxl\\test.txt";

    public static void main2(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(new File(path));
        byte[] array = new byte[fis.available()];
        byte[] temp = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (-1 != (fis.read(temp))) {
            baos.write(temp);
        }
        ByteArrayInputStream bios = new ByteArrayInputStream(baos.toByteArray());
        BufferedInputStream bis = new BufferedInputStream(bios);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bis));
        StringBuilder sb = new StringBuilder();
        String temps = null;
        while (null != (temps = bufferedReader.readLine())) {
            System.out.println(baos);
        }
    }

    public static void main(String[] args) {
        for (String s : split("1,2,3,,3", ',')) {
            System.out.println(s);
        }

    }

    public static String[] split(String str, char split) {
        if (null == str) return new String[]{};
        LinkedList<String> res = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        char c;
        int lastSize = chars.length - 1;
        for (int i = 0; i < chars.length; i++) {
            c = chars[i];
            if (c == split || i == lastSize) {
                if (i == lastSize) {
                    res.add(c == split ? "" : String.valueOf(c));
                } else {
                    res.add(sb.length() == 0 ? "" : sb.toString());
                }
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        return res.toArray(new String[]{});
    }
}
