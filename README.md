# Reward API

This project calculates customer reward points based on their purchase history over a selected time range.

## How it works
- This is a Spring Boot REST API
- Calculates points per transaction based on rules
- Provides totals and monthly breakdowns

API Usage
GET /api/rewards?customerId=C1 Optional params: start (YYYY-MM-DD), end (YYYY-MM-DD) If start/end omitted, last 3 months from today are used.