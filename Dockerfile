# Sử dụng JDK làm image gốc
FROM openjdk:17-jdk-slim

# Tạo thư mục để chứa file jar
WORKDIR /app

# Copy file jar từ máy host vào container
COPY target/*.jar app.jar

# Expose port ứng dụng sẽ chạy (Spring Boot mặc định là 8080)
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
