FROM gradle:jdk8-hotspot

RUN apt update && apt install -y lsof

WORKDIR /Forge-Backend

CMD pwd

COPY build.gradle /Forge-Backend

COPY gradle/ /Forge-Backend/gradle
COPY gradlew /Forge-Backend
COPY gradlew.bat /Forge-Backend
COPY settings.gradle /Forge-Backend
COPY src/ /Forge-Backend/src

CMD ["gradle", "clean", "bootRun", "--no-daemon"]
