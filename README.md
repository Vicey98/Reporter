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
  ![Stock Sentiment Notifier System Design](..%2F..%2FDownloads%2FReporter%20System%20Design%20%282%29.png)

## TODO
- Better test coverage
- Cloudformation template for all AWS resources used
- Add micrometer for observability
- Standardise exceptions