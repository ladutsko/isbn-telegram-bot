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
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * @author <a href="mailto:ladutsko@gmail.com">George Ladutsko</a>
 */
public class BotUpdatesListener implements UpdatesListener {

    private static final Logger LOGGER = getLogger();

    private final TelegramBot bot;
    private final BotCommandManager commandManager;
    private final BotTaskExecutor executor;

    public BotUpdatesListener(TelegramBot bot) {
        this.bot = bot;

        commandManager = new BotCommandManager(bot);
        executor = new BotTaskExecutor();
    }

    @Override
    public int process(List<Update> updates) {
        LOGGER.info("Processing {} update(s) ...", updates.size());
        updates.stream()
               .map(update -> new CommandBotTask(bot, commandManager, update))
               .map(LoggerBotTaskWrapper::new)
               .forEach(executor::execute);
        LOGGER.debug("Confirm them all");
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
