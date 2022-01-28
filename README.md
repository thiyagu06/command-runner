# Command Runner

_**Warning:** Development is in progress._

This project is a command line application to run your shell commands for simple workflows to more complex workflows on
your local computer. Command Runner pipelines are written in YAML format. This project is inspired from the concept
of [terraform](https://www.terraform.io/) and [circleci](https://circleci.com/).

### Use cases

* Command Runner can be used for setting up your local environment as the same way
  as [terraform](https://www.terraform.io/) is used for setting up your cloud VMs. Instead of writing a long
  how-to-setup guides for your new team members, define a set of commands needs to be executed in single YAML and use
  Command runner to define the steps in the YAML file.

* Command Runner also provides a way to teardown your local environment the same way you have done for setup. Just
  define your shell commands to removing your installation in the tearDown stage of your pipeline. easy, isn't it?

### Usage

### Available commands

Display the help to learn about the CLI

```shell script
command-runner -- help
Usage: command-runner [-hV] [COMMAND]
CLI for executing shell commands
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  setup     runs setup stages in pipeline Yaml.
  teardown  runs teardown stage in pipeline Yaml.
```

Run the setup stage of your pipeline

```shell script
command-runner setup -f <aboslute path to your pipeline yaml>
```

Run the setup stage of your pipeline

```shell script
command-runner teardown -f <aboslute path to your pipeline yaml>
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

To seed the command line arguments, pass the -Dquarkus.args option:

```shell script
 ./gradlew quarkusDev -Dquarkus.args='--help'
```

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.package.type=native
```

You can then execute your native executable with: `./build/command-runner-1.0.0-SNAPSHOT-runner`

## Related Guides

- Picocli ([guide](https://quarkus.io/guides/picocli)): Develop command line applications with Picocli
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin

## TODO

- [x] ability to run specific step in the stage
- [x] redirect command output to console based on CLI options.
- [ ] ability to specify regex to verify command output
- [ ] add tests
- [ ] GitHub action to create release for native executables
- [ ] improve README to how to add alias for command runner
- [ ] create sample pipelines
