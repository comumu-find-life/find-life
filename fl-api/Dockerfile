# base image
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사 (빌드된 JAR 파일을 docker 이미지로 복사)
COPY build/libs/fl-api.jar fl-api.jar

# 포트 열기
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "fl-api.jar"]