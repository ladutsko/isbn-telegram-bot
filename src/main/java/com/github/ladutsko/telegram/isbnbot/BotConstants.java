/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 George Ladutsko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.ladutsko.telegram.isbnbot;

/**
 * @author <a href="mailto:ladutsko@gmail.com">George Ladutsko</a>
 */
public class BotConstants {

    public static final String CONVERT_CMD = "/convert";
    public static final String FORMAT_CMD  = "/format";
    public static final String START_CMD   = "/start";
    public static final String HELP_CMD    = "/help";

    static final String HELP_MSG =
        "You can control me by sending these commands:\n\n" +
        CONVERT_CMD + " ISBN - convert the ISBN13 to ISBN10 and vice versa\n" +
        FORMAT_CMD + " ISBN - format the ISBN13 or ISBN10\n";

    private BotConstants() {
        // There are no use cases for this class where you need to build an object. You can only use static items.
        // I am preventing you from even trying to build an object of this class.
    }
}
