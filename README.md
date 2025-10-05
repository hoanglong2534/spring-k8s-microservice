# Microservice Architecture Project

## 🚀 Tuần 1: Split Service + Docker

### Cấu trúc dự án
```
microservice/
├── service/
│   ├── user-service/          # Port 8081, DB: user_db
│   └── order-service/         # Port 8082, DB: order_db
├── docker-compose.yml         # Multi-service setup
└── README.md
```

### Công nghệ sử dụng
- **Backend**: Spring Boot 3.5.6 + Java 21
- **Database**: PostgreSQL (riêng biệt cho từng service)
- **Container**: Docker + Docker Compose
- **ORM**: Spring Data JPA + Hibernate
- **Build Tool**: Maven

### Cách chạy dự án

#### 1. Chạy với Docker Compose (Khuyến nghị)
```bash
# Build và chạy tất cả services
docker-compose up --build

# Chạy ở background
docker-compose up -d --build

# Xem logs
docker-compose logs -f

# Dừng services
docker-compose down
```

#### 2. Chạy từng service riêng lẻ
```bash
# User Service
cd service/user-service
./mvnw spring-boot:run

# Order Service  
cd service/order-service
./mvnw spring-boot:run
```

### API Endpoints

#### User Service (Port 8081)
- `POST /api/users` - Tạo user mới
- `GET /api/users` - Lấy danh sách users
- `GET /api/users/{id}` - Lấy user theo ID
- `GET /api/users/email/{email}` - Lấy user theo email
- `PUT /api/users/{id}` - Cập nhật user
- `DELETE /api/users/{id}` - Xóa user

#### Order Service (Port 8082)
- `POST /api/orders` - Tạo order mới
- `GET /api/orders` - Lấy danh sách orders
- `GET /api/orders/{id}` - Lấy order theo ID
- `GET /api/orders/user/{userId}` - Lấy orders của user
- `PUT /api/orders/{id}/status` - Cập nhật trạng thái order
- `DELETE /api/orders/{id}` - Xóa order

### Health Check
- User Service: http://localhost:8081/actuator/health
- Order Service: http://localhost:8082/actuator/health

### Test API với cURL

#### Tạo User
```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "0123456789"
  }'
```

#### Tạo Order
```bash
curl -X POST http://localhost:8082/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "shippingAddress": "123 Main St, City",
    "orderItems": [
      {
        "productId": "PROD001",
        "productName": "Laptop",
        "quantity": 1,
        "unitPrice": 1000.00
      }
    ]
  }'
```

### Database Schema

#### User Table
- `id` (Primary Key)
- `email` (Unique)
- `first_name`
- `last_name`
- `phone_number`
- `status` (ACTIVE, INACTIVE, SUSPENDED)
- `created_at`
- `updated_at`

#### Order Table
- `id` (Primary Key)
- `user_id` (Foreign Key)
- `total_amount`
- `status` (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- `shipping_address`
- `created_at`
- `updated_at`

#### Order Item Table
- `id` (Primary Key)
- `order_id` (Foreign Key)
- `product_id`
- `product_name`
- `quantity`
- `unit_price`
- `total_price`

### Kết quả Tuần 1
✅ **Hoàn thành**:
- Tách thành 2 microservice độc lập
- Mỗi service có database riêng
- Docker hóa toàn bộ hệ thống
- CRUD operations cho User và Order
- Health check endpoints
- Docker Compose để chạy multi-service

### Tiếp theo (Tuần 2)
- GitHub Actions CI/CD
- Redis cache integration
- Lua scripts cho Redis
- Automated testing



