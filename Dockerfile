FROM eclipse-temurin:8
ADD . /qpe
RUN   cd qpe && ./gradlew fatJar
COPY ./resources /qpe/build/libs
WORKDIR /qpe/build/libs
ENTRYPOINT ["java", "-jar", "dev-interview-materials.jar", "data/citystatecountry.db"]
#ENTRYPOINT ["tail", "-f", "/dev/null"]