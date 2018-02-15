[![Discord](https://img.shields.io/discord/377144270034829324.svg?style=for-the-badge)](https://discord.gg/ameFTnC)

# Rev Extensions

This repository contains extensions for the Rev Expansion Hub, adding functionality not exposed in the core SDK.

#### NOTE: This repository does not include a complete development environment due to the dependency on the FTC SDK (https://github.com/ftctechnh/ftc_app).

## Installation/Download Instructions:

1) Clone the ftc_app repository (we suggest a shallow clone, i.e. `git clone https://github.com/ftctechnh/ftc_app --depth 1` because we have no need of the ftc_app history)

    If you already have a copy of ftc_app or OpenRC, no need to repeat this step.
2) Inside the ftc_app directory, clone RevExtensions (`git clone https://github.com/OpenFTC/RevExtensions`)

    If you don't wish to use Git, you may instead download a copy of this repository and extract it as a folder.
3) Open the ftc_app folder in Android Studio
4) After a full sync completes (including any applicable Gradle updates and installations), add `include ':RevExtensions'` to a new line in settings.gradle.
5) Sync

## Usage

The `OpenRevDcMotorImplEx` class retains all features of normal motors, including the `DcMotorImpl` and `DcMotorEx` extensions, while also adding current sensing capabilities. To use, declare an `OpenRevDcMotorImplEx` as such:

```java
OpenRevDcMotorImplEx motor1 = new OpenRevDcMotorImplEx((DcMotorImplEx) hardwareMap.dcMotor.get("motor1"));`
```

Then, you may use this motor as normal. Current draw, as a `double`, can be accessed through:

```java
motor1.getCurrentDraw();
```

Similarly, you may access extra Rev Expansion Hub capabilities as follows (Take note that Rev Expansion Hubs are called "Lynx Modules" internally to the FTC SDK):

```java
OpenRevHub expansionHub = new OpenRevHub(hardwareMap.get(LynxModule.class, "Expansion Hub 1"));
```

Using an `OpenRevHub`, you can access:

```java
expansionHub.setLedColor(byte r, byte g, byte b);
expansionHub.setLedColor(HardwareMap hardwareMap, int resID); // Useful if you have colors defined as R.color.pink
expansionHub.getTotalModuleCurrentDraw();
expansionHub.getServoBusCurrentDraw(); // This is known to have issues, but they are most likely are caused by the ExH firmware itself
expansionHub.getGpioBusCurrentDraw();
expansionHub.getI2CbusCurrentDraw();
expansionHub.read5vMonitor();
expansionHub.read12vMonitor();
expansionHub.getInternalTemperature();
```

Finally, you can also access these features through the `OpenRevDcMotorController` class. This allows you to access the current draw of all four motors that the Expansion Hub supports, without needing individual motor declarations. 

TODO: Finish motor controller docs 
