package com.example.haha.algorithm;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * 编码算法包括:
 * URL编码:URL编码是对字符进行编码，表示成%xx的形式
 * Base64编码:对二进制数据进行编码，表示成文本格式
 */
public class CodeAlgorithm {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) throws UnsupportedEncodingException {
        //URL编码
        String encoded = URLEncoder.encode("中文！", String.valueOf(StandardCharsets.UTF_8));
        System.out.println(encoded);

        String decoded = URLDecoder.decode("%E4%B8%AD%E6%96%87%21",String.valueOf(StandardCharsets.UTF_8));
        System.out.println(decoded);

        //Base64编码
        byte[] input = new byte[]{(byte)0xe4,(byte)0xb8,(byte)0xad};
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String b64encoded = Base64.getEncoder().encodeToString(input);
            System.out.println(b64encoded);

            byte[] output = Base64.getDecoder().decode("5Lit");
            System.out.println(Arrays.toString(output));
        }

    }
}
