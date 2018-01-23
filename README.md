RC-Extensions

This repository does not include a complete development environment due to the dependency on the FTC SDK (https://github.com/ftctechnh/ftc_app).

To set up a full environment, follow these instructions:

1) Clone the ftc_app repository (we suggest a shallow clone, i.e. `git clone https://github.com/ftctechnh/ftc_app --depth 1` because we have no need of the ftc_app history)
2) Inside the ftc_app directory, clone RC-Extensions (`git clone https://github.com/OpenFTC/RC-Extensions`)
3) Open the ftc_app folder in Android Studio
4) After a full sync completes (including any applicable Gradle updates and installations), add `include ':RC-Extensions'` to a new line in settings.gradle.
5) Sync