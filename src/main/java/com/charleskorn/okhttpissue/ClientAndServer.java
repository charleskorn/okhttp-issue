/*
 * Copyright (C) 2018 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.charleskorn.okhttpissue;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ClientAndServer {
    private static final Logger logger = Logger.getLogger(ClientAndServer.class.getName());

    public void run(boolean chunkedRequest) throws Exception {
        logger.info("Running with chunkedRequest = " + chunkedRequest);

        File socketFile = new File("/tmp/ClientAndServer.sock");
        socketFile.delete();
        socketFile.deleteOnExit();

        try (MockWebServer server = new MockWebServer()) {
            server.setServerSocketFactory(new UnixDomainServerSocketFactory(socketFile));

            String body = "hello";

            if (chunkedRequest) {
                Buffer bytes = new Buffer();
                bytes.writeHexadecimalUnsignedLong(body.length());
                bytes.writeUtf8("\r\n");
                bytes.writeUtf8(body);
                bytes.writeUtf8("\r\n");

                // Normally, to end a chunked body, you would write '0\r\n\r\n' to signal the end of the body, but this won't come in the case of streaming events from a server
                // (eg. the Docker events API)

                server.enqueue(new MockResponse().setBody(bytes).removeHeader("Content-Length").addHeader("Transfer-encoding: chunked"));
            } else {
                server.enqueue(new MockResponse().setBody(body));
            }

            server.start();

            OkHttpClient client = makeClient(socketFile);

            Request request = new Request.Builder()
                    .url(server.url("/test"))
                    .build();

            logger.info("Sending request...");

            Long cleanupStartTime;

            try (Response response = client.newCall(request).execute()) {
                logger.info("Received response.");

                logger.info("Cleaning up...");
                cleanupStartTime = System.nanoTime();
            }

            Long cleanupFinishTime = System.nanoTime();
            Duration cleanupDuration = Duration.ofNanos(cleanupFinishTime - cleanupStartTime);

            logger.info("Done! Cleanup took " + cleanupDuration.toMillis() + " ms.");
        }
    }

    private OkHttpClient makeClient(File socketFile) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient.Builder()
                .socketFactory(new UnixDomainSocketFactory(socketFile))
                .addInterceptor(logging)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
    }
}
