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
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * @author <a href="mailto:ladutsko@gmail.com">George Ladutsko</a>
 */
public class StartBotCommand extends AbstractMonoBotCommand {

    private static final Logger LOGGER = getLogger();

    public StartBotCommand(TelegramBot bot, Chat chat, String payload) {
        super(bot, chat, payload);
    }

    @Override
    protected void doExecute() {
        LOGGER.info("Send welcome");
        SendResponse response = bot.execute(new SendMessage(chat.id(),
            "I will help you convert and format ISBNs.\n\n" + BotConstants.HELP_MSG
        ).parseMode(ParseMode.HTML));
        LOGGER.debug(response.toString());
    }
}
