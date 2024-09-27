# Quarkus DAT/CSV Serialization and Validation Service

This project is a Quarkus-based service designed to handle the serialization and validation of DAT/CSV files. It saves
valid records to a selected database.

## Running the application in dev mode

You can run your application in dev mode, which enables live coding, using:

```shell
./mvnw compile quarkus:dev
```

> **_NOTE:_** Quarkus ships with a Dev UI, available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell
./mvnw package
```

This produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory. Note that it’s not a _über-jar_ as the
dependencies are copied into the `target/quarkus-app/lib/` directory.

You can run the application using:

```shell
java -jar target/quarkus-app/quarkus-run.jar
```

To build a _über-jar_, execute the following command:

```shell
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as a _über-jar_, can be run using:

```shell
java -jar target/quarkus-example-0.0.1-runner.jar
```

## Creating a native executable

### For Docker

You can create a native executable for Docker using:

```shell
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Then, build the Docker image:

```shell
docker build -f src/main/docker/Dockerfile.native -t quarkus-example .
```

Run the Docker container:

```shell
docker run -i --rm -p 8080:8080 quarkus-example
```

### For Windows

To create a native executable for Windows, ensure you have GraalVM installed and configured. Then, use:

```shell
./mvnw package -Pnative
```

Another solution is to use Microsoft Native Tools for Visual Studio, that needs to be initialized before packaging, as
documented in
the [Quarkus official guide to build a native image](https://quarkus.io/guides/building-native-image#producing-a-native-executable).

```cmd
cmd /c 'call "C:\Program Files (x86)\Microsoft Visual Studio\2017\BuildTools\VC\Auxiliary\Build\vcvars64.bat" && mvn package -Dnative'
```

You can then execute your native executable with:

```shell
./target/quarkus-example-0.0.1-runner.exe
```

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and
  Vert.x.
- REST JSON-B ([guide](https://quarkus.io/guides/rest#json-serialisation)): JSON-B serialization support for Quarkus
  REST.
- JDBC Driver - H2 ([guide](https://quarkus.io/guides/datasource)): Connect to the H2 database via JDBC.
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code
  for Hibernate ORM via the active record or the repository pattern.