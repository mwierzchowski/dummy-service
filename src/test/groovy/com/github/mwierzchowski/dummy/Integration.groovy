package com.github.mwierzchowski.dummy

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.core.annotation.AliasFor
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.Target

import static java.lang.annotation.ElementType.TYPE
import static java.lang.annotation.RetentionPolicy.RUNTIME

@Inherited
@Target(TYPE)
@Retention(RUNTIME)
@SpringBootTest
@ActiveProfiles
@Transactional
@AutoConfigureWireMock(port = 0)
@interface Integration {
    @AliasFor(annotation = SpringBootTest, attribute = "properties") String[] properties() default []
    @AliasFor(annotation = ActiveProfiles, attribute = "profiles") String[] profiles() default ["test"]
}