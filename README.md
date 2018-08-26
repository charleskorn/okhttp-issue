This repository demonstrates an issue in OkHttp where closing unfinished chunked responses retrieved over a Unix domain socket on Linux takes an unexpectedly long time.

See [https://github.com/square/okhttp/issues/4233](https://github.com/square/okhttp/issues/4233) for more information.

# Requirements

* JDK 1.8

# Running

1. Clone this repository
2. Run `./gradlew run`

# Sample output

## OS X

```
2018-08-26T01:56:51.840+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Running scenario ChunkedWithoutEndMarker
2018-08-26T01:56:52.381+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] starting to accept connections
2018-08-26T01:56:52.395+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Sending request...
2018-08-26T01:56:52.399+1000 INFO okhttp3.OkHttpClient --> GET http://localhost:1/test
2018-08-26T01:56:52.399+1000 INFO okhttp3.OkHttpClient --> END GET
2018-08-26T01:56:52.429+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] received request: GET /test HTTP/1.1 and responded: HTTP/1.1 200 OK
2018-08-26T01:56:52.431+1000 INFO okhttp3.OkHttpClient <-- 200 OK http://localhost:1/test (31ms)
2018-08-26T01:56:52.431+1000 INFO okhttp3.OkHttpClient Transfer-encoding: chunked
2018-08-26T01:56:52.431+1000 INFO okhttp3.OkHttpClient <-- END HTTP
2018-08-26T01:56:52.432+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Received response.
2018-08-26T01:56:52.432+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Cleaning up...
2018-08-26T01:56:52.538+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Done! Cleanup took 105 ms.
2018-08-26T01:56:52.538+1000 WARNING okhttp3.mockwebserver.MockWebServer MockWebServer[1] failed unexpectedly
java.io.IOException: accept failed: Software caused connection abort
        at jnr.unixsocket.UnixServerSocketChannel.accept(UnixServerSocketChannel.java:61)
        at com.charleskorn.okhttpissue.UnixDomainServerSocketFactory$UnixDomainServerSocket.accept(UnixDomainServerSocketFactory.java:85)
        at okhttp3.mockwebserver.MockWebServer$2.acceptConnections(MockWebServer.java:393)
        at okhttp3.mockwebserver.MockWebServer$2.execute(MockWebServer.java:370)
        at okhttp3.internal.NamedRunnable.run(NamedRunnable.java:32)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

2018-08-26T01:56:52.539+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Running scenario ChunkedWithEndMarker
2018-08-26T01:56:52.540+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] starting to accept connections
2018-08-26T01:56:52.542+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Sending request...
2018-08-26T01:56:52.543+1000 INFO okhttp3.OkHttpClient --> GET http://localhost:1/test
2018-08-26T01:56:52.543+1000 INFO okhttp3.OkHttpClient --> END GET
2018-08-26T01:56:52.544+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] received request: GET /test HTTP/1.1 and responded: HTTP/1.1 200 OK
2018-08-26T01:56:52.544+1000 INFO okhttp3.OkHttpClient <-- 200 OK http://localhost:1/test (1ms)
2018-08-26T01:56:52.545+1000 INFO okhttp3.OkHttpClient Transfer-encoding: chunked
2018-08-26T01:56:52.545+1000 INFO okhttp3.OkHttpClient <-- END HTTP
2018-08-26T01:56:52.545+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Received response.
2018-08-26T01:56:52.546+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Cleaning up...
2018-08-26T01:56:52.546+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Done! Cleanup took 0 ms.
2018-08-26T01:56:52.546+1000 WARNING okhttp3.mockwebserver.MockWebServer MockWebServer[1] failed unexpectedly
java.io.IOException: accept failed: Software caused connection abort
        at jnr.unixsocket.UnixServerSocketChannel.accept(UnixServerSocketChannel.java:61)
        at com.charleskorn.okhttpissue.UnixDomainServerSocketFactory$UnixDomainServerSocket.accept(UnixDomainServerSocketFactory.java:85)
        at okhttp3.mockwebserver.MockWebServer$2.acceptConnections(MockWebServer.java:393)
        at okhttp3.mockwebserver.MockWebServer$2.execute(MockWebServer.java:370)
        at okhttp3.internal.NamedRunnable.run(NamedRunnable.java:32)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

2018-08-26T01:56:52.547+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Running scenario Normal
2018-08-26T01:56:52.548+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] starting to accept connections
2018-08-26T01:56:52.550+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Sending request...
2018-08-26T01:56:52.550+1000 INFO okhttp3.OkHttpClient --> GET http://localhost:1/test
2018-08-26T01:56:52.551+1000 INFO okhttp3.OkHttpClient --> END GET
2018-08-26T01:56:52.552+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] received request: GET /test HTTP/1.1 and responded: HTTP/1.1 200 OK
2018-08-26T01:56:52.552+1000 INFO okhttp3.OkHttpClient <-- 200 OK http://localhost:1/test (1ms)
2018-08-26T01:56:52.553+1000 INFO okhttp3.OkHttpClient Content-Length: 5
2018-08-26T01:56:52.553+1000 INFO okhttp3.OkHttpClient <-- END HTTP
2018-08-26T01:56:52.553+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Received response.
2018-08-26T01:56:52.553+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Cleaning up...
2018-08-26T01:56:52.553+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Done! Cleanup took 0 ms.
2018-08-26T01:56:52.554+1000 WARNING okhttp3.mockwebserver.MockWebServer MockWebServer[1] failed unexpectedly
java.io.IOException: accept failed: Software caused connection abort
        at jnr.unixsocket.UnixServerSocketChannel.accept(UnixServerSocketChannel.java:61)
        at com.charleskorn.okhttpissue.UnixDomainServerSocketFactory$UnixDomainServerSocket.accept(UnixDomainServerSocketFactory.java:85)
        at okhttp3.mockwebserver.MockWebServer$2.acceptConnections(MockWebServer.java:393)
        at okhttp3.mockwebserver.MockWebServer$2.execute(MockWebServer.java:370)
        at okhttp3.internal.NamedRunnable.run(NamedRunnable.java:32)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)
```

## Linux

```
2018-08-26T01:57:10.649+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Running scenario ChunkedWithoutEndMarker
2018-08-26T01:57:11.486+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] starting to accept connections
2018-08-26T01:57:11.506+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Sending request...
2018-08-26T01:57:11.522+1000 INFO okhttp3.OkHttpClient --> GET http://localhost:1/test
2018-08-26T01:57:11.522+1000 INFO okhttp3.OkHttpClient --> END GET
2018-08-26T01:57:11.604+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] received request: GET /test HTTP/1.1 and responded: HTTP/1.1 200 OK
2018-08-26T01:57:11.611+1000 INFO okhttp3.OkHttpClient <-- 200 OK http://localhost:1/test (88ms)
2018-08-26T01:57:11.611+1000 INFO okhttp3.OkHttpClient Transfer-encoding: chunked
2018-08-26T01:57:11.611+1000 INFO okhttp3.OkHttpClient <-- END HTTP
2018-08-26T01:57:11.611+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Received response.
2018-08-26T01:57:11.612+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Cleaning up...
2018-08-26T01:57:14.626+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Done! Cleanup took 3013 ms.
2018-08-26T01:57:19.639+1000 SEVERE com.charleskorn.okhttpissue.ClientAndServer Exception thrown during test
java.io.IOException: Gave up waiting for executor to shut down
        at okhttp3.mockwebserver.MockWebServer.shutdown(MockWebServer.java:421)
        at okhttp3.mockwebserver.MockWebServer.close(MockWebServer.java:850)
        at com.charleskorn.okhttpissue.ClientAndServer.run(ClientAndServer.java:68)
        at com.charleskorn.okhttpissue.Application.main(Application.java:16)

2018-08-26T01:57:19.640+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Running scenario ChunkedWithEndMarker
2018-08-26T01:57:19.642+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] starting to accept connections
2018-08-26T01:57:19.648+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Sending request...
2018-08-26T01:57:19.649+1000 INFO okhttp3.OkHttpClient --> GET http://localhost:1/test
2018-08-26T01:57:19.649+1000 INFO okhttp3.OkHttpClient --> END GET
2018-08-26T01:57:19.653+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] received request: GET /test HTTP/1.1 and responded: HTTP/1.1 200 OK
2018-08-26T01:57:19.653+1000 INFO okhttp3.OkHttpClient <-- 200 OK http://localhost:1/test (4ms)
2018-08-26T01:57:19.654+1000 INFO okhttp3.OkHttpClient Transfer-encoding: chunked
2018-08-26T01:57:19.655+1000 INFO okhttp3.OkHttpClient <-- END HTTP
2018-08-26T01:57:19.655+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Received response.
2018-08-26T01:57:19.655+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Cleaning up...
2018-08-26T01:57:19.656+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Done! Cleanup took 0 ms.
2018-08-26T01:57:24.658+1000 SEVERE com.charleskorn.okhttpissue.ClientAndServer Exception thrown during test
java.io.IOException: Gave up waiting for executor to shut down
        at okhttp3.mockwebserver.MockWebServer.shutdown(MockWebServer.java:421)
        at okhttp3.mockwebserver.MockWebServer.close(MockWebServer.java:850)
        at com.charleskorn.okhttpissue.ClientAndServer.run(ClientAndServer.java:68)
        at com.charleskorn.okhttpissue.Application.main(Application.java:17)

2018-08-26T01:57:24.659+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Running scenario Normal
2018-08-26T01:57:24.663+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] starting to accept connections
2018-08-26T01:57:24.665+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Sending request...
2018-08-26T01:57:24.665+1000 INFO okhttp3.OkHttpClient --> GET http://localhost:1/test
2018-08-26T01:57:24.665+1000 INFO okhttp3.OkHttpClient --> END GET
2018-08-26T01:57:24.668+1000 INFO okhttp3.mockwebserver.MockWebServer MockWebServer[1] received request: GET /test HTTP/1.1 and responded: HTTP/1.1 200 OK
2018-08-26T01:57:24.671+1000 INFO okhttp3.OkHttpClient <-- 200 OK http://localhost:1/test (5ms)
2018-08-26T01:57:24.672+1000 INFO okhttp3.OkHttpClient Content-Length: 5
2018-08-26T01:57:24.672+1000 INFO okhttp3.OkHttpClient <-- END HTTP
2018-08-26T01:57:24.672+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Received response.
2018-08-26T01:57:24.672+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Cleaning up...
2018-08-26T01:57:24.673+1000 INFO com.charleskorn.okhttpissue.ClientAndServer Done! Cleanup took 0 ms.
2018-08-26T01:57:29.673+1000 SEVERE com.charleskorn.okhttpissue.ClientAndServer Exception thrown during test
java.io.IOException: Gave up waiting for executor to shut down
        at okhttp3.mockwebserver.MockWebServer.shutdown(MockWebServer.java:421)
        at okhttp3.mockwebserver.MockWebServer.close(MockWebServer.java:850)
        at com.charleskorn.okhttpissue.ClientAndServer.run(ClientAndServer.java:68)
        at com.charleskorn.okhttpissue.Application.main(Application.java:18)

There is more than one foreground thread still running:
1: main
10: MockWebServer 1
11: MockWebServer
14: MockWebServer 1
16: MockWebServer [family=PF_UNIX path=]
17: MockWebServer 1
19: MockWebServer [family=PF_UNIX path=]

These will be forcibly terminated.
```
