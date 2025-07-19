// DatePickerComponent.js
import React from 'react';
import './CheckboxComponent.css';

const CheckboxComponent = ({key, checked = false, text, handleCheckboxChange, id = "", name=""}) => {
    return (
        <div className="checkbox-component-wrapper">
            <label className="checkbox-component-content checkbox-component-label" key={key}>
                <input
                    type="checkbox"
                    name={name}
                    id={id}
                    checked={checked}
                    onChange={handleCheckboxChange}
                />
                <div className="checkmark">
                    <img src="../../../../img/icon/check.svg" alt="" />
                </div>
            </label>
            <p className="basic-text2">{text}</p>
        </div>
    );
};

export default CheckboxComponent;
