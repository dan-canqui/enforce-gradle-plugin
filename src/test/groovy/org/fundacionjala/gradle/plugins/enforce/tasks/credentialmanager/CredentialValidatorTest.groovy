/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.gradle.plugins.enforce.tasks.credentialmanager

import org.fundacionjala.gradle.plugins.enforce.credentialmanagement.CredentialFileManager
import org.fundacionjala.gradle.plugins.enforce.wsc.Connector
import org.fundacionjala.gradle.plugins.enforce.wsc.Credential
import org.fundacionjala.gradle.plugins.enforce.wsc.Session
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Paths

class CredentialValidatorTest extends Specification {
    @Shared
    String path = Paths.get(System.getProperty("user.dir"), "src", "test", "groovy", "org", "fundacionjala", "gradle",
            "plugins", "enforce", "tasks", "credentialmanager", "resources").toString()

    @Shared
    String pathCredentials = Paths.get(path, "credentials.dat").toString()

    @Shared
    String pathSecretKeyGenerated = Paths.get(path, "secretKeyGenerated.text").toString()

    @Shared
    CredentialFileManager credentialFileManager

    @Shared
    Connector connector

    @Shared
    Session session

    def setup() {
        credentialFileManager = new CredentialFileManager(pathCredentials, pathSecretKeyGenerated)
        connector = Mock(Connector)
        session = Mock(Session)
        connector.login(_) >> { Credential credential ->
            if (credential.equals(credentialFileManager.getCredentialById("default"))) {
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
        Credential credential = credentialFileManager.getCredentialById("invalid")
        when:
        boolean validated = CredentialValidator.isValidCredential(credential)
        then:
        validated == false
    }
}
