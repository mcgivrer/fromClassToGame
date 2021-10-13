// Copyright (c) 2021 Frédéric Delorme
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.snapgames.fromclasstogame.core.config.cli.exception;

public class ArgumentUnknownException extends Exception {

    public ArgumentUnknownException(String string, String key) {
        super(String.format(string, key));
    }

    private static final long serialVersionUID = 1L;

}