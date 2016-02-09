import org.openqa.selenium.OutputType
import org.openqa.selenium.WebDriverException

import static cucumber.api.groovy.Hooks.*

import geb.Browser
import geb.binding.BindingUpdater
import groovy.sql.Sql

import steps.data.setup.*
import static cucumber.api.groovy.Hooks.World

// NOTE: if you are using the steps in the geb-cucumber library, binding and unbinding
// will already be taken care of for you

def bindingUpdater
def theBrowser = null
def retailer = null
boolean teardownOfOldUsersAlreadyExecuted = false;

World() {
    testContext = new TestContext()
}


Before() { scenario ->
	System.setProperty("geb.cucumber.step.packages", "pages")
	if (!binding.hasVariable('browser')) {
		theBrowser = new Browser()
		bindingUpdater = new BindingUpdater(binding, theBrowser)
		bindingUpdater.initialize()
	} else {
		// save for later screenshot taking
		theBrowser = browser
	}

    // Refer to the build.gradle file for other environments
    def dbUrl = System.properties['dbUrl']
    def dbUser = System.properties['dbUser']
    def dbPass = System.properties['dbPass']
    def dbDriver = "oracle.jdbc.OracleDriver"

}

After() { scenario ->
	bindingUpdater?.remove()

	// embed screenshot into cucumber report
	if (scenario.failed) {
		try {
			def screenshot = theBrowser?.driver?.getScreenshotAs(OutputType.BYTES)
			if (screenshot) {
				scenario.embed(screenshot, "image/png")
			}
		} catch (WebDriverException e) {
			// sometime firefox runs out of memory trying to take a screenshot, not a big deal so ignore
		} catch (MissingMethodException e) {
			// HTMLUnit doesn't support screenshots
		}
	}

}