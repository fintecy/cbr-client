[![Build](https://github.com/fintecy/cbr-client/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/fintecy/cbr-client/actions/workflows/gradle.yml)

# Central Bank of Russia (CBR) Client

Java client based on new HttpClient (java 11+)

## Usage
### Simple client creation
```
CbrApi client = CbrClient.api();
```
### Complex client configuration
```
var client = cbrClient()
    .useClient(HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .priority(10)
        .connectTimeout(Duration.ofMillis(500))
        .executor(Executors.newSingleThreadExecutor())
        .build())
    .with(CircuitBreaker.ofDefaults())
    .with(RateLimiter.smoothBuilder(Duration.ofMillis(100))
        .build())
    .with(RetryPolicy.ofDefaults())
    .with(Timeout.of(Duration.ofMillis(400)))
    .rootPath("https://www.cbr.ru/scripts") -- just to use stub in tests
    .build();
```

### Get latest rates
```
var client = CbrClient.api();
int rates = client.rates();
```

### Get supported currencies
```
var client = CbrClient.api();
var ccurrencies = client.supportedCurrencies();
```

## Dependencies
- Java 11+
- FailSafe
- Slf4j api
- Jaxb (api)
- WireMock (tests)
- Junit5 (tests)

## Author
Anton Batiaev <anton@batiaev.com>
