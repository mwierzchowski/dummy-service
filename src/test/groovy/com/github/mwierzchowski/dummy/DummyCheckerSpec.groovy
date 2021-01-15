package com.github.mwierzchowski.dummy

import com.github.mwierzchowski.dummy.core.DummyChecker
import com.github.tomakehurst.wiremock.matching.UrlPattern
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED
import static org.apache.http.HttpHeaders.CONTENT_TYPE
import static org.apache.http.HttpStatus.SC_OK
import static org.apache.http.HttpStatus.SC_SERVICE_UNAVAILABLE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@Integration(properties = ["resilience4j.retry.instances.dummy-checker.maxRetryAttempts=2"])
class DummyCheckerSpec extends Specification {
    @Autowired
    DummyChecker dummyChecker

    @Shared
    UrlPattern apiUrl = urlPathMatching("/sunrise-sunset/.*")

    def setup() {
        reset()
        dummyChecker.evictCache()
    }

    def "Should request today's sunset time"() {
        given:
        stubFor(get(apiUrl).willReturn(aResponse()
                .withStatus(SC_OK)
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .withBodyFile("sunrise-sunset.json")))
        when:
        def sunset = dummyChecker.todaySunset()
        then:
        sunset != null
        verify(1, getRequestedFor(apiUrl))
    }

    def "Should cache today's sunset time"() {
        given:
        stubFor(get(apiUrl).willReturn(aResponse()
                .withStatus(SC_OK)
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .withBodyFile("sunrise-sunset.json")))
        when:
        def sunset1 = dummyChecker.todaySunset()
        def sunset2 = dummyChecker.todaySunset()
        then:
        sunset1 != null
        sunset2 != null
        sunset1 == sunset2
        verify(1, getRequestedFor(apiUrl))
    }


    def "Should request sunset time again when cache evicts"() {
        given:
        stubFor(get(apiUrl).willReturn(aResponse()
                .withStatus(SC_OK)
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .withBodyFile("sunrise-sunset.json")))
        when:
        def sunset1 = dummyChecker.todaySunset()
        dummyChecker.evictCache()
        def sunset2 = dummyChecker.todaySunset()
        then:
        sunset1 != null
        sunset2 != null
        verify(2, getRequestedFor(apiUrl))
    }

    def "Should retry request when it fails"() {
        given:
        stubFor(get(apiUrl).inScenario("Retries")
                .whenScenarioStateIs(STARTED)
                .willReturn(aResponse()
                        .withStatus(SC_SERVICE_UNAVAILABLE))
                .willSetStateTo("Second call"))
        stubFor(get(apiUrl).inScenario("Retries")
                .whenScenarioStateIs("Second call")
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("sunrise-sunset.json")))
        when:
        def sunset = dummyChecker.todaySunset()
        then:
        sunset != null
        verify(2, getRequestedFor(apiUrl))
    }

    def "Should throw exception when sunrise-sunset service is down"() {
        given:
        def errorMessage = "Some critical error"
        stubFor(get(apiUrl).willReturn(aResponse()
                .withStatus(SC_SERVICE_UNAVAILABLE)
                .withBody(errorMessage)))
        when:
        dummyChecker.todaySunset()
        then:
        def ex = thrown(RuntimeException)
        ex.getMessage().contains(errorMessage)
        verify(2, getRequestedFor(apiUrl))
    }

    def "Should throw exception when sunrise-sunset service returns empty response"() {
        given:
        stubFor(get(apiUrl).willReturn(aResponse()
                .withStatus(SC_OK)
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .withBody("{}")))
        when:
        dummyChecker.todaySunset()
        then:
        thrown RuntimeException
    }
}
