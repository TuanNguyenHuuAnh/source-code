// AntDatePicker.js
import React from 'react';
import { DatePicker } from 'antd';
import './DatePickerComponent.css';
import 'antd/dist/antd.min.css';

const DatePickerComponent = ({ id = "", value, handleDateChange }) => {
    return (
        <div className="custom-date-picker">
            <div className="custom-date-picker__inputdate">
                <DatePicker
                    className="custom-date-picker__inputdate-wrapper"
                    placeholder="Ngày"
                    id={id}
                    value={value}
                    onChange={handleDateChange}
                />
            </div>
        </div>
    );
};

export default DatePickerComponent;
