/*
 * Copyright (c) 2017 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.openftc.hardware.rev;

import com.qualcomm.hardware.lynx.LynxCommExceptionHandler;
import com.qualcomm.hardware.lynx.LynxController;
import com.qualcomm.hardware.lynx.LynxDcMotorController;
import com.qualcomm.hardware.lynx.LynxModuleIntf;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.core.LynxGetADCCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetADCResponse;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Wraps a LynxDcMotorController (i.e. the controller in an Expansion Hub aka LynxModule) to
 * provide access to new features. Authors: Benjamin Ward
 */
public class OpenRevDcMotorController extends LynxCommExceptionHandler implements DcMotorController, DcMotorControllerEx {
    private LynxModuleIntf expansionHub;
    private LynxDcMotorController realController;

    public OpenRevDcMotorController(LynxDcMotorController controller) {
        realController = controller;

        // To access the current sensors, we need an instance of the Expansion Hub (LynxModule)
        // itself, or a PretendLynxModule. We can get this through the LynxDcMotorController,
        // but only through reflection.
        Method getModule_method;

        try {
            // The "getModule" method is located within LynxDcMotorController's parent
            // class, LynxController
            getModule_method = LynxController.class.getDeclaredMethod("getModule");

            // Ensures the method is accessible for the next line. We still catch
            // the (impossible) IllegalAccessException just to be safe.
            getModule_method.setAccessible(true);

            // Actually get the value from the controller that was passed in. We cast this to
            // a LynxModuleIntf instead of a LynxModule to support the use of a PretendLynxModule.
            expansionHub = (LynxModuleIntf) getModule_method.invoke(controller);
        } catch (NoSuchMethodException e) {
            RobotLog.ee("OpenFTC", e, "Failed to reflect module from LynxDcMotorController. No such method.");
        } catch (IllegalAccessException e) {
            RobotLog.ee("OpenFTC", e, "Failed to reflect module from LynxDcMotorController. Illegal access.");
        } catch (InvocationTargetException e) {
            RobotLog.ee("OpenFTC", e, "Failed to reflect module from LynxDcMotorController. Illegal invocation target.");
        }
    }

    public synchronized double getMotorCurrentDraw(int port) {
        LynxGetADCCommand.Channel channel;

        if (port == 0) {
            channel = LynxGetADCCommand.Channel.MOTOR0_CURRENT;
        } else if (port == 1) {
            channel = LynxGetADCCommand.Channel.MOTOR1_CURRENT;
        } else if (port == 2) {
            channel = LynxGetADCCommand.Channel.MOTOR2_CURRENT;
        } else if (port == 3) {
            channel = LynxGetADCCommand.Channel.MOTOR3_CURRENT;
        } else {
            return 0; // TODO: Should we handle an invalid port with a crash?
        }

        LynxGetADCCommand command = new LynxGetADCCommand(expansionHub, channel, LynxGetADCCommand.Mode.ENGINEERING);
        try {
            LynxGetADCResponse response = command.sendReceive();
            return response.getValue();
        } catch (InterruptedException | RuntimeException | LynxNackException e) {
            handleException(e);
        }
        return 0;
    }

    @Override
    public void setMotorEnable(int motor) {
        realController.setMotorEnable(motor);
    }

    @Override
    public void setMotorDisable(int motor) {
        realController.setMotorDisable(motor);
    }

    @Override
    public boolean isMotorEnabled(int motor) {
        return realController.isMotorEnabled(motor);
    }

    @Override
    public void setMotorVelocity(int motor, double angularRate, AngleUnit unit) {
        realController.setMotorVelocity(motor, angularRate, unit);
    }

    @Override
    public double getMotorVelocity(int motor, AngleUnit unit) {
        return realController.getMotorVelocity(motor, unit);
    }

    @Override
    public void setPIDCoefficients(int motor, DcMotor.RunMode mode, PIDCoefficients pidCoefficients) {
        realController.setPIDCoefficients(motor, mode, pidCoefficients);
    }

    @Override
    public PIDCoefficients getPIDCoefficients(int motor, DcMotor.RunMode mode) {
        return realController.getPIDCoefficients(motor, mode);
    }

    @Override
    public void setMotorTargetPosition(int motor, int position, int tolerance) {
        realController.setMotorTargetPosition(motor, position, tolerance);
    }

    @Override
    public void setMotorType(int motor, MotorConfigurationType motorType) {
        realController.setMotorType(motor, motorType);
    }

    @Override
    public MotorConfigurationType getMotorType(int motor) {
        return realController.getMotorType(motor);
    }

    @Override
    public void setMotorMode(int motor, DcMotor.RunMode mode) {
        realController.setMotorMode(motor, mode);
    }

    @Override
    public DcMotor.RunMode getMotorMode(int motor) {
        return realController.getMotorMode(motor);
    }

    @Override
    public void setMotorPower(int motor, double power) {
        realController.setMotorPower(motor, power);
    }

    @Override
    public double getMotorPower(int motor) {
        return realController.getMotorPower(motor);
    }

    @Override
    public boolean isBusy(int motor) {
        return realController.isBusy(motor);
    }

    @Override
    public void setMotorZeroPowerBehavior(int motor, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        realController.setMotorZeroPowerBehavior(motor, zeroPowerBehavior);
    }

    @Override
    public DcMotor.ZeroPowerBehavior getMotorZeroPowerBehavior(int motor) {
        return realController.getMotorZeroPowerBehavior(motor);
    }

    @Override
    public boolean getMotorPowerFloat(int motor) {
        return realController.getMotorPowerFloat(motor);
    }

    @Override
    public void setMotorTargetPosition(int motor, int position) {
        realController.setMotorTargetPosition(motor, position);
    }

    @Override
    public int getMotorTargetPosition(int motor) {
        return realController.getMotorTargetPosition(motor);
    }

    @Override
    public int getMotorCurrentPosition(int motor) {
        return realController.getMotorCurrentPosition(motor);
    }

    @Override
    public Manufacturer getManufacturer() {
        return realController.getManufacturer();
    }

    @Override
    public String getDeviceName() {
        return realController.getDeviceName();
    }

    @Override
    public String getConnectionInfo() {
        return realController.getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return realController.getVersion();
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        realController.resetDeviceConfigurationForOpMode();
    }

    @Override
    public void close() {
        realController.close();
    }
}