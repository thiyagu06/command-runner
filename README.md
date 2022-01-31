# Command Runner

This project is a command line application to run your shell commands for simple workflows to more complex workflows on
your local computer. Command Runner pipelines are written in YAML format. This project is inspired from the concept
[circleci](https://circleci.com/) and [github actions](https://docs.github.com/en/actions).

### Use cases

* Command Runner can be used for setting up your [gcloud.yaml](samples/gcloud.yaml) local development environment.
  Instead of writing a long how-to-setup guides for your new team members, define a set of commands needs to be executed
  in single YAML and use Command runner to define the steps in the YAML file. You can control your local environment by
  creating pipeline based on needs

* Command Runner also provides a way to teardown your [version.yaml](samples/docker.yaml) local environment the same way
  you have done for setup. Just define your shell commands to removing your installation in the tearDown stage of your
  pipeline. easy, isn't it?

_**Warning:** sample pipelines are not fully tested. It's for providing some thoughts to user on how we can perform
various day-to-day tasks using command runner._

_**Warning:** native executables are tested only macos._

### Usage

### Available commands

Display the help to learn about the CLI

```shell script
Usage: command-runner [-hV] [COMMAND]
CLI for executing shell commands
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  pipeline  execute pipeline
```

Run the specific workflow in pipeline

```shell script
command-runner pipeline -f <aboslute path to your pipeline yaml> -w <workflow name>
```

Run the specific step in your pipeline

```shell script
command-runner pipeline -f <aboslute path to your pipeline yaml> -w <workflow name> -s <step name>
```

Disable printing output of each step execution

```shell script
command-runner pipeline -f <aboslute path to your pipeline yaml> -w <workflow name> -s <step name> --no-p
```

### Development

This project uses Quarkus, the Supersonic Subatomic Java Framework.

To build the project, make sure to have Java 17 and GraalVM 21.3 or newer installed. The following commands are commonly
used:

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

It produces the quarkus-run.jar file in the build/quarkus-app/ directory.

The application is now runnable using java -jar build/quarkus-app/quarkus-run.jar. You should define an alias
command-runner now:

```shell script
alias command-runner="java -jar target/quarkus-app/quarkus-run.jar"
```

To seed the command line arguments, pass the -Dquarkus.args option:

```shell script
 ./gradlew quarkusDev -Dquarkus.args='--help'
```

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.package.type=native
```

You can then execute your native executable with: `./build/command-runner-1.0.0-runner`

## Releasing new version

Github action is setup for releasing new artifacts. create a new release from  `releases` tab and native os executable will be built and uploaded by the GH action.



## Related Guides

- Picocli ([guide](https://quarkus.io/guides/picocli)): Develop command line applications with Picocli
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin
