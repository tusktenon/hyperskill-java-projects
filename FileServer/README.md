# Running the File Server Project

Most of the projects in this repo use the Gradle [Application Plugin](https://docs.gradle.org/current/userguide/application_plugin.html), and can built and then started with the `gradle run` command. Because the File Server project involves separate client and server programs (that are meant to be run simultaneously in separate terminals), building and starting it is not so straightforward. 

I've organized the source files into multiple packages:

- `stage1` contains the storage emulator program developed during Stage 1;
- `stageX.client` and `stageX.server` (where `X` is 2 or 3) contain the client and server programs for the later stages of the project.

I've written custom Gradle tasks for each of these, so they can be built and run with `gradle runStage1`, `gradle runClientX` and `gradle runServerX`. Alternatively, you can just use `gradle build` to compile all the source files, then navigate to the `build/classes/java/main` directory and run `java stage1.Main`, `java stageX.client.Main` or `java stageX.server.Main`.
