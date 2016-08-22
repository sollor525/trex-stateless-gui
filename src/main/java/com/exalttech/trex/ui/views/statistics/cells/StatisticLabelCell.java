/**
 * *****************************************************************************
 * Copyright (c) 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************
 */
package com.exalttech.trex.ui.views.statistics.cells;

import com.exalttech.trex.ui.PortState;
import com.exalttech.trex.util.Util;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Statistical label cell implementation
 *
 * @author Georgekh
 */
public class StatisticLabelCell extends Label implements StatisticCell {

    CellType type;

    public StatisticLabelCell(double width, boolean odd, CellType type, boolean isRightPosition) {
        this.type = type;
        setPrefSize(width, 25);
        setAlignment(Pos.CENTER_LEFT);
        if (isRightPosition) {
            setAlignment(Pos.CENTER_RIGHT);
        }
        getStyleClass().add("statsTableColCell");
        if (odd) {
            getStyleClass().add("statsTableColCellOdd");
        }
    }

    /**
     * Set left position
     */
    public void setLeftPosition() {
        setAlignment(Pos.CENTER_LEFT);
    }

    /**
     * Update cell values
     *
     * @param oldValue
     * @param newValue
     */
    @Override
    public void updateItem(String oldValue, String newValue) {
        switch (type) {
            case ERROR_CELL:
                double currentVal = Double.parseDouble(newValue);
                double prevVal = 0;
                if (!Util.isNullOrEmpty(oldValue)) {
                    prevVal = Double.parseDouble(oldValue);
                }
                double diff = Math.abs(currentVal - prevVal);
                setText(String.valueOf((int)diff));
                updateErrorCell(diff);
                break;
            case STATUS_CELL:
                setText(newValue);
                updateStatusCell(newValue);
                break;
            default:
                setText(newValue);
                break;
        }
    }

    /**
     * Update error cell value
     *
     * @param newValue
     */
    private void updateErrorCell(double value) {
        String valueColor = "statsTableGreenValue";
        if (value > 0) {
            valueColor = "statsTableRedValue";
        }
        getStyleClass().add(valueColor);
    }

    /**
     * Update status cell value
     *
     * @param newValue
     */
    private void updateStatusCell(String newValue) {
        PortState portState = PortState.getPortStatus(newValue);
        getStyleClass().add(portState.getTextColor());
    }

}
