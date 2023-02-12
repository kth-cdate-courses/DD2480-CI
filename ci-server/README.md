# CI-Server 

The Continuous integration server. 
Src contains core functionality and its tests. 
testing_resources contains auxiliary resources necessary for the tests to be preformed. 
compileFail.txt, testFail.txt, testSuccess.txt are files containing sample outputs from 
building/testing maven projects.

## Folder Structure

```
root
│
├── README.md
├── src
│   ├── main
│   │   └── ...
│   │       ├── Commit.java
│   │       ├── ContinuousIntegrationServer.java
│   │       ├── DownloadFailedException.java
│   │       ├── FileParsingFailedException.java    
│   │       ├── Output_parser.java
│   │       ├── RequestExtraction.java
│   │       └── Status.java           
│   │          
│   └── test
│       └── ...
│           ├── ContinuousIntegrationServerTest.java
│           ├── Output_parserTest.java
│           └── RequestExtractionText.java
│
├── testing_resources
│   ├── compileFail.txt
│   ├── json_unit_testing
│   ├── └──...
│   ├── maven_project_for_unit_testing
│   ├── └──...
│   ├── tempUnitTestFile
│   ├── testFail.txt
│   └── testSuccess.txt
...
```
