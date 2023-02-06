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