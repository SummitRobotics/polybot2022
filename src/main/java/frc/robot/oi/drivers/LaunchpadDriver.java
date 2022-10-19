package frc.robot.oi.drivers;

import edu.wpi.first.hal.HAL;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.oi.inputs.OIButton;
import java.util.function.DoubleSupplier;


/**
 * Wrapper class for the TI Launchpad in mode 1.
 */
public class LaunchpadDriver extends GenericDriver {

    private int outputs;

    public OIButton missileA, missileB, funLeft, funMiddle, funRight;
    public OIAxis axisA, axisB, axisC, axisD, axisE, axisF, axisG, axisH;

    public DoubleSupplier reee;


    /**
     * Constructor for the TI Launchpad.\
     *
     * @param port The port of the TI Launchpad. This is retrieved from the driver station.
     */
    public LaunchpadDriver(int port) {
        super(port);

        missileA = generateOIButton(10);
        missileB = generateOIButton(11);


        axisA = generateOIAxis(0);
        axisB = generateOIAxis(1);

        axisA.setDeadzone(0);
        axisB.setDeadzone(0);

        reee = getAxisGetter(2);

        funLeft = generateATDRangeButton(2, -1.0, -0.95);
        funMiddle = generateATDRangeButton(2, -0.03, 0.03);
        funRight = generateATDRangeButton(2, 0.95, 1.0);

        axisC = generateOIAxis(3);
        axisD = generateOIAxis(4);
        axisE = generateOIAxis(5);
        axisF = generateOIAxis(6);
        axisG = generateOIAxis(7);
        axisH = generateOIAxis(8);
    }

    private OIButton generateATDRangeButton(int output, double min, double max) {
        DoubleSupplier axis = getAxisGetter(output);
        return new OIButton(
                () -> {
                    double value = axis.getAsDouble();
                    return value >= min && max >= value;
                });
    }

    /**
     * Black box to set outputs copied from wpilib.
     *
     * @param outputNumber the output number
     * @param value        the state of the output
     */
    public void setOutput(int outputNumber, boolean value) {
        outputs = (outputs & ~(1 << (outputNumber - 1))) | ((value ? 1 : 0) << (outputNumber - 1));
        HAL.setJoystickOutputs((byte) port, outputs, (short) 0, (short) 0);
    }
}
