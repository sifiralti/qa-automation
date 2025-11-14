package com.qa.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void init() {
        // 0) Manuel path verildiyse WDM'i atla
        String manualPath = null;
        try { manualPath = ConfigReader.get("chrome.driver.path"); } catch (Exception ignored) {}
        if (manualPath != null && !manualPath.trim().isEmpty()) {
            System.setProperty("webdriver.chrome.driver", manualPath.trim());
            System.out.println("[DRIVER] Using manual path: " + manualPath);
            DRIVER.set(new ChromeDriver(defaultOptions()));
            return;
        }

        // 1) Config'ten major oku (örn: 142)
        String major = null;
        try { major = ConfigReader.get("chrome.major"); } catch (Exception ignored) {}

        // 2) Kullanıcı cache'ini tamamen by-pass et: proje içine temiz cache ve zorla indir
        WebDriverManager wdm = WebDriverManager.chromedriver()
                .cachePath("target/wdm")     // sadece proje içi cache'i kullan
                .avoidResolutionCache()      // çözüm cache'ini kullanma
                .clearDriverCache()          // daha önce indirilmiş driver'ları temizle
                .clearResolutionCache()      // çözüm cache'ini temizle
                .forceDownload();            // her seferinde temiz indirme dene

        if (major != null && !major.trim().isEmpty()) {
            wdm = wdm.browserVersion(major.trim());
            System.out.println("[DRIVER] Forcing ChromeDriver major: " + major.trim());
        }

        // 3) İndir/kur ve kullanılan driver yolunu logla
        wdm.setup();
        System.out.println("[DRIVER] webdriver.chrome.driver=" + System.getProperty("webdriver.chrome.driver"));

        // 4) Driver oluştur (gerekirse retry)
        try {
            DRIVER.set(new ChromeDriver(defaultOptions()));
        } catch (SessionNotCreatedException e) {
            System.out.println("[DRIVER] SessionNotCreated — retry with forced download");
            // son bir kez daha temiz indir ve tekrar dene
            WebDriverManager.chromedriver()
                    .cachePath("target/wdm")
                    .clearDriverCache()
                    .clearResolutionCache()
                    .forceDownload()
                    .browserVersion(major != null ? major.trim() : "")
                    .setup();
            System.out.println("[DRIVER] webdriver.chrome.driver(RETRY)=" + System.getProperty("webdriver.chrome.driver"));
            DRIVER.set(new ChromeDriver(defaultOptions()));
        }
    }

    private static ChromeOptions defaultOptions() {
        ChromeOptions opts = new ChromeOptions();
        // CI'da gerekirse:
        // opts.addArguments("--headless=new");
        opts.addArguments("--window-size=1920,1080");
        opts.addArguments("--remote-allow-origins=*");
        // Gerekirse:
        // opts.addArguments("--disable-gpu", "--ignore-certificate-errors");
        return opts;
    }

    public static WebDriver get() { return DRIVER.get(); }

    public static void quit() {
        if (DRIVER.get() != null) {
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}
