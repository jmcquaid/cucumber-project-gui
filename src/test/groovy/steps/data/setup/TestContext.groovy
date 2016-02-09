package steps.data.setup

/**
 * Created by jmcquaid on 27/02/15.
 */
class TestContext {
    final static String BASELINE_OPTIONS = "baselineOptions"

    def apiResponses = []
    def apiRequests = []
    def apiHelper
    def testConsignments
    def matchedConsignment
    def data = [:]

    def getLastApiResponse() {
        apiResponses.last()["response"]
    }

    def getLastApiResponse(method) {
        for (r in apiResponses.reverse()){
            if(r["method"] == method){
                return r["response"]
            }
        }
        println "No response for method ${method}!"
    }

    def getFirstApiResponse(method) {
        for (r in apiResponses){
            if(r["method"] == method){
                return r["response"]
            }
        }
        println "No response for method ${method}!"
    }

    def addApiResponseToContext(method, response) {
        def ele = [:]
        ele["method"] = method
        ele["response"] = response
        apiResponses << ele
    }

    def addApiResponseToContext(response) {
        addApiResponseToContext("unspecified", response)
    }

    def addApiRequestToContext(method, response) {
        def ele = [:]
        ele["method"] = method
        ele["request"] = response
        apiRequests << ele
    }

    def addApiRequestToContext(response) {
        addApiRequestToContext("unspecified", response)
    }
}