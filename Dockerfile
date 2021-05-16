# Enable NewRelic integration - download Java Agent
FROM appropriate/curl as NewRelicInstall
WORKDIR /newrelic
RUN curl -O https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip  \
    && unzip newrelic-java.zip

# Primary deliverable
FROM adorsys/java:11

WORKDIR /app
ENV APP_PORT=9092

COPY --from=NewRelicInstall /newrelic/newrelic ./newrelic
COPY ./messaging-app/target/didp-messaging-app-*.jar ./didp-messaging-app.jar

CMD exec java $JAVA_OPTS -javaagent:newrelic/newrelic.jar -jar ./didp-messaging-app.jar --server.port=$APP_PORT