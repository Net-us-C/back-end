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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataService {
    public static void newTap(WebDriver driver){
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
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        // ChromeDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", "D:\\Users\\minji\\chromedriver.exe");

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

        newTap(driver);

        // 게시판으로 이동하는 버튼 클릭
        driver.findElement(By.cssSelector("a[href='/ins/index.jsp'][target='_blank']")).click();
        Thread.sleep(2000);

        newTap(driver);

        for(int i = 1; i <= 12; i++){
            String Month = i + "월";
            driver.get("https://ins2.inha.ac.kr/ITIS/ADM/SS/SS_04002/UseSearch_Pop.aspx?EquipCode=");
            driver.findElement(By.name("ddlYear")).sendKeys("2023");
            driver.findElement(By.name("ddlMonth")).sendKeys(Month);
            driver.findElement(By.name("ddlEquipCode")).sendKeys("운동장(축구장)");
            String originalHandle = driver.getWindowHandle();
            driver.findElement(By.name("ibtnReservationPrint")).click();
            driver.switchTo().window(originalHandle);

            // 요소를 식별하기 위한 XPath 또는 CSS 선택자를 사용하여 해당 요소를 찾습니다.
            WebElement element = driver.findElement(By.xpath("//script[contains(text(),'ReservationView_xml.aspx?Value=')]"));

            // 요소의 내용을 가져옵니다.
            String scriptContent = element.getAttribute("innerHTML");

            // 값을 추출하기 위한 정규식 패턴을 설정합니다.
            String valuePattern = "ReservationView_xml.aspx\\?Value=([^']+)";
            Pattern pattern = Pattern.compile(valuePattern);
            Matcher matcher = pattern.matcher(scriptContent);

            String value = "";
            if (matcher.find()) {
                value = matcher.group(1);
            }

            driver.get("https://ins2.inha.ac.kr/ITIS/ADM/SS/SS_04002/ReservationView_xml.aspx?Value=" + value);
            Thread.sleep(5000);
            System.out.println(driver.getPageSource());
        }



        // 브라우저 종료
//        driver.quit();
    }
}