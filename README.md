# Shop Flow

## Overview
Shop-Flow is a **backend e‑commerce REST API** built with **Spring Boot**. It provides JWT authentication, product catalog, shopping cart, and order management endpoints.

## Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA (H2 in‑memory DB)
- Lombok, Validation, Maven Wrapper

## Getting Started
1. **Prerequisites** – Java 17 (JDK) installed.
2. **Clone the repository** (already done).
3. **Build the project**:
   ```bash
   ./mvnw.cmd clean compile
   ```
4. **Run the application**:
   ```bash
   ./mvnw.cmd spring-boot:run
   ```
   The API will be available at `http://localhost:8080`.

## API Overview
- `POST /api/auth/login` – obtain JWT token.
- `GET /api/products` – list products (public).
- `GET /api/cart` – view cart (requires JWT).
- `POST /api/orders` – place order (requires JWT).

## Testing
Run unit tests with:
```bash
./mvnw.cmd test
```

## License
This project is provided for educational purposes. Feel free to adapt and extend it for real‑world use.
