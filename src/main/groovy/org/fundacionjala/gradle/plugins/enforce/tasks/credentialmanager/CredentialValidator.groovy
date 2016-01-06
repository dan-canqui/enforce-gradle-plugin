/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.gradle.plugins.enforce.tasks.credentialmanager

import org.fundacionjala.gradle.plugins.enforce.credentialmanagement.CredentialEncrypter
import org.fundacionjala.gradle.plugins.enforce.wsc.Connector
import org.fundacionjala.gradle.plugins.enforce.wsc.Credential

import java.nio.file.Paths

/**
 * This class performs/validates SFDC credentials.
 */
class CredentialValidator {
    private static final String SECRET_KEY_PATH = Paths.get(System.properties['user.home'].toString(), 'keyGenerated.txt').toString()
    private static final String NULL_MESSAGE = "Can not validate a null credential"

    /**
     * Validates a encrypt credential on Enforce.
     *
     * @param credential to be validated.
     * @return true if it's possible validate the credential, otherwise return false.
     * @exception IllegalArgumentException if credential is null.
     */
    public static boolean isValidCredential(Credential credential) {
        if (credential == null) {
            throw new IllegalArgumentException(NULL_MESSAGE)
        }
        credential = decryptCredential(credential)
        try {
            (new Connector(credential.loginFormat)).login(credential)
        } catch (Exception ex) {
            return Boolean.FALSE
        }
        return Boolean.TRUE
    }

    /**
     * Returns the decrypted credential.
     *
     * @param credential to be decrypted.
     * @return the decrypt credential.
     */
    private static Credential decryptCredential(Credential credential) {
        String secretKey = new File(SECRET_KEY_PATH).text
        CredentialEncrypter credentialEncrypter = new CredentialEncrypter()
        return credentialEncrypter.decryptCredential(credential, secretKey)
    }
}
