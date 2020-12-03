package main;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class TableTennis {

    public static void main(String[] args) {

        try {
            reset();
            generateUrlsAndTitles();
            for(int i = 0;i<urls.size();i++){
                String url  = urls.get(i);
                String title = titles.get(i);
                System.out.println();
                parseHTML(title,url);
            }
            String path = "/Users/mingo/Desktop/mingo/d3v/src/data/";
            String codeFilename = path + "排名数据.txt";
            for (String name : names){
                nameBuffer.append(name+"\r\n");
            }
            for(String country:countries){
                countryBuffer.append(country+"\r\n");
            }

            saveAsFile(path +"选手名称.txt",nameBuffer.toString());
            saveAsFile(path +"国家名称.txt",countryBuffer.toString());
            saveAsFile(codeFilename, data.toString());
            System.out.println("================================================");
            System.out.println(codeFilename+" generate succeed!");
            System.out.println("================================================");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static final String userAgent = "Mozilla/5.0";
    private static final int timeout = 5 * 1000;
    private static final HashMap<String,String> monthWweekMap = new HashMap<String, String>() {
        {
            put("01", "1");
            put("02", "6");
            put("03", "10");
            put("04", "14");
            put("05", "18");
            put("06", "23");
            put("07", "27");
            put("08", "31");
            put("09", "36");
            put("10", "40");
            put("11", "45");
            put("12", "49");
        }
    };
    private static final HashMap<String,String> weekMonthMap = new HashMap<String, String>() {
        {
            put("1", "01");
            put("6", "02");
            put("10", "03");
            put("14", "04");
            put("18", "05");
            put("23", "06");
            put("27", "07");
            put("31", "08");
            put("36", "09");
            put("40", "10");
            put("45", "11");
            put("49", "12");
        }
    };

    private static final HashMap<String,String> nameMap = new HashMap<String, String>() {
        {
            put("WANG Liqin", "王励勤");
            put("KONG Linghui", "孔令辉");
            put("MA Lin", "马琳");
            put("CHIANG Peng-Lung", "蒋澎龙");
            put("LIU Guozheng", "刘国正");
            put("SAMSONOV Vladimir", "萨姆索诺夫");
            put("LIU Guoliang", "刘国梁");
            put("SCHLAGER Werner", "施拉格");
            put("PRIMORAC Zoran", "佐兰·普里莫拉茨");
            put("KIM Taeksoo", "金泽洙");
            put("SAIVE Jean-Michel", "让-米歇尔·塞弗");
            put("WALDNER Jan-Ove", "瓦尔德内尔");
            put("BOLL Timo", "蒂姆·波尔");
            put("KREANGA Kalinikos", "格林卡");
            put("CHUANG Chih-Yuan", "庄智渊");
            put("WANG Hao", "王皓");
            put("OH Sangeun", "丁祥恩");
            put("CHEN Qi", "陈玘");
            put("RYU Seungmin", "柳承敏");
            put("CHEN Weixing", "陈卫星");
            put("HAO Shuai", "郝帅");
            put("HOU ^^ Yingchao", "侯英超");
            put("MA Long", "马龙");
            put("GAO Ning", "高宁");
            put("JOO Saehyuk", "JOO Saehyuk");
            put("LI Ching", "李静");
            put("MAZE Michael", "米凯尔·梅兹");
            put("MIZUTANI Jun", "水谷隼");
            put("ZHANG Jike", "张继科");
            put("XU Xin", "许昕");
            put("OVTCHAROV Dimitrij", "奥恰洛夫");
            put("FAN Zhendong", "樊振东");
            put("YAN An", "闫安");
            put("FREITAS Marcos", "弗雷塔斯");
            put("FANG Bo", "方博");
            put("WONG Chun Ting", "黄镇廷");
            put("JEOUNG Youngsik", "郑荣植");
            put("NIWA Koki", "丹羽孝希");
            put("LIN Gaoyuan", "林高远");
            put("GAUZY Simon", "西蒙·高茨");
            put("MATSUDAIRA Kenta", "松平健太");
            put("LEE Sangsu", "李尚洙");
            put("HARIMOTO Tomokazu", "张本智和");
            put("CALDERANO Hugo", "雨果·卡尔德拉诺");
            put("LIANG Jingkun", "梁靖崑");
            put("JANG Woojin", "张宇镇");
            put("FALCK Mattias", "马蒂亚斯·法尔克");
            put("LIN Yun-Ju", "林昀儒");
            put("JOO Saehyuk","朱世赫");
        }
    };

    private static final HashMap<String,String> stateMap = new HashMap<String, String>() {
        {
            put("China", "中国");
            put("Chinese Taipei", "中国台湾");
            put("Belarus", "白俄罗斯");
            put("Austria", "澳大利亚");
            put("Croatia", "克罗地亚");
            put("Republic of Korea", "韩国");
            put("Belgium", "比利时");
            put("Sweden", "瑞典");
            put("Germany", "德国");
            put("Greece", "希腊");
            put("Singapore", "新加坡");
            put("Hong Kong, China", "中国香港");
            put("Denmark", "丹麦");
            put("Japan", "日本");
            put("Portugal", "葡萄牙");
            put("France", "法国");
            put("Brazil", "巴西");

        }
    };

    private static ArrayList<String> urls = new ArrayList<String>();
    private static ArrayList<String> titles = new ArrayList<String>();
    private static ArrayList<String> names = new ArrayList<String>();
    private static ArrayList<String> countries = new ArrayList<String>();

    private static StringBuffer data = new StringBuffer();
    private static StringBuffer nameBuffer = new StringBuffer();
    private static StringBuffer countryBuffer = new StringBuffer();
    private static HashMap<String,String> cache = new HashMap<String, String>();

    public static void parseHTML(String title,String url) throws IOException {
        /*[ name,type,value,date ]*/
        Document doc = Jsoup.connect(url).ignoreContentType(true).userAgent(userAgent).timeout(timeout).get();
        System.out.println(title);
        System.out.println(doc.toString());
        String content = doc.select("body").text();
        JSONObject json = new JSONObject(content);
        Object arr =  json.toMap().get("ranks");
        for(Object item : ((ArrayList) arr)){
            HashMap<String,Object> it = (HashMap<String,Object>) item;
            HashMap<String,String> country = (HashMap<String,String>) it.get("Country");
            HashMap<String,String> player = (HashMap<String,String>) it.get("Player");
            String date = "";

            if(((Integer) it.get("Year")).equals(Integer.valueOf(2019)) ||((Integer) it.get("Year")).equals(Integer.valueOf(2020))){
                String tmp = String.valueOf(it.get("Week"));
                String month = weekMonthMap.get(tmp);
                date =  String.valueOf(it.get("Year")) + "-" + month;
            }else{
                String tmp =  String.valueOf(it.get("Month"));
                if(tmp.length() == 1){ tmp = "0" + tmp;}
                date =  String.valueOf(it.get("Year")) + "-" + tmp;
            }
            String playerName = player.get("Name");
            if(nameMap.containsKey(playerName)){
                playerName = nameMap.get(playerName);
            }
            String countryName = country.get("desc");
            if(stateMap.containsKey(countryName)){
                countryName = stateMap.get(countryName);
            }
            cache.put(playerName + "," + countryName+"," + date,player.get("Name") + "," + countryName + "," + it.get("Points") + "," + date + "\r\n");
            data.append(playerName + "," +countryName + "," + it.get("Points") + "," + date + "\r\n");

            if(!names.contains(player.get("Name"))){
                names.add(player.get("Name"));
            }
            if(!countries.contains(country.get("desc"))){
                countries.add(country.get("desc"));
            }
        }
    }
    public  static  void reset(){
        cache = new HashMap<String, String>();
        data = new StringBuffer();
        data.append("name,type,value,date" + "\r\n");
        names = new ArrayList<String>();
        countries = new ArrayList<String>();
        nameBuffer = new StringBuffer();
        countryBuffer = new StringBuffer();
    }
    public  static  void generateUrlsAndTitles(){
        /*2001 - 2018 month 2019-2020 week */
        // https://ranking.ittf.com/public/s/ranking/list?category=SEN&typeGender=M%3BSINGLES&year=2001&month=1&offset=0&size=50
       //  https://ranking.ittf.com/public/s/ranking/list?category=SEN&typeGender=M%3BSINGLES&year=2020&week=49&offset=0&size=10
        String topnum = "10";
        urls = new ArrayList(){};
        titles = new ArrayList(){};
        for(int i = 2001; i<= 2020; i++){
            if(i<2019){
               for(int j=1 ;j<=12 ; j++){
                   urls.add("https://ranking.ittf.com/public/s/ranking/list?category=SEN&typeGender=M%3BSINGLES&year="+String.valueOf(i)+"&month="+String.valueOf(j)+"&offset=0&size="+topnum);
                   titles.add("year="+String.valueOf(i)+"&month="+String.valueOf(j));
               }
            }else{
                for(int j=1;j<=12;j++){
                    String temp_month =String.valueOf(j);
                    if(temp_month.length()==1){
                        temp_month  = "0" + temp_month;
                    }
                    String week = monthWweekMap.get(temp_month);
                    urls.add("https://ranking.ittf.com/public/s/ranking/list?category=SEN&typeGender=M%3BSINGLES&year="+String.valueOf(i)+"&week="+week+"&offset=0&size="+topnum);
                    titles.add("year="+String.valueOf(i)+"&week="+week);
                }
            }
        }
    }
    /**
     *
     * 把一个字符串，写入到一个文件当中去
     * @param path 目标文件的绝对路径
     * @param content 字符串
     */
    public static void saveAsFile(String path, String content) {

        FileOutputStream fop = null;
        File file;

        try {

            file = new File(path);
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
