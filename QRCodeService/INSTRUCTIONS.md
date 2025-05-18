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


## Stage 2/5: First image

### Description

QR codes are square-shaped patterns comprised of black squares on a white background. Your web service will generate QR codes and send them to clients as images.

In this stage, you must create a 250x250 pixel image filled with white color. You can accomplish this using the `java.awt.image.BufferedImage` class, which allows for image data manipulation. Here's an example:
```java
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
Graphics2D g = image.createGraphics();

g.setColor(Color.WHITE);
g.fillRect(0, 0, width, height);
```

In this example, first, create a `BufferedImage` object with the specified width and height and an 8-bit RGB color space. Then, generate a `Graphics2D` object to draw into the image. Set the color to white and draw a rectangle to fill the image with white color.

Next, serialize the `BufferedImage` and include it in the response body. There are several ways to do this, but we recommend the following two methods:

- Return the `BufferedImage` from your request handler method and let Spring Boot serialize it for you. You'll need to create a `HttpMessageConverter<BufferedImage>` bean to facilitate the serialization:
    ```java
    // in the REST controller class

    @GetMapping(path = "/api/qrcode")
    public ResponseEntity<BufferedImage> getImage() {
        BufferedImage bufferedImage = ... // your BufferedImage source
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(bufferedImage);
    }


    // in a configuration class

    @Bean
    public HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
    ```

- Serialize the `BufferedImage` into a byte array and return that array from your request handler method. For serialization, you can use the following method:
    ```java
    @GetMapping(path = "/api/qrcode")
    public ResponseEntity<byte[]> getImage() throws IOException {
        BufferedImage bufferedImage = ... // your BufferedImage source

        try (var baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", baos); // writing the image in the PNG format
            byte[] bytes = baos.toByteArray();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(bytes);
        } catch (IOException e) {
            // handle the IOEexception
        }
    }
    ```

> [!NOTE]
> It's important to programmatically create the image, as image editors can embed metadata in the image file, which could cause tests to fail.

### Objectives

In this stage, you should:

- Modify the `GET /api/qrcode` to return the status code `200 OK` and an image as the response body. The image must be a white square, 250x250 pixels, in PNG format.
- Set the correct `Content-Type` header of the response to indicate that the content type is `image/png`.

### Examples

**Example 1.** *a GET request to /api/health:*

*Response:* `200 OK`

**Example 2.** *a GET request to /api/qrcode:*

*Response code:* `200 OK`

*Response header:* `Content-Type: image/png`

*Response body:* byte array


## Stage 3/5: Image parameters

### Description

In this stage, your task is to add versatility to the QR code service by allowing it to handle different request parameters. This will enable the generation of QR code images with various sizes and formats, as opposed to the same image every time.

Clients may need images in different formats and sizes, which requires the modification of the `GET /api/qrcode` endpoint to accept two request parameters:

- `size`: this parameter, measured in pixels, should be within a reasonable range. If the value is less than 150 pixels or exceeds 350 pixels, the endpoint should respond with the status code `400 BAD REQUEST`, accompanied by a JSON containing the `error` field and a suitable error message.

- `type`: the image can be in *PNG*, *JPEG*, or *GIF* format. The endpoint should return the generated image in the requested format, including setting the appropriate `Content-Type` header. If the parameter value doesn't match these three formats, the endpoint should respond with the status code `400 BAD REQUEST` and a JSON containing the `error` field and a suitable error message.

Both parameters are mandatory. However, you don't need to account for missing parameters; the default Spring Boot behavior will suffice.

### Objectives

- Modify the `GET /api/qrcode` endpoint to accept the `size` request parameter. If the parameter value ranges between 150 and 350 pixels (inclusive), return the status code `200 OK` and the image of the requested size. Bear in mind that QR code images are square! If the parameter value is outside this range, return the status code `400 BAD REQUEST` along with a JSON-formatted request body containing the following:
    ```json
    {
      "error": "Image size must be between 150 and 350 pixels"
    }
    ```
- Modify the `GET /api/qrcode` endpoint to accept the `type` request parameter. If the parameter value is either `png`, `jpeg`, or `gif`, return the status code `200 OK` and the image in the requested format. Don't forget to set the correct `Content-Type` header in the response! If the parameter value doesn't match any of the three accepted types, return the status code `400 BAD REQUEST`, along with a JSON-formatted request body containing the following:
    ```json
    {
        "error": "Only png, jpeg and gif image types are supported"
    }
    ```
- If both the `size` and the `type` parameters are invalid, return the error message related to the `size` parameter.
- Aside from these modifications, the image generation and serialization process remains unchanged.

### Examples

**Example 1**. *a GET request to /api/qrcode with the correct size and type parameters:*

*Request:* `GET /api/qrcode?size=250&type=png`

*Response code:* `200 OK`

*Response header:* `Content-Type: image/png`

*Response body:* byte array

**Example 2.** *a GET request to /api/qrcode with an incorrect size parameter:*

*Request:* `GET /api/qrcode?size=100&type=png`

*Response code:* `400 BAD REQUEST`

*Response body:*
```json
{
    "error": "Image size must be between 150 and 350 pixels"
}
```

**Example 3.** *a GET request to /api/qrcode with an incorrect type parameter:*

*Request:* `GET /api/qrcode?size=250&type=tiff`

*Response code:* `400 BAD REQUEST`

*Response body:*
```json
{
    "error": "Only png, jpeg and gif image types are supported"
}
```
