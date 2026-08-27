# Используем актуальный образ Eclipse Temurin (Java 21)
FROM eclipse-temurin:21-jdk

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файлы для сборки
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Даем права на выполнение mvnw и скачиваем зависимости
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Копируем исходный код
COPY src src

# Собираем приложение (пропускаем тесты для скорости)
RUN ./mvnw package -DskipTests

# Команда для запуска приложения
CMD ["java", "-jar", "target/*.jar"]
