package inhaus.inhaus.data;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.apache.catalina.connector.Response;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataService {
    private static final String LOGIN_URL = "https://portal.inha.ac.kr/login.jsp";
    private static final String Board_URL = "https://portal.inha.ac.kr/p/00015/";
    // 테스트로 지금은 학사행정 부분 url을 넣어놨습니다.
    private static String ID = "12201702";
    private static String PW = "kimmj010611!";
    private static Map<String, String> TryCookies;
    private static Map<String, String> loginCookies;
    private static Map<String, String> Session = new HashMap<String, String>();


    public static void main(String[] args) throws IOException {
        // 웹사이트 연결
        Jsoup.connect(LOGIN_URL).get();
        // 쿠키 가져오기
        Connection.Response loginPageResponse = Jsoup.connect(LOGIN_URL)
                .timeout(3000)
                .header("Origin", "https://portal.inha.ac.kr")
                .header("Referer", "https://portal.inha.ac.kr/inha/SsoTokenService.do")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .method(Connection.Method.GET)
                .execute();

        Document loginPageDocument = loginPageResponse.parse();
        TryCookies = loginPageResponse.cookies();

        Document doc = Jsoup.connect(LOGIN_URL)
                .method(Connection.Method.GET)
                .execute()
                .parse();
        Element form = doc.select("form[name=loginFrm]").first();
        String formName = form.attr("name");
        // 로그인(POST)
        Connection.Response loginResponse = Jsoup.connect("https://portal.inha.ac.kr/inha/SsoTokenService.do")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Whale/3.20.182.14 Safari/537.36")
                .timeout(3000)
                .header("Origin", "https://portal.inha.ac.kr")
                .header("Referer", "https://portal.inha.ac.kr/login.jsp")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .data("user_id", ID)
                .data("user_password", PW)
                .data("com.tomato.portal.contents.redirection.url","/p/00015/")
                .data("loginFrm", formName)
                //.data(token??)
                .cookies(TryCookies)
                .method(Connection.Method.POST)
                .execute();

        loginCookies = loginResponse.cookies();
        Session.putAll(loginCookies);

        System.out.println(Session); // 쿠키 표시.

        Document doc2 = Jsoup.connect(Board_URL)
                .cookies(loginCookies)
                .get();
        System.out.println(""+doc2.toString());
    }
}