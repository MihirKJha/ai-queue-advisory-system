# AI Queue Advisory System

# Architecture

---

# 1. Architecture Overview

The AI Queue Advisory System follows a cloud-native, layered architecture based on the **Ports & Adapters (Hexagonal Architecture)** pattern.

The system separates business logic from cloud provider implementations, enabling clean code, maintainability, and future multi-cloud support.

The first implementation targets **AWS**, while the architecture allows future integration with Azure and Google Cloud Platform without modifying the business layer.

---

# 2. Architectural Principles

The solution is designed around the following principles:

- Clean Architecture
- Ports & Adapters (Hexagonal Architecture)
- Separation of Concerns
- Domain-Driven Design (DDD)
- Cloud-Native Design
- Infrastructure as Code (Terraform)
- Cloud Provider Independence
- Extensibility
- Maintainability

---

# 3. High-Level Architecture

```text
                           Client
                              │
                              ▼
                     Spring Boot REST API
                              │
                              ▼
                   Application Service Layer
                              │
            ┌─────────────────┼─────────────────┐
            ▼                 ▼                 ▼
     Vision Provider     Storage Provider    LLM Provider
            │                 │                 │
            ▼                 ▼                 ▼
      AWS Rekognition       Amazon S3       Amazon Bedrock
```

---

# 4. Layered Architecture

## Presentation Layer

Responsible for exposing REST APIs.

Responsibilities

- Receive HTTP requests
- Validate requests
- Return HTTP responses
- Handle exceptions

Package

```
api/
```

---

## Application Layer

Coordinates business workflows.

Responsibilities

- Execute use cases
- Orchestrate business operations
- Invoke capability ports

Package

```
application/
```

---

## Domain Layer

Contains business models and business rules.

Responsibilities

- Domain Models
- Value Objects
- Business Exceptions
- Domain Enums

Package

```
domain/
```

---

## Ports Layer

Defines business capabilities.

Responsibilities

- Abstract cloud services
- Define application contracts

Examples

```
VisionProvider

StorageProvider

LLMProvider
```

Package

```
ports/
```

---

## Adapters Layer

Implements cloud-specific integrations.

Responsibilities

- AWS SDK Integration
- External service communication

AWS Adapters

```
RekognitionAdapter

BedrockAdapter

S3Adapter
```

Package

```
adapters/aws/
```

---

# 5. Request Flow

```text
User

↓

REST API

↓

QueueAnalysisService

↓

StorageProvider

↓

VisionProvider

↓

LLMProvider

↓

Response
```

---

# 6. Cloud Services

| Service | Purpose |
|----------|---------|
| Amazon S3 | Store uploaded queue images |
| Amazon Rekognition | Detect people in queue |
| Amazon Bedrock | Generate AI advisory |
| Spring Boot | REST Backend |
| Terraform | Infrastructure Provisioning |

---

# 7. Project Structure

```text
ai-queue-advisory-system

├── backend
│
├── infrastructure
│
├── frontend
│
├── docs
│
├── diagrams
│
└── .github
```

---

# 8. Backend Structure

```text
backend/

├── api/

├── application/

├── domain/

├── ports/

├── adapters/
│
│   └── aws/

├── configuration/

├── prompts/

└── utils/
```

---

# 9. Capability Ports

The business layer depends only on capability interfaces.

Current interfaces

- VisionProvider
- StorageProvider
- LLMProvider

Future interfaces

- OCRProvider
- SpeechProvider
- TranslationProvider
- NotificationProvider

---

# 10. Multi-Cloud Architecture

The architecture is designed to support multiple cloud providers.

```text
                    QueueAnalysisService
                            │
          ┌─────────────────┼─────────────────┐
          ▼                 ▼                 ▼
   Vision Provider   Storage Provider    LLM Provider
          │                 │                 │
 ┌────────┼────────┐ ┌──────┼───────┐ ┌───────┼────────┐
 ▼        ▼        ▼ ▼      ▼       ▼ ▼       ▼        ▼
AWS     Azure     GCP AWS   Azure   GCP AWS  Azure    GCP
```

The business layer remains unchanged when introducing new cloud providers.

---

# 11. Technology Stack

## Language

- Java 21

## Framework

- Spring Boot 3.x

## Build Tool

- Gradle (Kotlin DSL)

## Cloud Platform

- AWS

## Cloud AI Services

- Amazon Rekognition
- Amazon Bedrock

## Storage

- Amazon S3

## Infrastructure

- Terraform

## Testing

- JUnit 5
- Mockito

---

# 12. Deployment Architecture

```text
                User

                  │

                  ▼

          API Gateway (Optional)

                  │

                  ▼

          Spring Boot Application

                  │

     ┌────────────┼─────────────┐

     ▼            ▼             ▼

 Amazon S3   Rekognition   Bedrock
```

---

# 13. Architectural Decisions

| Decision | Rationale |
|----------|-----------|
| Spring Boot | Enterprise-ready REST framework |
| Java 21 | Modern LTS release with strong enterprise support |
| Gradle | Fast builds and flexible dependency management |
| Ports & Adapters | Decouple business logic from cloud providers |
| AWS SDK v2 | Native integration with AWS AI services |
| Terraform | Infrastructure as Code |
| Cloud-Native Design | Scalability and maintainability |

---

# 14. Future Evolution

Version 1

- Image-based queue analysis
- AWS implementation
- REST API

Version 2

- Multi-cloud support
- Dashboard
- Notifications

Version 3

- Live CCTV monitoring
- Video analytics
- Historical analytics
- Predictive wait-time estimation

---

# 15. Conclusion

The AI Queue Advisory System is designed as a cloud-native, enterprise-grade application that combines Computer Vision and Generative AI to provide intelligent queue advisory.

By adopting Ports & Adapters (Hexagonal Architecture), the solution maintains a clean separation between business logic and cloud-specific implementations, enabling future expansion to additional cloud providers while preserving the core application design.