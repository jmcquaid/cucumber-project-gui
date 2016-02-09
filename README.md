# Automated Testing of DM

The goal of this project is to allow automatic testing of [DM] using a BDD approach.

- Tests must be defined using [Gherkin] DSL.
- Tests must be repeatable on any environment.
- A test report (including screenshots) will be published.

This is an project uses [Geb], [Cucumber-JVM] and [WSlite].

## Usage

To run the tests, run the following depending on your platform.

	*nix    - ./gradlew cucumber
	Windows - gradlew.bat cucumber

Note: This will run all scenarios that have not been tagged as `@wip` (work in progress). 

To run a subset of tests, include the `--tags` parameter.
For more details about how to "AND"ing and "OR"ing Tags see https://github.com/cucumber/cucumber/wiki/Tags

	*nix    - ./gradlew cucumber -Dcucumber.options="--tags @wip"
	Windows - gradlew.bat cucumber -Dcucumber.options="--tags @wip"

See the `src/test/resources/GebConfig.groovy` file for how to run the tests with different browsers.

If running on a linux box, you can run in firefox headless:

install xvfb:

    sudo apt-get install xvfb

Run xvfb:

    Xvfb :10

Run tests:

    *linux    - ./gradlew cucumber -Dlinux-unix-headless-mode=true

## Older firefox versions

By default the tests will use the default firefox on your environment. However, it is possible to run the tests using an older version of firefox.

    OSX     - ./gradlew cucumber -Dff-binary-file=~/Applications/Firefox-34.app/Contents/MacOS/firefox-bin
    Linux   - ./gradlew cucumber -Dff-binary-file=/opt/firefox30/firefox/firefox
    Windows - gradlew.bat cucumber -Dff-binary-file="C:\\Program Files (x86)\\Firefox32\\firefox.exe"

Note: It is assumed that the older versions of firefox have been previously installed, this process does not install them.

For OSX users refer to https://github.com/smclernon/homebrew-firefox for installing legacy Firefox versions.

## Environments

By default the scenarios will be run against the [DMC1] environment. This will determine which base url is going to be used and also provide the database credentials.

    *nix    - ./gradlew cucumber -Denv=DMC1
    Windows - gradlew.bat cucumber -Denv=DMC1

So in the above case it will run the tests against http://dmtstcis1.metapack.local and use the nctestdb01.metapack.local database.

If you need to add a new environment update the `build.gradle` file in the ext/envDetails section. 

## Reporting

Once a test run has completed the results can be viewed in the following location;

	*nix    - ./build/reports/cucumber/index.html
	Windows - .\build\reports\cucumber\index.html

Note: if a test has failed it will include a screenshot of page that failed.

## Reference

[The Book Of Geb] is useful background reading to understand the concepts.

## Structure of repo

The following tree describes how this project is structured and an indication of what/where files should be stored.

```
├── sql                   - Sql scripts that are used for setting up/tearing down test data
└── src
    ├── cucumber
    │   └── resources
    │       ├── env       - Runtime configuration
    │       ├── features
    │       │   └── gui   - scenario to be run against the web application only
    │       └── steps
    │           └── gui   - step definitions to implement the features
    └── test
        ├── groovy
        │   └── pages
        │       └── gui   - Page objects to define key parts of a page
        └── resources     - Configuration for tests
```

## Technical debt

This project is on-going however there are issues that have arisen during implementation that whilst they work should be reviewed and refactored at the earliest opportunity.

- **Selectors** - Whilst this is strictly not an issue with this project and more to do with construction of the DM GUI web app. No or very little semantic id's have been used in the HTML markup, which as a result means that the selectors being used are very brittle. Refer to [DMC-56] for details.


[DM]: http://www.metapack.com "Delivery Manager"
[Gherkin]: https://github.com/cucumber/cucumber/wiki/Gherkin "Gherkin Domain Specific Language"
[Geb]: http://geb.codehaus.org/ "Geb - Groovy Browser Automation"
[Cucumber-JVM]: http://cukes.info/install-cucumber-jvm.html "Cucumber JVM" 
[WSlite]: https://github.com/jwagenleitner/groovy-wslite "Groovy WSlite"
[The Book Of Geb]: http://www.gebish.org/manual/current/ "The Book Of Geb"
[DMC-56]: https://metapack.atlassian.net/browse/DMC-56
[DMC1]: https://metapack.atlassian.net/wiki/display/MOR/QA+environments "Formally known as DMTSTCIS1"
