Tên Dự Án : Code REST API quản lý user

## **Yêu Cầu Trước**
> **LƯU Ý:** Trước khi thiết lập dự án, hãy đảm bảo bạn đã cài đặt các phần mềm sau trên hệ thống của bạn:

Java Development Kit (JDK): Phiên bản 17 trở lên (khuyến nghị: OpenJDK 17)
Apache Maven: Phiên bản 3.6.0 trở lên (để quản lý phụ thuộc và xây dựng)
MongoDB: Phiên bản 4.4 trở lên (dùng local hoặc remote)
Git: Để clone repository
IDE: Tùy chọn (ví dụ: IntelliJ IDEA, Eclipse) cho phát triển
Postman : Để kiểm tra API 

Các Bước Cài Đặt
1. Clone Repository
Clone dự án từ repository Git về máy local của bạn:

##    git clone https://github.com/trantienanh204/BAI_TEST_DST.git

3. Cấu Hình application.properties
Chỉnh sửa file src/main/resources/application.properties với thông tin kết nối MongoDB:

cung cấp sẵn :  

##       spring.data.mongodb.uri=mongodb+srv://trantienanht00:090909999@tanh.xghyg.mongodb.net/DTS_USERS?retryWrites=true&w=majority

> **LƯU Ý:**       có thể thay đế theo ý thích nếu muốn 

4. Cài Đặt Phụ Thuộc

các thư viện cần đã có trong pom.xmp sẽ được tự động cài khi vào dự án

5. Khởi Động Ứng Dụng
Khởi động ứng dụng Spring Boot bằng các phương pháp sau:

Sử Dụng IDE
Mở dự án trong IDE của bạn (ví dụ: IntelliJ IDEA).
Tìm class chính (ví dụ: com.example.demo.DemoApplication).
Chạy ứng dụng như một ứng dụng Spring Boot.

6. Xác Thực

Để truy cập các endpoint được bảo vệ (ví dụ: /api/users/{id}), lấy token JWT bằng cách đăng nhập:
POST /api/login

ví dụ Postman

Body:  
tài khoản admin mẫu nếu dùng data được cung cấp sẵn :


```json
{
  "username": "admin",
  "password": "123456"
}
```
Phản hồi mong đợi (200 OK):
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBRE1JTiJdLCJzdWIiOiJhYmMiLCJpYXQiOjE3NTA0MjYwMTcsImV4cCI6MTc1MDUxMjQxN30...",
    "username": "abc",
    "roles": ["ADMIN"]
}
```
tài khoản user mẫu nếu dùng data được cung cấp sẵn :


```json
{
  "username": "nguyenvana",
  "password": "password123"
}
```
Phản hồi mong đợi (200 OK):
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBRE1JTiJdLCJzdWIiOiJhYmMiLCJpYXQiOjE3NTA0MjYwMTcsImV4cCI6MTc1MDUxMjQxN30...",
    "username": "nguyenvana",
    "roles": ["USER"]
}
```

7. Kiểm Tra Dịch Vụ
Đảm bảo MongoDB đang chạy trên localhost:27017 (hoặc host/port bạn cấu hình).
Mở trình duyệt hoặc sử dụng Postman để kiểm tra các endpoint API:
GET /api/users: Lấy danh sách tất cả người dùng (yêu cầu xác thực). 
POST /api/users: Thêm người dùng mới. 
Ví dụ request (sử dụng Postman):
Phương thức: POST
URL: http://localhost:8080/api/users
Body:
```json
{
    "name": "Nguyen Van A",
    "username": "nguyenvana",
    "password": "password123",
    "email": "nguyenvana@example.com",
    "phone": "0987654321",
    "avatar": "",
    "roles": ["USER"]
}
```
> **LƯU Ý:** và để có thể vào các endpoint API bạn cần login 

khi bạn đã có token 

ví dụ Postman
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBRE1JTiJdLCJzdWIiOiJhYmMiLCJpYXQiOjE3NTA0MjYwMTcsImV4cCI6MTc1MDUxMjQxN30...",
    "username": "abc",
    "roles": ["ADMIN"]
}
```
## lấy token khi đã đăng nhập thành công rồi dán vào 

Vào tab Authorization.

Chọn Type: Bearer Token.

Dán token bạn vừa nhận được vào ô Token.

Gửi request tới các endpoint cần xác thực (ví dụ: GET /api/users).

Phản hồi mong đợi (200 ok):
```json
{
{
        "id": "68558c7ca711d716323ffe55",
        "name": "a thị b",
        "username": "aaa",
        "email": "athib@gmail.com",
        "phone": "0987654321",
        "avatar": "notavata.gpn",
        "roles": [
            "USER"
        ],
        "status": true,
        "delete": false,
        "ngayTao": "2025-06-20",
        "ngaySua": "2025-06-20"
    }
}
```
7. Docker
   
# User API - Docker Image

## Link Docker Hub
https://hub.docker.com/r/tanh0206/user-api

## Hướng dẫn chạy app

```bash
docker pull tanh0206/user-api
docker run -p 8080:8080 tanh0206/user-api
```


## Xử Lý Sự Cố

Lỗi Kết Nối MongoDB: Đảm bảo MongoDB đang chạy và chuỗi kết nối trong application.properties đúng.

403 Forbidden: Kiểm tra xem token JWT đã được thêm vào header Authorization và chưa hết hạn.

Lỗi Validation: Đảm bảo tất cả các trường bắt buộc (name, username, password, email, roles) được cung cấp và tuân thủ quy tắc validation.

