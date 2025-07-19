import React from "react";
import ReactDOM from "react-dom";
import Highlighter from "react-highlight-words";
import { removeAccents } from '../util/common';

const CustomSelectContainer = ({ data, keywork }) => {
  return (
    <div style={{ width: "100%", height: "53px" }}>
      <div className="state">
        <span>
          <Highlighter
            className = "select-header"
            highlightClassName="simple-brown-small"
            searchWords={keywork.split(' ')}
            autoEscape={true}
            sanitize={removeAccents}
            textToHighlight={data.StateName}
          />
        </span>
      </div>
      <div className="hopital-name">
        <span>

          <Highlighter
            highlightClassName="simple-brown"
            searchWords={keywork.split(' ')}
            autoEscape={true}
            sanitize={removeAccents}
            textToHighlight={data.HospitalName}
          />
        </span>

      </div>
    </div>
  );
};
export default CustomSelectContainer;


