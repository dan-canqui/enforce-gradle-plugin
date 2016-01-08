/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.gradle.plugins.enforce.wsc

/**
 * This class is a representation of a user account with the login type
 */
class Credential {
    String id
    String username
    String password
    String token
    String loginFormat
    String type

    String getPasswordToken() {
        return "${password}${token}"
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Credential)) return false

        Credential that = (Credential) o

        if (id != that.id) return false
        if (loginFormat != that.loginFormat) return false
        if (password != that.password) return false
        if (token != that.token) return false
        if (type != that.type) return false
        if (username != that.username) return false

        return true
    }

    int hashCode() {
        int result
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + (username != null ? username.hashCode() : 0)
        result = 31 * result + (password != null ? password.hashCode() : 0)
        result = 31 * result + (token != null ? token.hashCode() : 0)
        result = 31 * result + (loginFormat != null ? loginFormat.hashCode() : 0)
        result = 31 * result + (type != null ? type.hashCode() : 0)
        return result
    }
}
