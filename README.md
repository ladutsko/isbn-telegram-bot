# ISBN Telegram Bot

It will help you convert and format ISBNs.

You can control it by sending these commands:
- _/convert ISBN_ - convert the ISBN13 to ISBN10 and vice versa
- _/format ISBN_ - format the ISBN13 or ISBN10

## Authorizing your bot

Use `BOT_TOKEN` environment variable to provide your bot authentication token.
You can learn about authorizing token in [this document](https://core.telegram.org/bots/api#authorizing-your-bot).

## Development environment
### Build

- Linux: `./gradlew clean build`
- Windows: `gradlew.bat clean build`

### Run

- Linux: `./gradlew run` or just `./gradlew`
- Windows: `gradlew.bat run` or just `gradlew.bat`

## Docker
### Build

1. Build the application
2. `docker build -t isbn-telegram-bot .`

### Run

`docker run -ti --rm -e BOT_TOKEN=<BOT_TOKEN> isbn-telegram-bot`

## Heroku

You can learn about deploying on Heroku in [this document](https://devcenter.heroku.com/articles/deploying-java).
