# CI assignment
## Members
- Edward
- Erik
- Hampus
- Pauline

## Project 2: Continuous Integration
In this assignment a CI server has to be created and connected to this github repository to automatically run tests and report them to commits and pull requests. The status of each commit also has to be reported to a status site with all of the CI information.

## Folder structure
```
root  
├── README.md  
├── ci-server  
│   ├── src  
|   |   └── ...
|   |       ├── Decide.java
|   |       ├── InitialSettings.java
|   |       ├── LogicalOperator.java
|   |       └── Point.java
│   ├── tests  
│   └── README.md  
└── status-site  
    ├── src  
    |   └── ...
    └── README.md  
```
## Statement of contributions
The work was split up as evenly as possible for each team member. We started of the project by deciding on a basic structure.
During this process, we thought out which functionality we needed to implement, and how to divide the functionality into separate methods. The work was then
split up as follows:

* Edward: wrote the method and test for scanning files that contain the console output messages when building/testing maven projects. Also worked on README files.
* Erik: functionality for building and running tests on downloading maven projects. Also wrote corresponding tests. Also spend alot of time helping other group members.
* Hampus: wrote the functionality for- and designed the web-application that displays the results of build/testing. Also wrote functionality for interacting with Github APIs.
* Pauline: functionality for cloning repositories from github to the ci-server, and corresponding tests.

We also spent a good amount of time pair programming, which is reflected in commits with co-authors.

## Use project, compile, test and run

## Essence: assessment of our Way of Working

### Conventions (suggested)
Branch namings: `<name>/<type>/<issue-number>-<descriptive branch name>`  
Example: `sven/fix/1-change-color-of-button`


Commits follow: https://www.conventionalcommits.org/en/v1.0.0/  
| Where | Naming Convention |
| -- | -- |
| Variables | camelCase |
| functions/methods | camelCase |
| constants | CONSTANTS |
| classes | PascalCase |