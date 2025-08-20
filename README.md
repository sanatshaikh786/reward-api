# 🏆 Reward Points API

This is a Spring Boot REST API that calculates monthly and total reward points for customers based on their purchase transactions over a specified period.

---

## 📚 Business Logic

Customers earn reward points based on transaction amounts:

- ✅ **2 points** for every dollar spent **over $100**.
- ✅ **1 point** for every dollar spent **between $50 and $100**.
- ❌ No points for spending $50 or less.

### 🧮 Example:

A transaction of **$120** earns:

- (120 - 100) × 2 = 40 points  
- (100 - 50) × 1 = 50 points  
- **Total = 90 points**

