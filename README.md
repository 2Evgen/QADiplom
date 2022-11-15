[![Build status](https://ci.appveyor.com/api/projects/status/23seax4825881nnn/branch/master?svg=true)](https://ci.appveyor.com/project/2Evgen/qadiplom/branch/master)

> **��������� ������ ��������� ������������**

### 1. ������������ ������
_�����������:_ https://github.com/2Evgen/QADiplom
_�������:_ `git clone`

### **2. ������ SUT, ����-������ � ��������� �������**
**����������� SUT � PostgreSQL**
��������� Docker Desktop

### **3. ������� ������ � IntelliJ IDEA**

 ������� � ��������� � Docker ����������
����:  `PostgreSQL; Node.js`

� ��������� � ����� ������� ��������� ���������

`docker-compose up -d`

### **4. ��������� ����������:**

 ������� ��� ������� � ������������ PostgreSQL: 

` java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar`

 ���������� ����������� �� ������: [](http://localhost:8080/)

### **5. ������� ������ ��������**

��������� �����:

`.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app`

������� ����� Allure � ������� � ��������

` .\gradlew allureServe`

**### 6. ������� �����:**

`CTRL + C -> y -> Enter`

### **7. ������� � ������ ��������**

���������� ����������:

` CTRL + C`

���������� ����������:

`docker-compose down`

### **1. ����������� SUT � MySQL**
������� � ��������� � Docker ����������
MySQL (image mariadb)
��������� Docker Desktop

������� ������ � IntelliJ IDEA

� ��������� � ����� ������� ��������� ����������:
`
docker-compose up -d`

### **2. ��������� ����������:**

������� ��� ������� � ������������ MySQL: 

`java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar`


### **3. ������� ������ ��������**

### **4. ��������� �����:**

`.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`

������� ����� Allure � ������� � ��������

`.\gradlew allureServe`

### **5. ������� �����:**

`CTRL + C -> y -> Enter`

### **6. ������� � ������ ��������**

**���������� ����������:**

`CTRL + C`

**���������� ����������:**

`docker-compose down`


