<p align="center">
  <img src="assets/ifolks-commons-logo.jpg" alt="iFolks Commons Logo" width="220" />
</p>

<h1 align="center">iFolks Commons</h1>

<p align="center">
  <a href="https://ifolks-org.atlassian.net/wiki/spaces/COMMONS/overview"><img src="https://img.shields.io/badge/Documentation-commons.ifolks.org-blue.svg" alt="Documentation" /></a>
  <img src="https://img.shields.io/badge/Java-21-orange.svg" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen.svg" alt="Spring Boot 4.x" />
  <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="License" />
</p>

<p align="center">
  <strong>Enterprise Cross-Cutting Concerns, Structured JSON Logging &amp; AOP Foundation</strong>
</p>

---

## 📖 Official Documentation

To avoid documentation drift, complete architecture manuals, structured JSON schemas, and configuration guides are maintained on our official portal:

👉 **[commons.ifolks.org](https://ifolks-org.atlassian.net/wiki/spaces/COMMONS/overview)**

* 📖 **[User Guide](https://ifolks-org.atlassian.net/wiki/spaces/COMMONS/pages/1898086405/User+guide)**: Dual-channel logging (`ACCESS_LOG`, `ERROR_LOG`), distributed tracing & MDC propagation, GDPR data redaction (`@SensitiveData`), and `logback-spring.xml` setups.
* 🛠 **[Developer Guide](https://ifolks-org.atlassian.net/wiki/spaces/COMMONS/pages/1898184722/Developer+guide)**: Multi-module breakdown, AOP Template Method patterns, aspect execution ordering (`@Order(0)` vs `@Order(2)`), and Jackson 3 serialization internals.
* 📦 **[Releases & Changelogs](https://ifolks-org.atlassian.net/wiki/spaces/COMMONS/pages/1893007361/Releases)**: Detailed version history and upgrade notes.

---

## ⚡ Quickstart

Add the core logging and REST interception modules to your Spring Boot project:

```xml
<dependency>
    <groupId>org.ifolks.commons</groupId>
    <artifactId>commons-log</artifactId>
    <version>1.1.0</version>
</dependency>

<dependency>
    <groupId>org.ifolks.commons</groupId>
    <artifactId>commons-rest</artifactId>
    <version>1.1.0</version>
</dependency>
```

Annotate sensitive fields on your DTOs or Java Records to guarantee zero log leaks:

```java
public record UserForm(
    String username,
    
    @SensitiveData // Redacted as "*****" in logs, preserved in REST API responses
    String password
) {}
```

---

## 📝 License

Distributed under the **Apache Software License, Version 2.0**. See [`LICENCE.md`](LICENCE.md) for details.
