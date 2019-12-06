# CPSC 433: AI Project
# Members:
# Ben Cook, Caelum Sloan, Devon Hockley, Zahra Ghavasieh, Patrick Abou Gharib


## Usage
Run ./gradlew \<args> from the commandline 

Must have Java 8 or greater installed.

On Mac OS and Linux,  gradlew must be made executable. For example, chmod +x gradlew.


## Args

Arguements: [\<file\> [\<w_minfilled\> \<w_pref\> \<w_pair\> \<w_secdiff\>] [\<pen_coursemin> \<pen_labsmin> \<pen_section> \<pen_notpaired>] [\<time(MIN)>] [\<#threads(OPT)>]]

Number of Threads > 0


## Example

run "deptinst1.txt"
weights = 1
penalties = 1
time = 2 minutes
number of threads = 16 (this is optional)

./gradlew run --args "deptinst1.txt 1 1 1 1 1 1 1 1 2 16"

In order to provide custom weights a file must be specified. Default weights are 1,1,1,1.


## Tasks
./gradlew build - builds the source code

./gradlew run - build and runs the source code

./gradlew run --args "\<arg1\> \<arg2\> ...." - builds and runs the source code, providing the given arguements to the program. Example: ./gradlew run --args "deptinst1.txt 1 1 0 1"

./gradlew distZip - build and zips a runnable distribution under build\distributions

## Running Distributions
Unzip the distribution, then go into the bin folder and run the script appropriate for your platform.

## All-in-one/"Fat" Jars
Fat jars include all dependencies and are runnable on their own as self contained programs. Still need Java 8 of course, but it includes all libraries.
