# Litmus

## What is this?

This app compiles the best odds for UFC matches across available bookmakers. It is a full stack application that uses
React for the frontend and Spring Boot for the backend. The backend is connected to a Postgres RDS database and uses
in-memory caching to reduce the number of calls to the database. The frontend is deployed to an S3 bucket and the
backend is deployed to an ECS cluster. The ECS cluster is deployed using an ECR to ECS pipeline.

## URL

http://report-alb-99542802.ap-southeast-2.elb.amazonaws.com/

## How to run

1. Install Docker
2. Run `docker-compose.sh` in the root directory
3. Run `npm run dev` in the frontend directory

## Backend System Design

Main Components:

- React frontend w/ Spring backend
- Java 17
- Postgres RDS DB
- Test containers for integration tests
- In-memory caching
- ECR to ECS pipeline

### Design Diagram

![reporter-sys drawio](https://github.com/user-attachments/assets/dae5e76c-847d-4f1d-a504-223c6699e5fc)
