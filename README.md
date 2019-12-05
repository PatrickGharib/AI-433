# AI Project
## Usage
Run ./gradlew \<task> from the commandline 

Must have Java 8 or greater installed.

On Mac OS and Linux,  gradlew must be made executable. For example, chmod +x gradlew.

## Args

Arguements: [\<file\> [\<pen_\> \<pen_\> \<pen_\> \<pen_\>]] 

In order to provide custom weights a file must be specified. Default weights are 1,1,1,1.

## Tasks
./gradlew build - builds the source code

./gradlew run - build and runs the source code

./gradlew run --args "\<arg1\> \<arg2\> ...." - builds and runs the source code, providing the given arguements to the program.

./gradlew distZip - build and zips a runnable distribution under build\distributions

## Running Distributions
Unzip the distribution, then go into the bin folder and run the script appropriate for your platform.

## All-in-one/"Fat" Jars
Fat jars include all dependencies and are runnable on their own as self contained programs. Still need Java 8 of course, but it includes all libraries.

WIP, I've done it before just needs more configuration
