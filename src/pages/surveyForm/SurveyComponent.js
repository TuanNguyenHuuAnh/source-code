import React, {useEffect, useState} from 'react';
import moment from "moment/moment";
import InputComponent from "./DLVNComponents/Input/InputComponent";
import RadioComponent from "./DLVNComponents/Radio/RadioComponent";
import DatePickerComponent from "./DLVNComponents/DatePicker/DatePickerComponent";
import CheckboxComponent from "./DLVNComponents/Checkbox/CheckboxComponent";
import AutoCompleteSelect from "./DLVNComponents/Selectbox/AutoCompleteSelect";
import {isEmpty} from "lodash";

const SurveyComponent = ({
                             data,
    getDataSubmit
                         }) => {
    const [formData, setFormData] = useState({});

    const handleInputChange = (questionCode, value) => {
        setFormData({...formData, [questionCode]: value});
    };

    const handleCheckboxChange = (questionCode, value, e) => {
        const currentCheckboxValues = formData[questionCode] || [];
        const updatedCheckboxValues = e.target.checked
            ? [...currentCheckboxValues, value]
            : currentCheckboxValues.filter((v) => v !== value);

        setFormData({...formData, [questionCode]: updatedCheckboxValues});
    };

    const convertData = (data) => {
        if (!data) return;
        return data.map(item => ({
            label: item.SDisplayText,
            value: item.SCode,
            order: item.SOrder
        }));
    }

    const renderQuestion = (question, idx) => {
        switch (question.QType) {
            case 'InputField':
                return (
                    <div key={question.QCode}>
                        <p className="survey-component-container__content-question">{`${question.QContent}`}</p>
                        <InputComponent
                            value={formData[question.QCode] || ''}
                            placeholder={question.QContent}
                            handleInputChange={(e) => handleInputChange(question.QCode, e.target.value)}
                        />
                    </div>
                );

            case 'RadioBox':
                return (
                    <div key={question.QCode}>
                        <p className="survey-component-container__content-question">{`${question.QContent}`}</p>
                        {question.QDataList?.map((option, index) => (
                            <RadioComponent
                                key={option.SCode}
                                checked={formData[question.QCode] === option.SCode}
                                handleRadioChange={() => handleInputChange(question.QCode, option.SCode)}
                                id={`radioCheckSurvey_${option.SOrder}`}
                                text={option.SDisplayText}
                            />
                        ))}
                    </div>
                );

            case 'DateTimePicker':
                return (
                    <div key={question.QCode}>
                        <p className="survey-component-container__content-question">{`${question.QContent}`}</p>
                        <DatePickerComponent
                            id={`datepicker_${question.QCode}`}
                            value={formData[question.QCode] ? moment(formData[question.QCode]) : null}
                            handleDateChange={(date, dateString) => handleInputChange(question.QCode, dateString)}
                        />
                    </div>
                );

            case 'Checkbox':
                return (
                    <div key={question.QCode}>
                        <p className="survey-component-container__content-question">{`${question.QContent}`}</p>
                        {question.QDataList.map((option) => (
                            <CheckboxComponent
                                key={option.SOrder}
                                name={option.SOptionCode}
                                id={option.SOptionCode}
                                checked={formData[question.QCode]?.includes(option.SCode ? option.SCode : option.SOptionCode) || false}
                                handleCheckboxChange={(isChecked) =>
                                    handleCheckboxChange(question.QCode, option.SCode ? option.SCode : option.SOptionCode, isChecked)
                                }
                                text={option.SDisplayText}
                            />
                        ))}
                    </div>
                );
            case 'AutoComplete':
                return (
                    <div key={question.QCode}>
                        <p className="survey-component-container__content-question">{`${question.QContent}`}</p>
                        <AutoCompleteSelect
                            options={convertData(question.QDataList)}
                            onChange={(value) => handleInputChange(question.QCode, value)}

                        />
                    </div>
                );
            default:
                return null;
        }
    };

    useEffect(() => !isEmpty(formData) && getDataSubmit(formData), [formData]);

    return (
        <div className="survey-component-container">
            {!isEmpty(data) &&
                <>
                    <h4 className="survey-component-container__title">{data.Data.Title}</h4>
                    <div className="survey-component-container__content"
                         dangerouslySetInnerHTML={{__html: data.Data.Introduce}}/>
                    {data.Data.Questions.map((question, idx) => renderQuestion(question, idx + 1))}
                </>
            }
        </div>
    );
};

export default SurveyComponent;
