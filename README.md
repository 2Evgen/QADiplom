[![Build status](https://ci.appveyor.com/api/projects/status/23seax4825881nnn/branch/master?svg=true)](https://ci.appveyor.com/project/2Evgen/qadiplom/branch/master)

> **Дипломный проект профессии «Тестировщик»**

### 1. Склонировать проект
_репозиторий:_ https://github.com/2Evgen/QADiplom
_команда:_ `git clone`

### **2. Запуск SUT, авто-тестов и генерация репорта**
**Подключение SUT к PostgreSQL**
Запустить Docker Desktop

### **3. Открыть проект в IntelliJ IDEA**

 Скачать и запустить в Docker контейнеры
СУБД:  `PostgreSQL; Node.js`

В терминале в корне проекта запустить контейнер

`docker-compose up -d`

### **4. Запустить приложение:**

 команда для запуска с подключением PostgreSQL: 

` java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar`

 приложение запускается по адресу: [](http://localhost:8080/)

### **5. Открыть второй терминал**

Запустить тесты:

`.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app`

Создать отчёт Allure и открыть в браузере

` .\gradlew allureServe`

**### 6. Закрыть отчёт:**

`CTRL + C -> y -> Enter`

### **7. Перейти в первый терминал**

Остановить приложение:

` CTRL + C`

Остановить контейнеры:

`docker-compose down`

### **1. Подключение SUT к MySQL**
Скачать и запустить в Docker контейнеры
MySQL (image mariadb)
Запустить Docker Desktop

Открыть проект в IntelliJ IDEA

В терминале в корне проекта запустить контейнеры:
`
docker-compose up -d`

### **2. Запустить приложение:**

команда для запуска с подключением MySQL: 

`java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar`


### **3. Открыть второй терминал**

### **4. Запустить тесты:**

`.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`

Создать отчёт Allure и открыть в браузере

`.\gradlew allureServe`

### **5. Закрыть отчёт:**

`CTRL + C -> y -> Enter`

### **6. Перейти в первый терминал**

**Остановить приложение:**

`CTRL + C`

**Остановить контейнеры:**

`docker-compose down`


