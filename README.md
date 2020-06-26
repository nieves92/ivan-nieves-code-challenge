How to Run

To run the transactions api from the command-line, open three CMD or Terminal windows

1. In each window, change to the directory where you cloned this repo.
2. In the first window, build the application using the corresponding command:
    ./mvnw clean package (UNIX)
    ./mvnw.cmd clean package (WINDOWS)
3. In the same window run: java -jar discovery/target/discovery-1.0-SNAPSHOT.jar
4. Wait until Discovery server has started
5. Switch to the second window and run: java -jar transactions-service/target/transactions-service-1.0-SNAPSHOT.jar
6. Wait until Transactions server has started
7. You can access [Swagger UI](http://localhost:2222/swagger-ui) now or switch to the third window and run any of the following cURLs:

•Add a transaction
curl --location --request POST 'http://localhost:2222/clip/v1/transactions?userId=123' \
--header 'Cache-Control: no-cache' \
--header 'Content-Type: application/json' \
--data-raw '{
	"amount": 5,
	"description": "Ivan Nieves",
	"date": "2019-11-17"
}'

•Show a transaction
curl --location --request GET 'http://localhost:2222/clip/v1/transactions/e5e17172-08de-4ed4-8e0f-3749814892c1?userId=123' \
--header 'Cache-Control: no-cache' \
--header 'Content-Type: application/json'

•List transactions
curl --location --request GET 'http://localhost:2222/clip/v1/transactions?userId=123' \
--header 'Cache-Control: no-cache' \
--header 'Content-Type: application/json'

•Sum transactions
curl --location --head 'http://localhost:2222/clip/v1/transactions?userId=12345'

•Transactions Report
curl --location --request GET 'http://localhost:2222/clip/v1/transactions/report?userId=12345' \
--header 'Cache-Control: no-cache' \
--header 'Content-Type: application/json'

•Random Transaction
curl --location --request GET 'http://localhost:2222/clip/v1/transactions/random' \
--header 'Cache-Control: no-cache' \
--header 'Content-Type: application/json'


