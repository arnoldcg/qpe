# Practical exersice Quickbase Documentation -- Arnold Castroman Garcia

# How to use the Solution 

## Project structure
```
>gradle  
>lib                                          -- Folder where are located the local dependencies  
>output                                       -- Folder where will be store the output of the project
>resources                                    -- Folder where is stored the current SQLite database
>src                                          -- Folder that contains the main code
.gitignore
.project
build.gradle
docker-compose.yml                            -- File used in collaboration with Dockerfile file
Dockerfile                                    -- Contains the definition of the docker service
gradlew
gradlew.bat
README.md
README_HOW_TO_USE.md                          -- Instructions about how to test the solution
settings.gradle
```

## Intellij Idea + Java Application Configuration
One of the options to use the solution is to configure the IDE to run a Java Application

### Prerequisites
1. Java 8 installed on the environment (temurin-1.8 preferebly) 
2. JAVA_HOME configured in the system or in Intellij
3. Go to Intellij Idea Run/Debug Configurations 
4. Add Application in the plus symbol
5. Run the project using as Main class **"com.quickbase.Main"** and -cp **"dev-interview-materials.main"**
6. Compile the project using the Gradle wrapper
7. Execute the solution


## Using Docker + Docker Compose
1. Install docker and docker-compose in the computer
2. In the root of the project execute the command 
```cmd
    {PROJECT_FOLDER}\qpe> docker-compose up
```
3. Check the output in the console and in the output folder in the project. 

## Expected Output
All the output produced by the project will be stored in the **output** directory

