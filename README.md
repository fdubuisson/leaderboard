Simple leaderboard API to track user scores on a tournament. Based on MongoDB, [ktor](https://ktor.io) and [koin](https://insert-koin.io/).

# Requirements

- docker
- docker-compose

# Building and running

Quick run:

```
./start.sh
```

Or detailed workflow:

```
docker-compose -f dep/docker-compose.yml up -d
./gradlew build run
```

A Postman collection is available at project root with all API definitions.

# Development

Code style is enforced using the `ktlint` plugin:

```
./gradlew ktlinkCheck
```
