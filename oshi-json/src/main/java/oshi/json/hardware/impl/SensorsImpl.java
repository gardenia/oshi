/**
 * OSHI (https://github.com/oshi/oshi)
 *
 * Copyright (c) 2010 - 2019 The OSHI Project Team:
 * https://github.com/oshi/oshi/graphs/contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package oshi.json.hardware.impl;

import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import oshi.json.hardware.Sensors;
import oshi.json.json.AbstractOshiJsonObject;
import oshi.json.json.NullAwareJsonObjectBuilder;
import oshi.json.util.PropertiesUtil;

/**
 * Wrapper class to implement Sensors interface with platform-specific objects
 */
public class SensorsImpl extends AbstractOshiJsonObject implements Sensors {

    private static final long serialVersionUID = 1L;

    private transient JsonBuilderFactory jsonFactory = Json.createBuilderFactory(null);

    private oshi.hardware.Sensors sensors;

    /**
     * Creates a new platform-specific Sensors object wrapping the provided
     * argument
     *
     * @param sensors
     *            a platform-specific Sensors object
     */
    public SensorsImpl(oshi.hardware.Sensors sensors) {
        this.sensors = sensors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCpuTemperature() {
        return this.sensors.getCpuTemperature();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getFanSpeeds() {
        return this.sensors.getFanSpeeds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCpuVoltage() {
        return this.sensors.getCpuVoltage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonObject toJSON(Properties properties) {
        JsonObjectBuilder json = NullAwareJsonObjectBuilder.wrap(this.jsonFactory.createObjectBuilder());
        if (PropertiesUtil.getBoolean(properties, "hardware.sensors.cpuTemperature")) {
            json.add("cpuTemperature", getCpuTemperature());
        }
        if (PropertiesUtil.getBoolean(properties, "hardware.sensors.fanSpeeds")) {
            JsonArrayBuilder fanSpeedsArrayBuilder = this.jsonFactory.createArrayBuilder();
            for (int speed : getFanSpeeds()) {
                fanSpeedsArrayBuilder.add(speed);
            }
            json.add("fanSpeeds", fanSpeedsArrayBuilder.build());
        }
        if (PropertiesUtil.getBoolean(properties, "hardware.sensors.cpuVoltage")) {
            json.add("cpuVoltage", getCpuVoltage());
        }
        return json.build();
    }

}
