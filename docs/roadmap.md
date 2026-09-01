# AI Queue Advisory System

## Project Development Roadmap

---

## Current Progress

### Phase 0 — Solution Design

- ✅ `solution-design.md`
- ✅ `architecture.md`
- ✅ `deployment.md`

---

## Phase 1 — Project Foundation

### 1.1 Spring Boot Initialization

- ✅ Spring Boot project initialized
- ✅ Java 25 LTS
- ✅ Gradle
- ✅ Gradle Kotlin DSL

### 1.2 Gradle Configuration

- ✅ Gradle configuration
- ✅ Java 25 toolchain
- ✅ Gradle Wrapper
- ✅ Required initial dependencies

### 1.3 Package Structure

- ✅ Hexagonal Architecture structure
- ✅ API layer
- ✅ Application layer
- ✅ Domain layer
- ✅ Ports
- ✅ Adapters
- ✅ Configuration
- ✅ Common utilities

### 1.4 Application Configuration

- ✅ `application.yml`
- ✅ Development configuration
- ✅ Production configuration
- ✅ Environment-based configuration
- ✅ Prompt resources structure

### 1.5 Core Application Setup

#### 1.5.1 Global Exception Handling

- ✅ `GlobalExceptionHandler`
- ✅ Generic exception handling

#### 1.5.2 Validation

- ✅ Jakarta Validation
- ✅ Validation testing
- ✅ Validation error handling

#### 1.5.3 Health API

- ✅ Spring Boot Actuator
- ✅ `/actuator/health`
- ✅ Health endpoint verified

#### 1.5.4 Standard API Response

- 🔄 `ApiResponse`
- 🔄 `ApiError`
- ⬜ Integrate with `GlobalExceptionHandler`
- ⬜ Verify success response
- ⬜ Verify error response

#### 1.5.5 OpenAPI / Swagger

- ⬜ Add Springdoc OpenAPI
- ⬜ Configure Swagger UI
- ⬜ Configure API documentation

#### 1.5.6 Logging

- ⬜ Configure application logging
- ⬜ Configure log levels
- ⬜ Establish logging conventions

> Advanced observability such as structured logging, CloudWatch integration, correlation IDs, metrics, and tracing will be handled later in Phase 10.

#### 1.5.7 Build & Verification

- ⬜ Clean build
- ⬜ Run tests
- ⬜ Start application
- ⬜ Verify health endpoint
- ⬜ Verify validation
- ⬜ Verify API documentation
- ⬜ Review Git status
- ⬜ Commit Phase 1 foundation

---

# Phase 2 — Domain Design

Design the business domain without depending on AWS or cloud SDKs.

## 2.1 Domain Models

Create:

- `QueueAnalysis`
- `QueueMetrics`
- `QueueImage`
- `QueueAdvice`

## 2.2 Value Objects

Create appropriate value objects for:

- People count
- Waiting time
- Image metadata
- Service duration

## 2.3 Enums

Define domain enums such as:

- `QueueStatus`
- `RecommendationType`
- `ImageFormat`

## 2.4 Domain Exceptions

Define business-level exceptions.

Examples:

- `QueueAnalysisException`
- `InvalidQueueImageException`
- `InvalidWaitingTimeException`

## 2.5 API Models

Create:

- `AnalyzeQueueRequest`
- `AnalyzeQueueResponse`
- `ErrorResponse`

## 2.6 Business Rules

Define rules such as:

- Supported image formats
- Maximum image size
- Valid service duration
- Valid available time
- Queue analysis constraints

---

# Phase 3 — Capability Ports

Define cloud-independent interfaces.

## 3.1 Vision Provider

```text
VisionProvider
```

Responsible for computer vision analysis.

## 3.2 Storage Provider

```text
StorageProvider
```

Responsible for image storage.

## 3.3 LLM Provider

```text
LLMProvider
```

Responsible for Generative AI inference.

## Future Ports

Potential future capabilities:

- `OCRProvider`
- `SpeechProvider`
- `TranslationProvider`
- `NotificationProvider`

The application layer depends on these interfaces rather than directly depending on cloud SDKs.

---

# Phase 4 — AWS Adapters

Implement the first cloud provider using AWS.

## 4.1 Amazon S3

```text
StorageProvider
       ↓
S3Adapter
       ↓
Amazon S3
```

Responsibilities:

- Upload queue image
- Generate storage location
- Handle storage errors

## 4.2 Amazon Rekognition

```text
VisionProvider
       ↓
RekognitionAdapter
       ↓
Amazon Rekognition
```

Responsibilities:

- Analyze queue image
- Detect people
- Return vision metrics

## 4.3 Amazon Bedrock

```text
LLMProvider
       ↓
BedrockAdapter
       ↓
Amazon Bedrock
```

Responsibilities:

- Build model request
- Invoke foundation model
- Parse model response
- Handle AI service errors

---

# Phase 5 — Application Use Case

Implement the core business workflow.

## 5.1 Analyze Queue Use Case

```text
AnalyzeQueueUseCase
```

Workflow:

```text
Receive Request
       ↓
Validate Input
       ↓
Store Image
       ↓
Analyze Image
       ↓
Build Queue Metrics
       ↓
Generate AI Advisory
       ↓
Build Response
```

The application layer should not know whether the implementation is AWS, Azure, or GCP.

---

# Phase 6 — REST API

Expose the application through REST APIs.

## Primary Endpoint

```http
POST /api/v1/queue/analyze
```

Input:

- Queue image
- Average service time
- Available time

Output:

- People detected
- Queue metrics
- AI advisory
- Image storage location

## Additional Endpoints

```http
GET /api/v1/health

GET /api/v1/info
```

## API Features

- Request validation
- Exception handling
- Standard response format
- OpenAPI documentation

---

# Phase 7 — Infrastructure as Code

Provision AWS infrastructure using Terraform.

## AWS Resources

Potential resources include:

- Amazon S3
- IAM Roles
- IAM Policies
- Backend deployment resources
- Logging resources
- Networking resources

## Terraform Structure

```text
infrastructure/
└── terraform/
    ├── modules/
    ├── environments/
    │   ├── dev/
    │   ├── test/
    │   └── prod/
    └── variables/
```

## Goals

- Repeatable infrastructure
- Version-controlled infrastructure
- Environment isolation
- Least-privilege IAM
- Reproducible deployments

---

# Phase 8 — Frontend

Build a simple user interface.

## User Flow

```text
Upload Queue Image
       ↓
Enter Service Time
       ↓
Enter Available Time
       ↓
Analyze
       ↓
Display Queue Information
       ↓
Display AI Advisory
```

The frontend communicates only with the REST API.

---

# Phase 9 — Testing

Build tests around architectural boundaries.

## Unit Tests

- Domain tests
- Application tests
- Business rule tests

## API Tests

- Request validation
- Response validation
- Error handling

## Adapter Tests

- S3 adapter
- Rekognition adapter
- Bedrock adapter

## Integration Tests

Verify interactions between major application components.

Use mocks where appropriate to avoid unnecessary cloud service calls during unit testing.

---

# Phase 10 — Observability

Add production-oriented operational capabilities.

## Logging

- Structured logging
- Request logging
- Error logging
- Correlation IDs

## Monitoring

- Application metrics
- Request latency
- Error rates
- AI service invocation metrics

## AWS

- Amazon CloudWatch
- CloudWatch Logs
- CloudWatch Metrics

## Future

- Distributed tracing
- OpenTelemetry

---

# Phase 11 — CI/CD

Implement GitHub Actions.

## Pipeline

```text
Git Push
   ↓
Checkout
   ↓
Setup Java 25
   ↓
Gradle Build
   ↓
Run Tests
   ↓
Quality Checks
   ↓
Terraform Validation
   ↓
Docker Build
   ↓
Deployment
```

## Goals

- Automated builds
- Automated testing
- Consistent deployments
- Infrastructure validation

---

# Phase 12 — Final Documentation

Finalize the project documentation.

## Core Documentation

```text
docs/
├── solution-design.md
├── architecture.md
└── deployment.md
```

## README

The root README should include:

- Project overview
- Business problem
- Architecture
- Technology stack
- AWS services
- Local setup
- API usage
- Deployment
- Testing
- Screenshots
- Future roadmap

## Additional Artifacts

- Architecture diagrams
- API documentation
- AWS service mapping
- Cost considerations
- Security considerations
- Design decisions

---

# Phase 13 — Multi-Cloud Expansion

Extend the architecture beyond AWS.

## AWS

```text
VisionProvider
       ↓
RekognitionAdapter

StorageProvider
       ↓
S3Adapter

LLMProvider
       ↓
BedrockAdapter
```

## Azure

```text
VisionProvider
       ↓
AzureVisionAdapter

StorageProvider
       ↓
AzureBlobAdapter

LLMProvider
       ↓
AzureOpenAIAdapter
```

## Google Cloud

```text
VisionProvider
       ↓
GoogleVisionAdapter

StorageProvider
       ↓
CloudStorageAdapter

LLMProvider
       ↓
VertexAIAdapter
```

## Target Architecture

```text
                    Application
                         │
                         ▼
                 Capability Ports
                         │
          ┌──────────────┼──────────────┐
          ▼              ▼              ▼
         AWS            Azure           GCP
          │              │              │
      Adapters        Adapters       Adapters
```

The goal is to keep the core business and application layers independent of the cloud provider.

---

# Overall Project Journey

```text
Phase 0
Solution Design
       ↓
Phase 1
Project Foundation
       ↓
Phase 2
Domain Design
       ↓
Phase 3
Capability Ports
       ↓
Phase 4
AWS Adapters
       ↓
Phase 5
Application Use Case
       ↓
Phase 6
REST API
       ↓
Phase 7
Terraform
       ↓
Phase 8
Frontend
       ↓
Phase 9
Testing
       ↓
Phase 10
Observability
       ↓
Phase 11
CI/CD
       ↓
Phase 12
Documentation
       ↓
Phase 13
Multi-Cloud
AWS → Azure → GCP
```

---

# Portfolio Objective

The final project should demonstrate more than the ability to call AWS AI APIs.

It should demonstrate the ability to:

- Design a Cloud AI solution
- Model a business domain
- Apply Hexagonal Architecture
- Abstract cloud AI capabilities
- Integrate managed AI services
- Build REST APIs
- Provision infrastructure with Terraform
- Containerize and deploy applications
- Implement testing and observability
- Build CI/CD pipelines
- Extend the architecture toward multi-cloud

The objective is to demonstrate **Cloud AI Architecture capability**, not simply completion of a cloud AI lab.