package com.hld.stockserver.uitls;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.springframework.stereotype.Component;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * pinyin4j汉字转拼音工具类
 * @author zhiheng
 *
 */
@Component
public class ChangeToPinYin {

    //pinyin4j格式类
    private HanyuPinyinOutputFormat format = null;
    //拼音字符串数组
    private String[]pinyin;

    //通过构造方法进行初始化
    public ChangeToPinYin(){

        format = new HanyuPinyinOutputFormat();
        /*
         * 设置需要转换的拼音格式
         * 以天为例
         * HanyuPinyinToneType.WITHOUT_TONE 转换为tian
         * HanyuPinyinToneType.WITH_TONE_MARK 转换为tian1
         * HanyuPinyinVCharType.WITH_U_UNICODE 转换为tiān
         *
         */
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pinyin = null;
    }

    /**
     * 对单个字进行转换
     * @param pinYinStr 需转换的汉字字符串
     * @return 拼音字符串数组
     */
    public String getCharPinYin(char pinYinStr){

        try
        {
            //执行转换
            pinyin = PinyinHelper.toHanyuPinyinStringArray(pinYinStr, format);

        } catch (BadHanyuPinyinOutputFormatCombination e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //pinyin4j规则，当转换的符串不是汉字，就返回null
        if(pinyin == null){
            return null;
        }

        //多音字会返回一个多音字拼音的数组，pinyiin4j并不能有效判断该字的读音
        return pinyin[0];
    }

    /**
     * 对单个字进行转换
     * @param pinYinStr
     * @return
     */
    public String getStringPinYin(String pinYinStr){
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        //循环字符串
        for(int i = 0; i<pinYinStr.length(); i++)
        {

            tempStr = this.getCharPinYin(pinYinStr.charAt(i));
            if(tempStr == null)
            {
                //非汉字直接拼接
                sb.append(pinYinStr.charAt(i));
            }
            else
            {
                sb.append(tempStr);
            }
        }

        return sb.toString();

    }

    public String getPinYinHeadChar(String zn_str, int caseType) {
        if(zn_str != null && !zn_str.trim().equalsIgnoreCase("")) {
            char[] strChar = zn_str.toCharArray();
            // 汉语拼音格式输出类
            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
            // 输出设置，大小写，音标方式等
            if(1 == caseType) {
                hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            } else {
                hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            }
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            StringBuffer pyStringBuffer = new StringBuffer();
            for(int i=0; i<strChar.length; i++) {
                char c = strChar[i];
                char pyc = strChar[i];
                if(String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {//是中文或者a-z或者A-Z转换拼音
                    try {
                        String[] pyStirngArray = PinyinHelper.toHanyuPinyinStringArray(strChar[i], hanYuPinOutputFormat);
                        if(null != pyStirngArray && pyStirngArray[0]!=null) {
                            pyc = pyStirngArray[0].charAt(0);
                            pyStringBuffer.append(pyc);
                        }
                    } catch(BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                }
            }
            return pyStringBuffer.toString();
        }
        return null;
    }



    public static void main(String[] args){
        ChangeToPinYin py=new ChangeToPinYin();
        String str=py.getStringPinYin("你好啊");
        String str2=py.getPinYinHeadChar("你好啊",2);
        System.out.println(""+str);
        System.out.println(""+str2);
    }

}