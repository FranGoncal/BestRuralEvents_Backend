# BestRuralEvents_Backend

This project is a microservices-based backend system built with **Spring Boot**, designed for a rural events promotion platform, accompanied by a mobile application.

The platform allows users to discover, explore, and interact with events such as fairs, festivals, and local activities. It also enables organizers to create, manage, and promote their own events.

## Architecture

The system follows a **microservices architecture**, using:

- **Eureka Server** for service discovery  
- **API Gateway** as the single entry point for client requests  

## Microservices

The backend is composed of several independent services:

- **Auth Service** – authentication and JWT management  
- **User Service** – user profiles and account data  
- **Event Service** – event creation and management  
- **Review Service** – user reviews and ratings  
- **Ticket Service** – ticket management and purchases  
- **Payment Service** – simulated payment processing  
- **Notification Service** – user notifications  
- **Promotion Service** – featured and promoted events  
- **Info Service** – FAQs and static platform content  


# Purpose of each service

## Infrastructure Services

### Eureka Server
Responsible for:
- service discovery
- keeping a registry of all available microservices
- allowing services to find each other dynamically

This service answers the question:  
**“Where is each microservice located?”**

It is not a business microservice. Its role is to support communication between services in a microservice architecture.

Example:
- `auth-service` registers itself in Eureka
- `event-service` registers itself in Eureka
- `ticket-service` can discover `payment-service` through Eureka

This is useful because services do not need to hardcode the addresses of other services.

---

### API Gateway
Responsible for:
- acting as the single entry point for the mobile app
- routing requests to the correct microservice
- centralizing cross-cutting concerns such as authentication checks, logging, or rate limiting if needed

This service answers the question:  
**“Through which door does the client access the backend?”**

The Android application should communicate with the Gateway instead of calling each microservice directly.

Example:
- `/api/auth/**` -> Auth Service
- `/api/events/**` -> Event Service
- `/api/tickets/**` -> Ticket Service

This makes the architecture cleaner and easier to manage.

---

## Business Microservices

### Auth Service
Responsible for:
- login
- registration
- credential validation
- JWT generation and validation
- refresh token support, if implemented

This service answers the question:  
**“Who are you, and are you allowed to access the system?”**

It should focus on authentication and security, not on storing all profile information about the user.

---

### User Service
Responsible for:
- user profile data
- name
- email
- avatar or profile image
- user role, such as attendee, organizer, or admin
- account-related preferences

This service answers the question:  
**“Who is this user inside the platform?”**

It is separate from the Auth Service because authentication and user profile management are different responsibilities.

---

### Event Service
Responsible for:
- creating events
- editing events
- deleting events
- listing events
- retrieving event details
- managing organizer-created events
- storing event metadata such as title, description, date, location, and category

This service answers the question:  
**“What events exist in the platform?”**

It is one of the core services of the application because the platform revolves around rural events.

---

### Review Service
Responsible for:
- creating reviews
- listing reviews for an event
- storing ratings
- calculating average rating

This service answers the question:  
**“What do users think about an event?”**

It helps add trust and social proof to the platform.

Possible business rules:
- only authenticated users can leave reviews
- optionally, only users who bought a ticket can review an event

Even if the second rule is not implemented, it can still be mentioned as a future improvement.

---

### Ticket Service
Responsible for:
- ticket types
- ticket stock or availability
- ticket purchase or reservation
- ticket generation
- storing purchased tickets
- organizer-side ticket management

This service answers the question:  
**“How are tickets managed for an event?”**

It is strongly connected to:
- Event Service
- Payment Service
- Notification Service

This is one of the most important business services because it handles the core transaction of attending events.

---

### Notification Service
Responsible for:
- sending purchase confirmations
- notifying users that a ticket was generated
- reminding users about upcoming events
- creating notification history for the mobile app
- optionally simulating email or push notifications

This service answers the question:  
**“How does the platform communicate important updates to the user?”**

Even if real push notifications or emails are not implemented, this service still makes sense in the architecture.

Example flow:
- Payment Service confirms a payment
- Ticket Service generates a ticket
- Notification Service creates a user notification

This service is useful because it decouples user communication from the rest of the business logic.

---

### Payment Service
Responsible for:
- simulating payment processing
- validating fake payment data
- returning payment results such as:
  - `SUCCESS`
  - `FAILED`
  - `PENDING`
- storing transaction history
- informing the Ticket Service of the payment result

This service answers the question:  
**“Was the payment for the ticket successful?”**

For a university project, it does not need to connect to a real payment provider.  
A simulated payment service is enough to represent real business flows.

Example fake logic:
- if the card number ends in `1` -> payment fails
- if the card number ends in `2` -> payment is pending
- otherwise -> payment succeeds

This service is valuable because it allows the system to model realistic purchase scenarios without requiring external integrations.

---

### Info Service
Responsible for:
- FAQs
- help content
- platform information pages
- terms and conditions
- privacy policy
- static informational content used in the app

This service answers the question:  
**“Where does the user get support and platform-related information?”**

It is useful for keeping static and informational content separate from the main business services.

This service is especially appropriate if the mobile application includes:
- an FAQ page
- help or support sections
- informational text not directly related to event transactions

---

### Promotion Service
Responsible for:
- managing promoted events
- deciding which events appear on the main page
- handling featured events
- handling sponsored or boosted visibility
- promotion periods with start and end dates
- promotion priority or ordering

This service answers the question:  
**“Which events should receive more visibility in the application?”**

In this architecture, the events shown on the main page are promoted events.  
Because of that, this service plays an important role in shaping the homepage experience.

Possible responsibilities include:
- selecting homepage highlights
- ranking promoted events
- activating or deactivating promotions
- associating events with promotion campaigns

This service makes sense if promotions are treated as a separate business concern instead of being stored directly inside the Event Service.

