package com.pcp.myapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * author : huazi
 * time   : 2021/4/29
 * desc   :
 */
public class TextUtil {

    /**
     * 限制输入字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    @SuppressWarnings("ALL")
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字，以及部分符号
        //String regEx = "[^a-zA-Z\u4E00-\u9FA5]";
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5\\`\\¥\\￥\\~\\!\\@\\#\\$\\%\\^\\*\\(\\)\\-\\_\\=\\+\\;\\:\\'\\,\\<\\.\\>\\/\\?" +
                "\\！\\·\\…\\【\\「\\】\\」\\、\\；\\：\\“\\”\\‘\\’\\，\\✔\\✘\\★\\《\\。\\》\\／\\？\\&\\＆\\＃\\＠\\〖\\〗" +
                "\\‹\\›\\＜\\¿\\︽\\︾\\＞\\﹝\\﹞\\『\\』\\～\\〔\\〕\\{\\}\\[\\]\\|\\①\\②\\③\\④\\⑤\\⑥\\⑦\\⑧\\⑨\\⑩\\⑪\\⑫\\⑬" +
                "\\⑭\\⑮\\⑯\\⑰\\⑱\\⑲\\⑳\\Ⅰ\\Ⅱ\\Ⅲ\\Ⅳ\\Ⅴ\\Ⅵ\\Ⅶ\\Ⅷ\\Ⅸ\\Ⅹ\\Ⅺ\\Ⅻ\\ⅰ\\ⅱ\\ⅲ\\ⅳ\\ⅴ\\ⅵ\\ⅶ\\ⅷ\\ⅸ\\ⅹ" +
                "\\×\\÷\\≤\\≥\\≦\\≧\\≠\\≈\\±\\∑\\∏\\㎎\\㎏\\㎜\\㎝\\㎞\\′\\€\\฿\\￡\\㎡\\㏄\\㏕\\″\\£\\￠\\₠\\‰\\％\\＼\\\\\\｜]";

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
