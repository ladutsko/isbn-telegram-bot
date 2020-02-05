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
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.MessageEntity.Type;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * @author <a href="mailto:ladutsko@gmail.com">George Ladutsko</a>
 */
public class BotCommandManager {

    private static final Logger LOGGER = getLogger();

    private static final Map<String, Class<? extends AbstractBotCommand>> COMMAND_MAP =
        Map.of(BotConstants.CONVERT_CMD, ConvertIsbnBotCommand.class,
               BotConstants.FORMAT_CMD,  FormatIsbnBotCommand.class,
               BotConstants.START_CMD,   StartBotCommand.class,
               BotConstants.HELP_CMD,    HelpBotCommand.class);

    private final TelegramBot bot;

    public BotCommandManager(TelegramBot bot) {
        this.bot = bot;
    }

    public BotCommand extract(Message message) {
        String text = message.text();
        if (null == text || text.isBlank()) {
            return NopBotCommand.INSTANCE;
        }

        Chat chat = message.chat();

        MessageEntity[] entities = message.entities();
        if (null != entities) {
            for (MessageEntity entry : entities) {
                if (Type.bot_command == entry.type()) {
                    String cmd = text.substring(entry.offset(), entry.offset() + entry.length()).toLowerCase();
                    if (!COMMAND_MAP.containsKey(cmd)) {
                        return new ErrorMessageBotCommand(bot, chat,
                            "I cannot recognize the command: " + cmd + "\n\n" + BotConstants.HELP_MSG);
                    }

                    String payload = text.substring(entry.offset() + entry.length()).trim();
                    return newInstance(chat, cmd, payload);
                }
            }
        }

        return new IsbnInlineKeyboardBotCommand(bot, chat, text.trim());
    }

    public BotCommand extract(CallbackQuery query) {
        Message message = query.message();
        Chat chat = message.chat();
        String cmd = query.data();
        if (!COMMAND_MAP.containsKey(cmd)) {
            return new ErrorMessageBotCommand(bot, chat, "OPPS!");
        }

        return newInstance(chat, cmd, message.text());
    }

    private BotCommand newInstance(Chat chat, String cmd, String payload) {
        Class<? extends AbstractBotCommand> clazz = COMMAND_MAP.get(cmd);
        try {
            Constructor<? extends AbstractBotCommand> constructor =
                clazz.getConstructor(TelegramBot.class, Chat.class, String.class);
            return constructor.newInstance(bot, chat, payload);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            LOGGER.error(t.getMessage());
            return new ErrorMessageBotCommand(bot, chat, "OPPS!");
        } catch (ReflectiveOperationException e) {
            LOGGER.error(e.getMessage(), e);
            return new ErrorMessageBotCommand(bot, chat, "OPPS!");
        }
    }
}
