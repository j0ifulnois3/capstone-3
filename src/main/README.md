I have structured a comprehensive, highly professional markdown documentation file that addresses every single requirement given in your capstone guidelines[cite: 312, 601]. It details your layered Spring Boot architecture, provides an explicit REST endpoint tracking grid, highlights the specific structural safeguards you engineered, and showcases the dynamic shopping cart item enrichment code block[cite: 436, 569, 601].

Below is the complete text representation of the file that you can use to review or directly upload into your GitHub workspace repository:

```markdown
# EasyShop / Grocery Store Backend API (Capstone 3)

An enterprise-grade, layered Spring Boot REST API for a multi-category e-commerce storefront. This backend serves as the core engine powering product lookups, category controls, secure authentication, and a fully dynamic, persistent shopping cart architecture.

---

## 🛠️ Tech Stack & Architecture

- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Data Access:** Spring Data JPA / Hibernate
- **Database:** MySQL Workbench
- **Security:** Spring Security & JWT (JSON Web Tokens)
- **Testing:** Insomnia REST Client & Swagger UI

The system implements a clean, industry-standard **Layered Architecture Pattern**:
1. **Controller Layer (`@RestController`):** Exposes secure REST endpoints, maps incoming request bodies, sets appropriate HTTP status codes, and applies strict role-based access tokens.
2. **Service Layer (`@Service`):** Houses the core business logic, orchestrates cross-service records, performs dynamic transaction calculations, and ensures data enrichment.
3. **Repository Layer (`@Repository`):** Leverages Spring Data JPA to interface seamlessly with the MySQL relational database using custom derived query signatures.

---

## 🚀 Implemented Features

### 🏢 Phase 1: Categories Controller & Security Alignment
- Implemented public read endpoints (`GET /categories` and `GET /categories/{id}`).
- Exposed nested resource querying for viewing product subsets cleanly (`GET /categories/{categoryId}/products`).
- Established secure write operations (`POST`, `PUT`, `DELETE`) guarded with `@PreAuthorize("hasRole('ROLE_ADMIN')")` to restrict access strictly to administrative personnel.
- Built explicit validation safeguards returning custom `404 Not Found` messages whenever users try to alter or delete non-existent category IDs.

### 🐛 Phase 2: Core Bug Fixes & User Domain Hardening
- **Authentication Resilience:** Audited the data layer implementation to transition password storage away from plain text into cryptographically safe hashing utilizing standard `BCryptPasswordEncoder` operations.
- **Product Filter Resolution:** Repaired underlying structural criteria parameters within the `ProductsController` search signatures to accept optional category metrics (`cat`), range variations (`minPrice`, `maxPrice`), and subcategory keyword filters cleanly.

### 🛒 Phase 3: Advanced Shopping Cart Management System
- Constructed a fully modular database tracking model mapping transactional line rows (`CartItem`) securely to individual user identifiers.
- Created dynamic object-level enrichment handlers inside the service layer to reconstruct standard persistence elements into deep nested response trees containing full product detail snapshots on the fly.
- Embedded runtime safety controls (`@Transactional`) guaranteeing multi-row purge safety whenever a user empties their active cart collection.

---

## 💡 Code Showcase: Dynamic Shopping Cart Enrichment

Below is an interesting piece of code from the project showcasing how the backend converts raw database rows into complete, context-enriched shopping cart items for the frontend view layer:

```java
private ShoppingCart buildCartFromDb(int userId)
{
    ShoppingCart cart = new ShoppingCart();

    // 1. Fetch raw cart records from the database using our custom repository query
    List<CartItem> dbItems = shoppingCartRepository.findByUserId(userId);

    // 2. Iterate through each database row, enrich it with full product details, and compile the map
    for (CartItem dbItem : dbItems)
    {
        Product product = productService.getById(dbItem.getProductId());
        if (product != null)
        {
            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(dbItem.getQuantity());

            cart.add(cartItem); // Reconstructs into the required Map layout dynamically
        }
    }

    return cart;
}