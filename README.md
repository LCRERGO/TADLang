# TADL
------

The TAsk Description Language is language for specifying tasks during the day of a user.

## Dependencies

To build the TADL compiler it is necessary to have antlr and java installed:
- Maven (>= 3.6.3)
- JDK (>= 11.0.16)
- Antlr (>= 4.10.1)

## Build

To build the TADL compiler simply run maven and a jar archive with all the dependencies 
will be generated on the target directory:  
```bash
mvn clean package
```

## How to run

To run TADL simply invoke the command with the name of the file that contains the task
specification:  
```bash
java -jar TADL-1.0-SNAPSHOT-jar-with-dependencies.jar <filename>
```

The result will be on a file named `out.html`. It is also possible to change
the name of the output file with the `-output` handle. To get more help on TADL
compiler utilization use the `-help` handle.

## TADL syntax
TADL syntax follows a LISP-like syntax so it's totally possible to parse a TADL file
in a Lisp environment. The simplest way to describe a task schedule is as follows:  
```lisp
(schedule sch-name
    (task task-name "description" "place" yy/mm/yyyy category))
```

More examples can be found in the examples directory.

## Information
Built by Lucas Cruz dos Reis (A.K.A. Dante Frostbyte) in 2022.