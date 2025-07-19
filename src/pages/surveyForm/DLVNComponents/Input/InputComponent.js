// Input.js
import React from 'react';
import './InputComponent.css';

const InputComponent = ({label = "", value, placeholder, handleInputChange, name = "", id = "", type = "search"}) => {
    return (
        <div className="input-container__input-wrapper">
            <div className="input-container__input-content">
                {label && <label htmlFor="" style={{paddingLeft: '2px'}}>
                    <p className="survey-component-container__content-question">{label}</p>
                </label>}
                <input
                    className="input-container__input"
                    type={type}
                    name={name}
                    id={id}
                    value={value}
                    placeholder={placeholder}
                    onChange={handleInputChange}
                />
            </div>
            <i><img className="input-container__edit-icon" src="../../../../img/icon/edit.svg" alt=""/></i>
        </div>
    );
};

export default InputComponent;
