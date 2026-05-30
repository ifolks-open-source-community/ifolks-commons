# iFolks Commons 🛠️

[![Maven Build](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)]()
[![JUnit 5](https://img.shields.io/badge/JUnit-5%20Jupiter-orange.svg)]()
[![License](https://img.shields.io/badge/License-Apache%202.0-red.svg)]()

**iFolks Commons** is a modular collection of shared utility libraries, APIs, and cross-cutting concerns that underpin the entire **iFolks** open-source ecosystem. 

This repository provides foundational modules for security, entities, REST web services, logging, cryptography, and SOAP integration. It is designed to be highly pluggable, allowing generated full-stack applications (via **iFolks Generator**) to remain lightweight and standard-compliant.

---

## 📦 Key Modules

The repository is organized into distinct sub-modules. Currently, the most relevant modules for **iFolks Generator** and bootstrapped applications are:

| Module | Description | Key Components |
| :--- | :--- | :--- |
| **`commons-api`** | Standard shared interfaces and DTOs. | Modern Java Records such as `SelectItem`. |
| **`commons-aop`** | Aspect-Oriented Programming utilities and custom interceptors. | Aspect configurations and common interceptors. |
| **`commons-entities`** | Core persistence mappings and base domain classes. | Base JPA entities and audit models. |
| **`commons-log`** | Unified structured logging formatters. | Custom logging adapters and log level mappings. |
| **`commons-rest`** | Shared utilities for building robust Spring Boot REST controllers. | Centralized exception handler and serialization. |
| **`commons-text`** | String formatting, text template processing, and serialization. | Serializers and common string manipulations. |

### Other Shared Modules (Evolving)
* **`commons-rest-security`** : Base security architectures, token providers, and filters (JWT/RSA verifiers, role checking).
* **`commons-crypto`** : Utility components for password-hashing and ciphering.
* **`commons-jms`** : Messaging infrastructure utilities.
* **`commons-soap`** : SOAP client and marshalling templates.

---

## 🛠️ Getting Started

### Prerequisites
* **Java 21** or higher.
* **Maven 3.9** or higher.

### Installation
Clone and build the modules into your local Maven cache (`.m2`):
```bash
mvn clean install
```

This installs all `commons-*` JARs under version `1.0.0-M1` (or SNAPSHOT) so they can be seamlessly resolved by the **iFolks Generator** compiler and the **iFolks Demo** projects.

---

## 📝 License

Distributed under the Apache Software License, Version 2.0. See `LICENCE.md` for more details.
