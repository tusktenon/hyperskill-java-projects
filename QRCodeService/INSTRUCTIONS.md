# QRCode Service

## Project description

This project utilizes QR codes — 2D barcodes that can store large amounts of data and are easily read by smartphones. Through this Spring Boot project, users can learn about the technology behind QR codes, generate them programmatically, and integrate them into a web service.

[View more](https://hyperskill.org/projects/385)


## Stage 1/5: Basics unveiled

### Description

Our project begins with REST API endpoints. Given the straightforward functionality of this project, our API will only need two endpoints.

The first endpoint `GET /api/health` will consistently respond with the status code `200 OK`. This endpoint allows clients to ping the service, verifying its operation and availability.

The second endpoint, `GET /api/qrcode`, will be utilized by clients to retrieve QR code images. The necessary logic for this will be implemented in subsequent stages. For the time being, it should respond with the status code `501 NOT IMPLEMENTED`.

### Objectives

Kick off the project by creating a Spring Boot web application. This application should be operable on any free port. You may use the default port `8080` or designate another port in the application.properties file. For example:
```text
server.port=8181
```

Next, create a REST Controller that will manage two endpoints:

- `GET /api/health` endpoint that will respond with a status code `200 OK`.
- `GET /api/qrcode` endpoint that will respond with a status code `501 NOT IMPLEMENTED`.

### Examples

**Example 1.** *a GET request to /api/health:*

*Response:* `200 OK`

**Example 2.** *a GET request to /api/qrcode:*

*Response:* `501 NOT IMPLEMENTED`
