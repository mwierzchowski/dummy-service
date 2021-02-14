package com.github.mwierzchowski.dummy

import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import spock.lang.Subject

@Integration
class ApplicationSpec extends Specification {
    @Subject
    @Autowired
    Application application

    def "Application should start"() {
        expect:
        application != null
    }
}
