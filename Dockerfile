FROM openjdk:11-jdk-slim
ENTRYPOINT ["./entrypoint.sh"]

ARG APP_HOME=/opt/isbn-telegram-bot

ARG USERNAME=isbnbot
ARG GROUPNAME=isbnbot

RUN groupadd -r $GROUPNAME && useradd --no-log-init -r -g $GROUPNAME $USERNAME

USER $USERNAME
WORKDIR $APP_HOME

COPY --chown=$USERNAME:$GROUPNAME src/main/docker/ .
COPY --chown=$USERNAME:$GROUPNAME build/dependencies/* lib/
COPY --chown=$USERNAME:$GROUPNAME build/libs/* lib/

RUN chmod +x entrypoint.sh
