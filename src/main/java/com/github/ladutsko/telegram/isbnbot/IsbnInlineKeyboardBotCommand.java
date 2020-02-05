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

import com.github.ladutsko.isbn.ISBN;
import com.github.ladutsko.isbn.ISBNException;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * @author <a href="mailto:ladutsko@gmail.com">George Ladutsko</a>
 */
public class IsbnInlineKeyboardBotCommand extends AbstractBotCommand {

    private static final Logger LOGGER = getLogger();

    public IsbnInlineKeyboardBotCommand(TelegramBot bot, Chat chat, String payload) {
        super(bot, chat, payload);
    }

    @Override
    public void execute() {
        SendMessage sm;

        try {
            ISBN isbn = ISBN.parseIsbn(payload);

            List<InlineKeyboardButton> buttons = new ArrayList<>(2);

            if (null != isbn.getIsbn10()) {
                buttons.add(new InlineKeyboardButton("Convert").callbackData(BotConstants.CONVERT_CMD));
            }

            buttons.add(new InlineKeyboardButton("Format").callbackData(BotConstants.FORMAT_CMD));

            InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(buttons.toArray(new InlineKeyboardButton[0]));
            sm = new SendMessage(chat.id(), payload).parseMode(ParseMode.HTML).replyMarkup(inlineKeyboard);
            LOGGER.info("Send response");
        } catch (ISBNException e) {
            sm = new SendMessage(chat.id(), e.getMessage()).parseMode(ParseMode.HTML);
            LOGGER.debug(e.getMessage());
        }

        SendResponse response = bot.execute(sm);
        LOGGER.debug(response.toString());
    }
}
