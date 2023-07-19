# Litmus
## What is this?
This project is a web app that notifies a user if certain stocks are trending in the news

## Demo - Retrieves messages in DB
http://litmus.ap-southeast-2.elasticbeanstalk.com/messages

## Backend System Design
Main Components:
- Lambda function hosting a Python webscraper
- Rest API exposed by API Gateway
- Spring Boot Application using Java 17 started using Elastic Beanstalk
- Postgres database storing all messages sent
- SNS topic sending SMS messages


### Design Diagram
![Reporter System Design (2)](https://github.com/Vicey98/Reporter/assets/46387884/744b964b-2c62-46d3-8f5a-6d9847dfad7d)


## TODO
- Better test coverage
- Cloudformation template for all AWS resources used
- Add micrometer for observability
- Standardise exceptions
