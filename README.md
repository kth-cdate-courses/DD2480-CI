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
* Erik: functionality for building and running tests on downloading maven projects. Also wrote corresponding tests. Also spend a lot of time helping other group members.
* Hampus: wrote the functionality for- and designed the web-application that displays the results of build/testing. Also wrote functionality for interacting with Github APIs.
* Pauline: functionality for cloning repositories from github to the ci-server, and corresponding tests.

We also spent a good amount of time pair programming and gluing the project together in the end, which is reflected in commits with co-authors.

## Use project, compile, test and run

## Essence: assessment of our Team

We feel that we have reached the Collaboring state, because we have identified the frame in which we work as a team and the external collaborators we work with, we know each other's competencies and preferences in the way of working. We feel that we trust each other, although it has only been two weeks since we started working as a team so we still have a lot to learn about each other. 
We have very clear governance rules because we decided that we did not want to have a group leader but rather all take the same role in the team, with the common responsability of participating in group meetings, of carrying one's agreed-upon part of the project, of helping each other, asking questions when needed and stay aware of potentiel issues with the project and our way of working. We also have clear communication channels with a discord discussion, online meetings to synchronize our tasks regularly, and meetings in real life.
We feel that we managed to create an open communication in the sense that we are always aware of how everyone's status in terms of their tasks and their availability, we give feedback to each other through code reviews, and we are not afraid to ask our teammates for help when we need it.

We don't think we have reached the Performing state yet, because we are still a very young team, for instance we cannot say that we fulfill our tasks consistently since it has only been two projects. We still ask for help on our projects, but we also identified many problems during the development of our CI-server and we managed to solve many of them together. One thing that we should probably work on as a team is to identify wasted effort, because we didn't take the time to reflect on that during our work on this project.

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