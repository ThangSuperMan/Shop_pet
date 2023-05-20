# RESTful API Spring Boot Server

[![Build Status](https://travis-ci.org/hagopj13/node-express-boilerplate.svg?branch=master)](https://travis-ci.org/hagopj13/node-express-boilerplate)
[![Coverage Status](https://coveralls.io/repos/github/hagopj13/node-express-boilerplate/badge.svg?branch=master)](https://coveralls.io/github/hagopj13/node-express-boilerplate?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2ab03f5d62a1404f87a659afe8d6d5de)](https://www.codacy.com/manual/hagopj13/node-express-mongoose-boilerplate?utm_source=github.com&utm_medium=referral&utm_content=hagopj13/node-express-boilerplate&utm_campaign=Badge_Grade)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

A project for quickly building RESTful APIs using Spring Boot Java and Postgresql.

By running some commands, you will get a production-ready Spring Boot installed and fully configured on your machine. The app comes with many built-in features, such as authentication using JWT, request validation, API documentation, pagination, etc. For more details, check the features list below.

## Quick Start

## Manual Installation

If you would still prefer to do the installation manually, follow these steps:

Clone the repo:

```bash
git clone https://github.com/ThangSuperMan/Shoppet_server
cd Shoppet_server
```

Install the dependencies and run the app:

```bash
./mvnw spring-boot:run
```

Add enviroment variables

```bash
cp application.yml src/main/resources
```

## Table of Contents

- [Features](#features)
- [Commands](#commands)
- [Enviroment Variable](#enviroment-variable)
- [Project Structure](#product-structure)
- [API Documentation](#api-documentation)
- [Error Handling](#error-handling)

## Features

- **Sql database**: [Postgresql](https://www.postgresql.org)
- **Authentication and authorization**: using [JWT (Json web token)](https://jwt.io) with library [jsonwebtoken](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt/0.9.1)
- **API documentation**: with [SpringDoc OpenAPI Starter WebMVC UI](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui/2.0.0-M2)
- **Security**: set security HTTP headers using [Spring Boot Starter Security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security/1.2.5.RELEASE)
- **Paypal payment**: integrate payment paypal gateway using [Paypal SDK](https://mvnrepository.com/artifact/com.paypal.sdk/rest-api-sdk/1.6.0)

## Commands

Running locally:

```bash
./mvnw spring-boot:run
```

Docker (setup Postgresql container):

```bash
# Build docker container
docker-compose up -d

# Create a new database name shop_pet
psql -h localhost -p 5432 -U root

# Running sql query after prompted to the psql prompt
create database shop_pet;

# Import data sql file to docker container
cd src/sql
psql -h localhost -p 5432 -U root --dbname=shop_pet -f ImportData.sql
```
