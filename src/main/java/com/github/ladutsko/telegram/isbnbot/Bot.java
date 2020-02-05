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

import com.pengrad.telegrambot.TelegramBot;
import org.apache.logging.log4j.Logger;

import static java.lang.System.exit;
import static java.lang.System.getenv;
import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * @author <a href="mailto:ladutsko@gmail.com">George Ladutsko</a>
 */
public class Bot {

    private static final Logger LOGGER = getLogger();

    public static final String BOT_TOKEN_NAME = "BOT_TOKEN";

    public static void main(String[] args) {
        String botToken = getenv(BOT_TOKEN_NAME);
        if (null == botToken || botToken.isBlank()) {
            botToken = System.getProperty(BOT_TOKEN_NAME);
        }
        if (null == botToken || botToken.isBlank()) {
            System.out.println(BOT_TOKEN_NAME + " is empty or unavailable");
            exit(1);
        }

        // Create your bot passing the token received from @BotFather
        TelegramBot bot = new TelegramBot(botToken);
        LOGGER.info("Bot is created successfully");
        LOGGER.debug("Registering for updates ...");
        bot.setUpdatesListener(new BotUpdatesListener(bot), new BotExceptionHandler());
    }
}
