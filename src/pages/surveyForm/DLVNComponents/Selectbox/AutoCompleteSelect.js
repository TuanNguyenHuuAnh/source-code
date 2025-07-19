// Import statements...

import {AutoComplete} from "antd";
import {SearchOutlined} from "@ant-design/icons";
import {useMemo, useState} from "react";
import './AutoCompleteSelect.css';

const AutoCompleteSelect = ({options, onChange}) => {
    const [searchText, setSearchText] = useState('');
    const [selectedOption, setSelectedOption] = useState(null);

    const handleSearchChange = (value) => {
        setSelectedOption(null);
        setSearchText(value);
    };

    const handleAutoCompleteChange = (value) => {
        handleSearchChange(value);
    };

    const handleAutoCompleteSelect = (selectedValue, option) => {
        if (selectedValue) {
            onChange(option.value);
            setSelectedOption(option);
            setSearchText(option.children);
        }
    };

    const handleAutoCompleteClear = () => {
        setSearchText('');
        setSelectedOption(null);
    };

    const handleAutoCompleteBlur = () => {
        if (!selectedOption) {
            setSearchText('');
            setSelectedOption(null);
        }
    };

    const filteredOptions = useMemo(() => {
        return searchText ? options.filter((option) =>
            option.label.toLowerCase().includes(searchText.toLowerCase())
        ) : options;
    }, [options, searchText]);

    return (
        <AutoComplete
            className="custom-autocomplete"
            style={{width: '100%'}}
            suffixIcon={<SearchOutlined/>}
            showSearch
            onChange={handleAutoCompleteChange}
            onClear={handleAutoCompleteClear}
            onSelect={handleAutoCompleteSelect}
            onBlur={handleAutoCompleteBlur}
            value={selectedOption ? selectedOption.children : searchText}
            placeholder="Chọn cơ sở y tế"
            allowClear
            notFoundContent={(
                <div style={{whiteSpace: 'normal'}}>
                    <p>Không có dữ liệu</p>
                </div>
            )}
        >
            {filteredOptions.map((option) => (
                <AutoComplete.Option key={option.value} value={option.value}>
                    {option.label}
                </AutoComplete.Option>
            ))}
        </AutoComplete>
    );
};

export default AutoCompleteSelect;
