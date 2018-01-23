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

import com.qualcomm.hardware.lynx.LynxDcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Wraps a DcMotorImplEx to provide access to new features. Note: motor MUST be attached
 * to an Expansion Hub.Authors: Benjamin Ward
 */
public class OpenRevDcMotorImplEx implements DcMotor, DcMotorEx {
    private OpenRevDcMotorController controller;
    private DcMotorImplEx realMotor;

    public OpenRevDcMotorImplEx(DcMotorImplEx motor) {
        realMotor = motor;
        controller = new OpenRevDcMotorController((LynxDcMotorController)motor.getController());
    }

    public double getCurrentDraw()
    {
        return controller.getMotorCurrentDraw(this.getPortNumber());
    }

    @Override
    public void setMotorEnable() {
        realMotor.setMotorEnable();
    }

    @Override
    public void setMotorDisable() {
        realMotor.setMotorDisable();
    }

    @Override
    public boolean isMotorEnabled() {
        return realMotor.isMotorEnabled();
    }

    @Override
    public void setVelocity(double angularRate, AngleUnit unit) {
        realMotor.setVelocity(angularRate, unit);
    }

    @Override
    public double getVelocity(AngleUnit unit) {
        return realMotor.getVelocity(unit);
    }

    @Override
    public void setPIDCoefficients(RunMode mode, PIDCoefficients pidCoefficients) {
        realMotor.setPIDCoefficients(mode, pidCoefficients);
    }

    @Override
    public PIDCoefficients getPIDCoefficients(RunMode mode) {
        return realMotor.getPIDCoefficients(mode);
    }

    @Override
    public void setTargetPositionTolerance(int tolerance) {
        realMotor.setTargetPositionTolerance(tolerance);
    }

    @Override
    public int getTargetPositionTolerance() {
        return realMotor.getTargetPositionTolerance();
    }

    @Override
    public MotorConfigurationType getMotorType() {
        return realMotor.getMotorType();
    }

    @Override
    public void setMotorType(MotorConfigurationType motorType) {
        realMotor.setMotorType(motorType);
    }

    @Override
    public DcMotorController getController() {
        return realMotor.getController();
    }

    @Override
    public int getPortNumber() {
        return realMotor.getPortNumber();
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        realMotor.setZeroPowerBehavior(zeroPowerBehavior);
    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return realMotor.getZeroPowerBehavior();
    }

    @Override
    @Deprecated
    public void setPowerFloat() {
        //noinspection deprecation
        realMotor.setPowerFloat();
    }

    @Override
    public boolean getPowerFloat() {
        return realMotor.getPowerFloat();
    }

    @Override
    public void setTargetPosition(int position) {
        realMotor.setTargetPosition(position);
    }

    @Override
    public int getTargetPosition() {
        return realMotor.getTargetPosition();
    }

    @Override
    public boolean isBusy() {
        return realMotor.isBusy();
    }

    @Override
    public int getCurrentPosition() {
        return realMotor.getCurrentPosition();
    }

    @Override
    public void setMode(RunMode mode) {
        realMotor.setMode(mode);
    }

    @Override
    public RunMode getMode() {
        return realMotor.getMode();
    }

    @Override
    public void setDirection(Direction direction) {
        realMotor.setDirection(direction);
    }

    @Override
    public Direction getDirection() {
        return realMotor.getDirection();
    }

    @Override
    public void setPower(double power) {
        realMotor.setPower(power);
    }

    @Override
    public double getPower() {
        return realMotor.getPower();
    }

    @Override
    public Manufacturer getManufacturer() {
        return realMotor.getManufacturer();
    }

    @Override
    public String getDeviceName() {
        return realMotor.getDeviceName();
    }

    @Override
    public String getConnectionInfo() {
        return realMotor.getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return realMotor.getVersion();
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        realMotor.resetDeviceConfigurationForOpMode();
    }

    @Override
    public void close() {
        realMotor.close();
    }
}
