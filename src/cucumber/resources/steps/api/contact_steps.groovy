import groovyx.net.http.*
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*

import pages.gui.*

import static cucumber.api.groovy.EN.*

When(~/^I make a "(.*?)" request to the endpoint "(.*?)"$/) { String method, String endpoint ->
    def baseUrl = System.getProperty('geb.build.baseUrl')

    response = null
    switch (method){
        case "GET":
            def client = new RESTClient(baseUrl)
            println "Making GET request to ${baseUrl+endpoint} ..."
            response = client.get( path : endpoint )
            break
        case "POST":
            throw new Exception("Step does not currently support method: ${method}!")
            break
        default:
            throw new Exception("Step does not currently support method: ${method}!")
    }
    testContext.addApiResponseToContext(response)
}

Then(~/^I get a response with a "(.*?)" status code$/) { String code ->
    assert testContext.getLastApiResponse().status == code.toInteger()
}

Then(~/^I navigate to contact page$/) { ->
 to ContactPage
}

Then(~/^I am on the contact page$/) { ->
    at ContactPage
}



