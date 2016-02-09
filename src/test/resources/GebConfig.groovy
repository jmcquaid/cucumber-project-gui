/*
	This is the Geb configuration file.
	
	See: http://www.gebish.org/manual/current/configuration.html
*/


import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities;

// Use firefox as the default
// See: http://code.google.com/p/selenium/wiki/FirefoxDriver


    driver = {new FirefoxDriver(getFirefoxBinary(), getFirefoxProfile(getDownLoadDirectoryPath()))}

environments {

    // run as “gradle -Dgeb.env=chrome cucumber”
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    chrome {
        driver = { new ChromeDriver() }
    }

    // run as “gradle -Dgeb.env=phantomjs cucumber”
    // See: https://github.com/detro/ghostdriver
    phantomjs {
        driver = { new PhantomJSDriver() }
    }

}
//Sometimes Firefox driver times out when trying to find the root HTML element of the page.
//You can prevent this error from happening by configuring a wait timeout to use when the driver is locating the root HTML element:
baseNavigatorWaiting = true

def getFirefoxBinary() {
	def HEADLESS_MODE = System.getProperty("linux-unix-headless-mode").toBoolean()
	def FF_BINARY_FILE = System.getProperty("ff-binary-file")
	
	FirefoxBinary firefoxBinary = new FirefoxBinary();
	
	if (HEADLESS_MODE) {
        firefoxBinary.setEnvironmentProperty("DISPLAY", ":10");
    }
	
	if(FF_BINARY_FILE!= null && !FF_BINARY_FILE.isEmpty()) {
		println "Creating Firefox binary with: " + FF_BINARY_FILE 
		firefoxBinary = new FirefoxBinary(new File(FF_BINARY_FILE))		
	}
	
	firefoxBinary
}

def getFirefoxProfile(String userDownloadDir) {
    // For more details see: https://developer.mozilla.org/en/docs/Download_Manager_preferences
    FirefoxProfile firefoxProfile = new FirefoxProfile();
    firefoxProfile.setPreference("browser.download.folderList", 2);
    firefoxProfile.setPreference("browser.download.dir", userDownloadDir);
    firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
    firefoxProfile.setPreference("browser.download.panel.firstSessionCompleted", true);
    firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/pdf,application/x-pdf");
    firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
    firefoxProfile.setPreference("browser.download.panel.shown", false);
    firefoxProfile.setPreference("browser.download.useToolkitUI", true);
    firefoxProfile.setPreference("pdfjs.disabled", true);
    return firefoxProfile;
}

def getDownLoadDirectoryPath(){
    return System.getProperty("temporaryDir")
}

waiting {
    timeout = 10
    retryInterval = 0.5
}