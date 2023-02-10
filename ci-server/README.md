# CI-Server 

The Continuous integration server. 
Src contains core functionality and its tests. 
testing_resources contains auxiliary resources necessary for the tests to be preformed. 
compileFail.txt, testFail.txt, testSuccess.txt are files containing sample outputs from 
building/testing maven projects.

## Folder Structure

---

root
│
├── README.md
├── src
│   ├── main
│   │   └── ...
│   │       ├── ContinuousIntegrationServer.java
│   │       ├── FileParsingFailedException.java    
│   │       ├── Output_parser.java        
│   │       └── Status.java           
│   │          
│   └── test
│       └── ...
│           ├── AppTest.java
│           └── Output_parserTest.java            
│
├── testing_resources
│   ├── compileFail.txt
│   ├── maven_project_for_unit_testing
│   ├── tempUnitTestFile
│   ├── testFail.txt
│   └── testSuccess.txt
...

---