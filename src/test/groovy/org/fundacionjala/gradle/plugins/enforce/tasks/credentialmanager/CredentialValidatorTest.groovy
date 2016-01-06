/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.gradle.plugins.enforce.tasks.credentialmanager

import org.fundacionjala.gradle.plugins.enforce.wsc.Connector
import org.fundacionjala.gradle.plugins.enforce.wsc.Credential
import org.fundacionjala.gradle.plugins.enforce.wsc.Session
import spock.lang.Shared
import spock.lang.Specification

class CredentialValidatorTest extends Specification {
    @Shared
    Connector connector
    @Shared
    Session session

    def setup() {
        connector = Mock(Connector)
        session = Mock(Session)
        connector.login(_) >> { Credential credential ->
            if (credential.id == "default" &&
                    credential.username == "username@email.com" &&
                    credential.password == "0bdzKV14J7bs2h1yw01eMQ==" &&
                    credential.token == "xthkbHWGppQTkrqFhHzpzw==") {
                return session
            }
            throw new Exception()
        }
    }

    def "Test should throw exception if credential is null"() {
        when:
        CredentialValidator.isValidCredential(null)
        then:
        thrown(IllegalArgumentException)
    }

    def "Test should return 'false' if credential is not active"() {
        given:
        Credential credential = new Credential()
        credential.id = "invalid"
        credential.username = "invaliduser@email.com"
        credential.token = "h6Sxot/neQychayvgq+tkg=="
        credential.password = "q30XOAOUVuyD7+zVrIZypQ=="
        when:
        boolean validated = CredentialValidator.isValidCredential(credential)
        then:
        validated == false
    }
}
