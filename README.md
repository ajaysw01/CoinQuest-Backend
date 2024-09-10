# CoinQuest - Cryptocurrency Trading Platform

**CoinQuest** is a comprehensive cryptocurrency trading platform developed using Spring Boot. The platform focuses on secure and efficient crypto asset management, offering a range of features such as user management, asset and wallet management, wallet-to-wallet transfers, and transaction management. The platform also integrates an AI chatbot using the Gemini API for enhanced user interaction, and implements secure payment gateway integration with robust JWT-based authentication and two-factor authentication (2FA) for transaction security.

## Features

- **User Management**: Register, login, and manage user accounts with role-based access control.
- **Asset Management**: Track and manage crypto assets, including deposits and withdrawals.
- **Wallet-to-Wallet Transfer**: Seamlessly transfer funds between wallets with secure transaction management.
- **AI Chatbot**: Integrated AI chatbot powered by the Gemini API for enhanced user support and trading assistance.
- **Payment Gateway Integration**: Secure payment processing using Razorpay.
- **JWT Authentication**: Secure authentication and authorization using JSON Web Tokens (JWT).
- **Two-Factor Authentication (2FA)**: Added layer of security for user logins and transactions.
- **Transaction Management**: Comprehensive management of transaction history and status tracking.

## Technologies Used

- **Backend**: Spring Boot, Spring Security
- **Database**: MySQL
- **Programming Language**: Java
- **Build Tool**: Maven
- **AI Integration**: Gemini API
- **Payment Gateway**: Razorpay
- **Authentication**: JWT, Two-Factor Authentication (2FA)


### Endpoints : 
### Coin Endpoints
- **GET `/coins?page={page}`**: Fetches a paginated list of coins.
- **GET `/coins/{coinId}/chart?days={days}`**: Fetches market chart data for a specific coin over a specified number of days.
- **GET `/coins/search?q={keyword}`**: Searches for a coin by keyword.
- **GET `/coins/top50`**: Fetches the top 50 coins by market cap rank.
- **GET `/coins/trading`**: Fetches trending coins.
- **GET `/coins/details/{coinId}`**: Fetches detailed information for a specific coin.

### Payment Endpoints
- **POST `/api/payment/{paymentMethod}/amount/{amount}`**: Handles payments using Razorpay or Stripe.
- **POST `/api/payment-details`**: Adds payment details for a user.
- **GET `/api/payment-details`**: Retrieves a user's payment details.

### User Endpoints
- **GET `/api/users/profile`**: Retrieves the profile of the currently authenticated user.
- **GET `/api/users/{userId}`**: Fetches a user's details by their ID.
- **GET `/api/users/email/{email}`**: Fetches a user's details by their email.
- **PATCH `/api/users/enable-two-factor/verify-otp/{otp}`**: Enables two-factor authentication after verifying an OTP.
- **PATCH `/api/users/verification/verify-otp/{otp}`**: Verifies a user's OTP to complete verification.
- **POST `/api/users/verification/{verificationType}/send-otp`**: Sends a verification OTP to the user.

### Password Reset Endpoints
- **PATCH `/auth/users/reset-password/verify-otp`**: Resets a user's password after verifying an OTP.
- **POST `/auth/users/reset-password/send-otp`**: Sends an OTP for password reset.


### **VerificationController**
- This controller manages verification operations related to the user, like enabling two-factor authentication and sending OTPs. No specific endpoints have been defined yet.

### **WalletController**
- **GET `/api/wallet`**: Retrieves the authenticated user's wallet.
- **GET `/api/wallet/transactions`**: Fetches the authenticated user's wallet transactions.
- **PUT `/api/wallet/deposit/amount/{amount}`**: Deposits money into the user's wallet.
- **PUT `/api/wallet/deposit`**: Adds money to the wallet after payment completion.
- **PUT `/api/wallet/{walletId}/transfer`**: Transfers funds from one wallet to another.
- **PUT `/api/wallet/order/{orderId}/pay`**: Pays for an order using wallet funds.

### **WatchlistController**
- **GET `/api/watchlist/user`**: Retrieves the authenticated user's watchlist.
- **POST `/api/watchlist/create`**: Creates a watchlist for the authenticated user.
- **GET `/api/watchlist/{watchlistId}`**: Retrieves a watchlist by its ID.
- **PATCH `/api/watchlist/add/coin/{coinId}`**: Adds a coin to the user's watchlist.

### **WithdrawalController**
- **POST `/api/withdrawal/{amount}`**: Initiates a withdrawal request for a specific amount.
- **PATCH `/api/admin/withdrawal/{id}/proceed/{accept}`**: Admin proceeds with or rejects a withdrawal request.
- **GET `/api/withdrawal`**: Retrieves the user's withdrawal history.
- **GET `/api/admin/withdrawal`**: Retrieves all withdrawal requests (admin-only).

### **AssetController**

- **`GET /api/assets/{assetId}`**  
  Retrieves the asset by its unique ID.

- **`GET /api/assets/coin/{coinId}/user`**  
  Retrieves an asset based on the user's ID and coin ID, using JWT for authentication.

- **`GET /api/assets`**  
  Retrieves all assets for the authenticated user, using JWT for authentication.

### **AuthController**

- **`POST /auth/signup`**  
  Registers a new user. Validates email uniqueness, creates the user, and returns a JWT token for authentication.

- **`POST /auth/signin`**  
  Authenticates a user. Returns a JWT token if the user is authenticated successfully, or prompts for 2FA if enabled.

- **`GET /auth/login/google`**  
  Redirects to Google OAuth2 authorization URI for Google login.

- **`GET /auth/login/oauth2/code/google`**  
  Handles Google OAuth2 callback and creates a user based on the Google account information.

- **`POST /auth/two-factor/otp/{otp}`**  
  Verifies a two-factor authentication OTP. Returns a JWT token if the OTP is valid.

### **ChatBotController**

- **`GET /chat/coin/{coinName}`**  
  Retrieves details for a specified coin by name.

- **`POST /chat/bot`**  
  Sends a prompt to the chatbot and retrieves a simple chat response.

- **`POST /chat/bot/coin`**  
  Sends a prompt to the chatbot to get real-time coin details.

### **OrderController**

- **`POST /api/orders/pay`**  
  Processes an order for a specified coin, using JWT for user authentication and order details.

- **`GET /api/orders/{orderId}`**  
  Retrieves an order by its ID. Ensures the order belongs to the authenticated user.

- **`GET /api/orders`**  
  Retrieves all orders for the authenticated user. Supports filtering by order type and asset symbol.


## Installation

1. **Clone the repository**:
   ```bash
   git clone git@github.com:ajaysw01/CoinQuest-Backend.git
   ```
2. **Navigate to the project directory**:
   ```bash
   cd CoinQuest-Backend
   ```
3. **Install dependencies**:
   ```bash
   mvn clean install
   ```
4. **Configure database**:
   - Update the `application.properties` file with your MySQL database details.

5. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

## Usage

1. **User Registration and Login**:
   - Use the provided endpoints to register and log in users, with JWT and 2FA for security.

2. **Asset and Wallet Management**:
   - Access endpoints for managing crypto assets and performing wallet-to-wallet transfers.

3. **AI Chatbot Integration**:
   - Interact with the AI chatbot for trading assistance using the Gemini API.

4. **Secure Payments**:
   - Integrate Razorpay for secure payment processing.

## Contributing

1. Fork the repository.
2. Create a new branch: `git checkout -b feature-name`.
3. Make your changes and commit them: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature-name`.
5. Submit a pull request.

## License
This project is licensed under the MIT License.
