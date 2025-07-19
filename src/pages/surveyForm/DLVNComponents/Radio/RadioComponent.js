// DatePickerComponent.js
import React from 'react';
import './RadioComponent.css';

const RadioComponent = ({key, checked = false, text, handleRadioChange, id = ""}) => {
    return (
        <div className="radio-item" key={key}>
            <div className="round-radio">
                <label className="radio-item__label custom-radio">
                    <input
                        type="radio"
                        checked={checked}
                        onClick={handleRadioChange}
                        id={id}
                    />
                    <div className="radio-item__checkmark"></div>
                    <p className="radio-item__text">{text}</p>
                </label>
            </div>
        </div>
    );
};

export default RadioComponent;
