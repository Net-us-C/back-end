package inhaus.inhaus.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class DataService {
    public static void main(String[] args) throws IOException, InterruptedException {
        // ChromeDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Park\\Desktop\\chromedriver_win32\\chromedriver.exe");

        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 전제촤면으로 실행
        options.addArguments("--disable-popup-blocking"); // 팝업 무시
        options.addArguments("--disable-default-app"); // 기본앱 사용안함

        // WebDriver 객체 생성
        WebDriver driver = new ChromeDriver();

        // 로그인 페이지로 이동
        driver.get("https://portal.inha.ac.kr/");
        Thread.sleep(2000); // 페이지 로딩 대기 시간 주기.

        // 아이디/비밀번호 입력
        driver.findElement(By.name("user_id")).sendKeys("id");
        driver.findElement(By.name("user_password")).sendKeys("pw");

        // 로그인 버튼 클릭
        driver.findElement(By.cssSelector("input[type='button'][onclick='doLogin()']")).click();
        Thread.sleep(2000); // 페이지 로딩 대기 시간 주기.

        // 현재 탭의 핸들(식별자) 저장
        String currentHandle = driver.getWindowHandle();

        // 새로운 탭의 핸들 찾기
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentHandle)) {
                // 새로운 탭으로 전환
                driver.switchTo().window(handle);
                break;
            }
        }
//        driver.get("https://ins2.inha.ac.kr/ITIS/ADM/SS/SS_04002/UseSearch_Pop.aspx?EquipCode=");
        // 게시판으로 이동하는 버튼 클릭
        driver.findElement(By.cssSelector("a[href='/ins/index.jsp'][target='_blank']")).click();
        Thread.sleep(5000);

        driver.get("https://ins2.inha.ac.kr/ITIS/FRAMESET.htm");



        // 브라우저 종료
//        driver.quit();
    }
}